/*
 * Copyright 2007 - 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lht.wangtong.core.utils.db.tool;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import lht.wangtong.core.utils.db.jdbcmodel.DbTypeEnum;

/**
 * Manages database sessions on a 'per thread' basis.
 * Executes SQL-Statements in the context of a session.
 * 
 * @author aibozeng
 */
public class Session {

    /**
     * Hold a connection for each thread.
     */
    private ThreadLocal<Connection> connection = new ThreadLocal<Connection>();
    
    /**
     * Holds all connections.
     */
    private final Collection<Connection> connections = Collections.synchronizedCollection(new ArrayList<Connection>());
    
    /**
     * The session in which temporary tables lives, if any.
     */
    private static Connection temporaryTableSession = null;
    
    /**
     * Scope of temporary tables.
     */
    private static TemporaryTableScope temporaryTableScope;
    
    /**
     * No SQL-Exceptions will be logged in silent mode. 
     */
    private boolean silent = false;
    
    private final boolean transactional;
    
    /**
     * Reads a JDBC-result-set.
     */
    public interface ResultSetReader {
    
        /**
         * Reads current row of a result-set.
         * 
         * @param resultSet the result-set
         */
        void readCurrentRow(ResultSet resultSet) throws SQLException;

        /**
         * Finalizes reading.
         */
        void close();
    }
    
    /**
     * Reads a JDBC-result-set.
     */
    public static abstract class AbstractResultSetReader implements ResultSetReader {
    
        /**
         * Does nothing.
         */
        public void close() {
        }
    }
    
    /**
     * The logger.
     */
    public static final Logger _log = Logger.getLogger("sql");
 
    /**
     * Connection factory.
     */
    private interface ConnectionFactory {
        Connection getConnection() throws SQLException;
    };
    
    /**
     * Connection factory.
     */
    private final ConnectionFactory connectionFactory;

    /**
     * The DB schema name.
     */
    private final String schemaName;

    /**
     * The DB URL.
     */
    public final String dbUrl;
    
    /**
     * The DB user.
     */
    public final String dbUser;
    
    /**
     * The DB password.
     */
    public final String dbPassword;
    
    public final Properties props =new Properties();


    /**
     * Optional schema for database analysis.
     */
    private String introspectionSchema;
    
    /**
     * Classloader to load Jdbc-Driver with.
     */
    public static ClassLoader classLoaderForJdbcDriver = null;

    /**
     * The DBMS.
     */
    public final DbTypeEnum dbms;
    
    /**
     * Wraps a Jdbc-Driver.
     */
    public static class DriverShim implements Driver {
        private Driver driver;
        public DriverShim(Driver d) {
            this.driver = d;
        }
        public boolean acceptsURL(String u) throws SQLException {
            return this.driver.acceptsURL(u);
        }
        public Connection connect(String u, Properties p) throws SQLException {
            return this.driver.connect(u, p);
        }
        public int getMajorVersion() {
            return this.driver.getMajorVersion();
        }
        public int getMinorVersion() {
            return this.driver.getMinorVersion();
        }
        public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
            return this.driver.getPropertyInfo(u, p);
        }
        public boolean jdbcCompliant() {
            return this.driver.jdbcCompliant();
        }
        public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        	throw new SQLFeatureNotSupportedException();
        }
    }
    
    /**
     * Constructor.
     * 
     * @param driverClassName name of JDBC-driver class
     * @param dbUrl the database URL
     * @param user the DB-user
     * @param password the DB-password
     */
    public Session(String driverClassName, final String dbUrl, final String user, final String password) throws Exception {
    	this(driverClassName, dbUrl, user, password, null, false);
    }
    
    /**
     * Constructor.
     * 
     * @param driverClassName name of JDBC-driver class
     * @param dbUrl the database URL
     * @param user the DB-user
     * @param password the DB-password
     */
    public Session(String driverClassName, final String dbUrl, final String user, final String password, final TemporaryTableScope scope, boolean transactional) throws Exception {
    	this.transactional = transactional;
        _log.info("connect to user " + user + " at "+ dbUrl);
        if (classLoaderForJdbcDriver != null) {
            Driver d = (Driver)Class.forName(driverClassName, true, classLoaderForJdbcDriver).newInstance();
            DriverManager.registerDriver(new DriverShim(d));
        } else {
            Class.forName(driverClassName);
        }
        this.schemaName = user;
        this.dbUrl = dbUrl;
        this.dbUser = user;
        this.dbPassword = password;
        
        props.put("user",dbUser);
        props.put("password",dbPassword);
        //2011-01-04 aibozeng 必须加上这个属性,才能查询出 remarks 信息
        props.put("remarksReporting","true");
        
        if (scope != null) {
        	closeTemporaryTableSession();
        	temporaryTableScope = scope;
        }
        connectionFactory = new ConnectionFactory() {
        	private Connection defaultConnection = null;
            public Connection getConnection() throws SQLException {
                Connection con = temporaryTableSession == null? connection.get() : temporaryTableSession;
                
                if (con == null) {
                	try {
                		con = DriverManager.getConnection(dbUrl, props);
                		defaultConnection = con;
                	} catch (SQLException e) {
                		if (defaultConnection != null) {
                			// fall back to default connection
                			con = defaultConnection;
                		} else {
                			throw e;
                		}
                	}
                    boolean ac = scope == null || scope != TemporaryTableScope.TRANSACTION_LOCAL;
                    if (Session.this.transactional) {
                    	ac = false;
                    }
                    _log.info("set auto commit to " + ac);
                    con.setAutoCommit(ac);
                    try {
                    	DatabaseMetaData meta = con.getMetaData();
                		String productName = meta.getDatabaseProductName();
                		if (productName != null) {
                			if ((!"ASE".equals(productName)) && !productName.toUpperCase().contains("ADAPTIVE SERVER")) {
                				// Sybase don't handle UR level correctly, see http://docs.sun.com/app/docs/doc/819-4728/gawlc?a=view
                				if (!productName.toUpperCase().startsWith("HSQL")) {
                					// HSQL don't allow write access at UR level
                        			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                				}
                      		}
                		}
                    } catch (SQLException e) {
                        _log.info("can't set isolation level to UR. Reason: " + e.getMessage());
                    }
                	if (scope != null && scope != TemporaryTableScope.GLOBAL) {
                		temporaryTableSession = con;
                	} else {
                        connection.set(con);
                        boolean addCon = true;
                        for (Connection c: connections) {
                        	if (c == con) {
                        		addCon = false;
                        		break;
                        	}
                        }
                        if (addCon) {
                        	connections.add(con);
                        }
                	}
                }
                return con;
            }
        };
        // fail fast
        Connection connection = connectionFactory.getConnection();
        dbms = logDriverInfo(connection);
    }

    /**
     * Closes current connection and opens a new one.
     */
    public void reconnect() throws SQLException {
    	Connection con = connection.get();
    	if (con != null) {
    		if (temporaryTableScope == TemporaryTableScope.TRANSACTION_LOCAL) {
    			con.commit();
    		}
    		con.close();
    		connection.set(null);
    		if (con == temporaryTableSession) {
    			temporaryTableSession = null;
    			return;
    		}
    	}
    	if (temporaryTableSession != null) {
    		if (temporaryTableScope == TemporaryTableScope.TRANSACTION_LOCAL) {
    			temporaryTableSession.commit();
    		}
    		temporaryTableSession.close();
    		temporaryTableSession = null;
    	}
    }
    
    /**
     * No SQL-Exceptions will be logged in silent mode.
     * 
     * @param silent <code>true</code> for silence
     */
    public synchronized void setSilent(boolean silent) {
    	this.silent = silent;
    }
    
    /**
     * No SQL-Exceptions will be logged in silent mode.
     * 
     * @return silent <code>true</code> for silence
     */
    public synchronized boolean getSilent() {
    	return silent;
    }

    /**
     * Logs driver info
     * 
     * @param connection connection to DB
     * @return the DBMS
     */
    private DbTypeEnum logDriverInfo(Connection connection) {
    	DbTypeEnum dbms = DbTypeEnum.UNKNOWN;
		try {
			DatabaseMetaData meta = connection.getMetaData();
			_log.info("driver name:    " + meta.getDriverName());
			_log.info("driver version: " + meta.getDriverVersion());
			String productName = meta.getDatabaseProductName();
			if (productName != null) {
				if (productName.toUpperCase().contains("ORACLE")) dbms = DbTypeEnum.ORACLE;
				if (productName.toUpperCase().contains("DB2")) dbms = DbTypeEnum.DB2;
				if (productName.toUpperCase().contains("POSTGRES")) dbms = DbTypeEnum.POSTGRESQL;
				if (productName.toUpperCase().contains("MYSQL")) dbms = DbTypeEnum.MySQL;
				if (productName.toUpperCase().contains("SQLITE")) dbms = DbTypeEnum.SQLITE;
				if (productName.toUpperCase().contains("ADAPTIVE SERVER")) dbms = DbTypeEnum.SYBASE;
				if (productName.toUpperCase().contains("HSQL")) dbms = DbTypeEnum.HSQL;
				if (productName.toUpperCase().equals("ASE")) dbms = DbTypeEnum.SYBASE;
			}
			_log.info("DB name:        " + productName + " (" + dbms + ")");
			_log.info("DB version:     " + meta.getDatabaseProductVersion());
		} catch (Exception e) {
			// ignore exceptions
		}
		return dbms;
	}

	/**
     * Gets DB schema name.
     * 
     * @return DB schema name
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * Lock for prevention of livelocks.
     */
    private static final Object DB_LOCK = "DB_LOCK";
    
    
    /**
     * Gets DB meta data.
     * 
     * @return DB meta data
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        return connection.getMetaData();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = connectionFactory.getConnection();
        return connection;    	
    }
    
    /**
     * Sets {@link ClassLoader} to load Jdbc-driver with.
     * 
     * @param classLoader {@link ClassLoader} to load Jdbc-driver with
     */
    public static synchronized void setClassLoaderForJdbcDriver(ClassLoader classLoader) {
        classLoaderForJdbcDriver = classLoader;
    }
    
    /**
     * Closes all connections.
     */
    public void shutDown() throws SQLException {
    	_log.info("closing connection...");
        for (Connection con: connections) {
            con.close();
        }
    	_log.info("connection closed");
    }
    
    /**
     * Rolls back and closes all connections.
     */
    public void rollbackAll() throws SQLException {
    	for (Connection con: connections) {
            try {
            	con.rollback();
            } catch(SQLException e) {
        		_log.warn(e.getMessage());
        	}
            try {
            	con.close();
            } catch(SQLException e) {
        		_log.warn(e.getMessage());
        	}
    	}
    	connection = new ThreadLocal<Connection>();
    }
    
    /**
     * Commits all connections.
     */
    public void commitAll() throws SQLException {
    	for (Connection con: connections) {
            try {
            	con.commit();
            } catch(SQLException e) {
        		_log.warn(e.getMessage());
        	}
     	}
    }
    
    /**
     * Gets optional schema for database analysis.
     * 
     * @return optional schema for database analysis
     */
    public String getIntrospectionSchema() {
    	return introspectionSchema;
    }
    
    /**
     * Sets optional schema for database analysis.
     * 
     * @param introspectionSchema optional schema for database analysis
     */
    public void setIntrospectionSchema(String introspectionSchema) {
    	this.introspectionSchema = introspectionSchema;
    }
    
    /**
     * Closes the session in which temporary tables lives, if any.
     */
    public static void closeTemporaryTableSession() {
    	try {
    		if (temporaryTableSession != null) {
    			if (temporaryTableScope == TemporaryTableScope.TRANSACTION_LOCAL) {
    				temporaryTableSession.commit();
    			}
    			temporaryTableSession.close();
    		}
    	} catch(SQLException e) {
    		_log.error("can't close connection", e);
    	}
    	temporaryTableSession = null;
    }

    /**
     * CLI connection arguments (UI support)
     */
    private List<String> cliArguments;
    
    /**
     * Connection password (UI support)
     */
    private String password;
    
    /**
     * Gets connection password (UI support)
     */
    public synchronized String getPassword() {
		return password;
	}

    /**
     * Sets connection password (UI support)
     */
	public synchronized void setPassword(String password) {
		this.password = password;
	}
    
	/**
     * Sets CLI connection arguments (UI support)
     */
	public synchronized void setCliArguments(List<String> args) {
		this.cliArguments = args;
	}
	
	/**
	 * Gets CLI connection arguments (UI support)
	 */
	public synchronized List<String> getCliArguments() {
		return cliArguments;
	}

}
