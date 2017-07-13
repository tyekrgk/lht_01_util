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
package lht.wangtong.core.utils.db.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import lht.wangtong.core.utils.db.jdbcmodel.AssociationVO;
import lht.wangtong.core.utils.db.jdbcmodel.ColumnVO;
import lht.wangtong.core.utils.db.jdbcmodel.DbTypeEnum;
import lht.wangtong.core.utils.db.jdbcmodel.SqlDataTypeEnum;
import lht.wangtong.core.utils.db.jdbcmodel.TableVO;
import lht.wangtong.core.utils.db.objmodel.Association;
import lht.wangtong.core.utils.db.objmodel.Column;
import lht.wangtong.core.utils.db.objmodel.PrimaryKey;
import lht.wangtong.core.utils.db.objmodel.SchemaModel;
import lht.wangtong.core.utils.db.objmodel.Table;
import lht.wangtong.core.utils.db.tool.Quoting;

/**
 * Finds associations and tables by analyzing the JDBC meta data.
 * 实现
 * @author aibozeng
 */
public class JDBCDatabaseMetaDataFinder implements ModelElementFinder {
	public Logger logger = Logger.getLogger(this.getClass());
    /**
     * The logger.
     */
    private static final Logger _log = Logger.getLogger(JDBCDatabaseMetaDataFinder.class);

    /**
     * Set of sql types (uppercase) not listed in {@link Types} which needs a length argument.
     */
    private final Set<String> typesWithLength = new HashSet<String>();
    {
    	typesWithLength.add("NVARCHAR2");
    	typesWithLength.add("NCHAR");
    	typesWithLength.add("RAW");
    }
 
    public DbTypeEnum findDbType(Connection connection)  throws Exception{
		DbTypeEnum dbms = DbTypeEnum.UNKNOWN;
		try {
			DatabaseMetaData meta = connection.getMetaData();
			String productName = meta.getDatabaseProductName();
			if (productName != null) {
				if (productName.toUpperCase().contains("ORACLE")){
					dbms = DbTypeEnum.ORACLE;					
				}else if (productName.toUpperCase().contains("DB2")){
					dbms = DbTypeEnum.DB2;
				}else if (productName.toUpperCase().contains("POSTGRES")){
					dbms = DbTypeEnum.POSTGRESQL;
				}else if (productName.toUpperCase().contains("MYSQL")){
					dbms = DbTypeEnum.MySQL;
				}else if(productName.toUpperCase().contains("SQLITE")){
					dbms = DbTypeEnum.SQLITE;
				}else if (productName.toUpperCase().contains("ADAPTIVE SERVER")){
					dbms = DbTypeEnum.SYBASE;
				}else if (productName.toUpperCase().contains("HSQL")){
					dbms = DbTypeEnum.HSQL;
				}else if (productName.toUpperCase().equals("ASE")){
					dbms = DbTypeEnum.SYBASE;
				}
			}
			//_log.info("DB name:        " + productName + " (" + dbms + ")");
			//_log.info("DB version:     " + meta.getDatabaseProductVersion());
		} catch (Exception e) {
		}
		return dbms;
	}

	/**
     * Finds all non-empty schemas in DB.
     * 
     * @param session the statement executor for executing SQL-statements
     * @param userName schema with this name may be empty
     */ 
    public List<String> findAllSchema(Connection connection, String userName) throws Exception {
    	List<String> schemas = new ArrayList<String>();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet rs = metaData.getSchemas();
			while (rs.next()) {
				String schema = rs.getString("TABLE_SCHEM").trim();
				if (schema != null) {
					if (findDbType(connection) == DbTypeEnum.POSTGRESQL && schema.startsWith("pg_toast_temp")) {
						continue;
					}
					schemas.add(schema);
				}
			}
			rs.close();
		} catch (SQLException e) {
			logger.error(e);
			if (userName != null) {
				schemas.add(userName);
			}
		}
		return schemas;
    }
    
    /**
     * 获取 # 前面的字符串
     * @param source
     * @param preStr
     * @return
     */
    public String fetchPreStr(String source , String preStr,String defaultVal){
    	if(source==null){
    		return defaultVal;
    	}
    	int i = source.indexOf(preStr);
    	if(i>0){
    		return source.substring(0,i);
    	}
    	return defaultVal;
    }
    
    public List<TableVO> findTableNames(Connection connection,String schemaPattern,String tableNamePattern)  throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
        Quoting quoting = new Quoting(metaData);
        List<TableVO> vos = new ArrayList<TableVO>();
        //1.获取表的名称
        ResultSet resultSet;
        _log.info("exec findTableNames()");
        _log.info("schemaPattern="+schemaPattern);
        _log.info("tableNamePattern="+tableNamePattern);
        resultSet = metaData.getTables(null,schemaPattern,tableNamePattern, new String[] { "TABLE" });
        while (resultSet.next()) {
            if ("TABLE".equalsIgnoreCase(resultSet.getString("TABLE_TYPE"))) {
            	String tableName = resultSet.getString("TABLE_NAME");
                if (isValidName(tableName)) {
                	tableName = quoting.quote(tableName);
                	TableVO vo = new TableVO();
                	vo.setName(tableName);
                	vo.setCatalogName(resultSet.getString("TABLE_CAT"));
                	vo.setSchemaName(resultSet.getString("TABLE_SCHEM"));
                	vo.setRemark(resultSet.getString("REMARKS"));
                	vo.setAbbr(fetchPreStr(vo.getRemark(),"#",vo.getName()));
                	vos.add(vo);
                	_log.info("found table " + tableName);
                } else {
                	_log.info("skip table " + tableName);
                }
            }
        }
        resultSet.close();  
        _log.info("find table size ="+ vos.size());
        return vos;
    }
    
    public List<ColumnVO> findColumnNames(Connection connection,String schemaName , String tableName,String columnNamePattern) throws Exception{
       	List<ColumnVO> columns = new ArrayList<ColumnVO>();
    	DatabaseMetaData metaData = connection.getMetaData();
    	//Quoting quoting = new Quoting(metaData);    	
    	_log.info("begin to find columns for "+schemaName+","+tableName+","+columnNamePattern);
    	ResultSet resultSet = metaData.getColumns(null, schemaName, tableName,columnNamePattern==null?"%":columnNamePattern);
    	//TODO 对于oracle NVARCHAR2 类型，jdbc取出来的长度是翻倍的，如：定义 NVARCHAR2(32) 时，getInt("COLUMN_SIZE")结果为 64
    	while (resultSet.next()) {
            //String colName = quoting.quote(resultSet.getString("COLUMN_NAME"));
            String colName = resultSet.getString("COLUMN_NAME");
            int type = resultSet.getInt("DATA_TYPE");
            int length = 0;
            int precision = -1;
            if (type == Types.NUMERIC || type == Types.DECIMAL || type == Types.VARCHAR || type == Types.CHAR) {
                length = resultSet.getInt("COLUMN_SIZE");
            }
            if (type == Types.NUMERIC || type == Types.DECIMAL || type == Types.VARCHAR || type == Types.CHAR) {
                precision = resultSet.getInt(9);
                if (resultSet.wasNull() || precision == 0) {
                	precision = -1;
                }
            }
            String sqlTypeName = toSqlType(resultSet.getString("TYPE_NAME"),findDbType(connection));
            if (sqlTypeName == null || sqlTypeName.trim().length() == 0 || resultSet.wasNull()) {
            	//转换不到，使用枚举类
            	sqlTypeName = SqlDataTypeEnum.getCodeById(type);
                if (sqlTypeName == null) {
                	continue;
                	// throw new RuntimeException("unknown SQL type: " + type);
                }
            }
            if (typesWithLength.contains(sqlTypeName.toUpperCase()) || type == Types.NUMERIC || type == Types.DECIMAL || type == Types.VARCHAR || type == Types.CHAR || type == Types.BINARY || type == Types.VARBINARY) {
                length = resultSet.getInt("COLUMN_SIZE");
                if (type == Types.VARCHAR) {
//                	if (Configuration.forDbms(session).getVarcharLengthLimit() != null) {
//                		length = Math.min(length, Configuration.forDbms(session).getVarcharLengthLimit());
//                	}
                	//length 的设置
                }
            }
            if (sqlTypeName != null && sqlTypeName.equalsIgnoreCase("uniqueidentifier")) {
            	length = 0;
            }
            if (type == Types.NUMERIC || type == Types.DECIMAL || type == Types.VARCHAR || type == Types.CHAR) {
                precision = resultSet.getInt(9);
                if (resultSet.wasNull() || precision == 0) {
                	precision = -1;
                }
            }
            if (type == Types.DISTINCT) {
            	length = 0;
            	precision = -1;
            }
            ColumnVO vo = new ColumnVO();
            vo.setName(colName);
            vo.setCatalogName(resultSet.getString("TABLE_CAT"));
            //vo.setIdentityColumn();
            vo.setLength(length);
            vo.setPrecision(precision);
            vo.setRemark(resultSet.getString("REMARKS"));
            vo.setSchemaName(resultSet.getString("TABLE_SCHEM"));
            vo.setTableName(resultSet.getString("TABLE_NAME"));
            vo.setType(sqlTypeName);
            vo.setTypeName(resultSet.getString("TYPE_NAME"));
            vo.setRemark(resultSet.getString("REMARKS"));
            vo.setAbbr(fetchPreStr(vo.getRemark(),"#",vo.getName()));
            int nullable = resultSet.getInt("NULLABLE");
            vo.setNullable(nullable==0?false:true);
            vo.setDefValue(resultSet.getString("COLUMN_DEF"));
            columns.add(vo);
        }
        resultSet.close();
        _log.info("found columns for table " + tableName+",size="+columns.size());
        return columns;    	
    }
    
    public List<ColumnVO> findPrimaryKeyNames(Connection connection,String schemaPattern , String tableNamePattern) throws Exception{
    	DatabaseMetaData metaData = connection.getMetaData();
        List<ColumnVO> vos = new ArrayList<ColumnVO>();
        //1.获取列表
        ResultSet resultSet;
        resultSet = metaData.getPrimaryKeys(null,schemaPattern,tableNamePattern);
        while (resultSet.next()) {
        	ColumnVO vo = new ColumnVO();
        	vo.setCatalogName(resultSet.getString("TABLE_CAT"));
        	vo.setSchemaName(resultSet.getString("TABLE_SCHEM"));
        	vo.setTableName(resultSet.getString("TABLE_NAME"));
        	vo.setName(resultSet.getString("COLUMN_NAME"));//主键对应的列名  id
        	vo.setAbbr(resultSet.getString("PK_NAME"));//主键的命名, 如：primary_key_user_id
        	vos.add(vo);
        }
        resultSet.close();    	
        return vos;
    }
    
    public List<AssociationVO> findImportedKeys(Connection connection,String catalog, String schema,String table)  throws Exception {
        DatabaseMetaData metaData = connection.getMetaData();
    	ResultSet resultSet = null;
    	List<AssociationVO> associationVOs = new ArrayList<AssociationVO>();
    	try {
    		resultSet = metaData.getImportedKeys(catalog, schema, table);
    		fillImportOrExportKey(resultSet,associationVOs);
    	} catch (Exception e) {
    		_log.info("failed. " + e.getMessage());
    	} finally{
    		resultSet.close();
    	}
		return associationVOs;
    }

    public List<AssociationVO> findExportedKeys(Connection connection,String catalog, String schema,String table)  throws Exception {
    	DatabaseMetaData metaData = connection.getMetaData();
    	ResultSet resultSet = null;
    	List<AssociationVO> associationVOs = new ArrayList<AssociationVO>();
    	try {
    		resultSet = metaData.getExportedKeys(catalog, schema, table);
    		fillImportOrExportKey(resultSet,associationVOs);
    	} catch (Exception e) {
    		_log.info("failed. " + e.getMessage());
    	} finally{
    		resultSet.close();
    	}
		return associationVOs;
    }
    
    
    private void fillImportOrExportKey(ResultSet resultSet , List<AssociationVO> associationVOs)   throws Exception{
        while (resultSet.next()) {
        	AssociationVO vo = new AssociationVO();
        	vo.setPkCatalogName(resultSet.getString("PKTABLE_CAT"));
        	vo.setPkSchemaName(resultSet.getString("PKTABLE_SCHEM"));
        	vo.setPkTableName(resultSet.getString("PKTABLE_NAME"));
        	vo.setPkColumnName(resultSet.getString("PKCOLUMN_NAME"));
        	vo.setFkCatalogName(resultSet.getString("FKTABLE_CAT"));
        	vo.setFkSchemaName(resultSet.getString("FKTABLE_SCHEM"));
        	vo.setFkTableName(resultSet.getString("FKTABLE_NAME"));
        	vo.setFkColumnName(resultSet.getString("FKCOLUMN_NAME"));
        	vo.setKeySeq(resultSet.getShort("KEY_SEQ"));
        	vo.setFkName(resultSet.getString("FK_NAME"));
        	vo.setPkName(resultSet.getString("PK_NAME"));
        	associationVOs.add(vo);
        }    	
    }

  
    public List<Table> buildTables(Connection connection,String schemaPattern,String tableNamePattern) throws Exception {
        //PrimaryKeyFactory primaryKeyFactory = new PrimaryKeyFactory();        
    	List<Table> tables = new ArrayList<Table>();
        //1.获取表的名称
        List<TableVO> tableVOs = this.findTableNames(connection, schemaPattern, tableNamePattern);
        //2.构造表对象，
        for (TableVO tableVO: tableVOs) {
        	Table tmp = new Table(tableVO);
            tables.add(tmp);
        }
        return tables;
    }

    @Override
    public List<Table> buildCompleteTables(Connection connection,String schemaPattern) throws Exception{
    	List<Table> tables = new ArrayList<Table>();
        //1.获取表的名称
        List<TableVO> tableVOs = this.findTableNames(connection, schemaPattern, "%");
        
        //2.构造表对象，
        for (TableVO tableVO: tableVOs) {
        	Table tmp = new Table(tableVO);
        	//(1).构造出每个表下的所有字段列
            List<ColumnVO> columnVOs = this.findColumnNames(connection, tableVO.getSchemaName(),tableVO.getName(),"%");
            for(ColumnVO colVO : columnVOs){
            	Column column = new Column(tmp,colVO);
            	tmp.addColumn(column);
            }        	        	
        	//(2).构造出每个表下的关键字段   -- 测试时，这一步运行时间比较长
            List<ColumnVO> primaryKeyVOs = this.findPrimaryKeyNames(connection, tableVO.getSchemaName(),tableVO.getName());
            if(primaryKeyVOs==null || primaryKeyVOs.size()<=0){
            	//没有关键字字段
            }else{
            	PrimaryKey key = new PrimaryKey();
	            for(ColumnVO colVO : primaryKeyVOs){
	            	//列对象已经在前面构建，只需要找出
	            	Column colKey = tmp.findColumn(colVO.getName());
	            	if(colKey!=null){
	            		key.addColumn(colKey);
	            	}
	            }
	            tmp.setPrimaryKey(key);	            
            }
            //(3).构造唯一性约束    getIndexInfo()            
            tables.add(tmp);
        }
        //3.构造表与表之间的关系 --- 必须在所有表和字段的对象构建完之后。否则，外键关系关联的表对象可能还没有建立。
        for(Table table : tables){
        	//(3.1)先找本身有哪些字段(fk)依赖其他表的主键
        	List<AssociationVO> importKeyVOs = findImportedKeys(connection, table.getCatalogName(),table.getSchemaName(),table.getName());
        	for(AssociationVO vo : importKeyVOs){
        		if(!vo.getFkTableName().equals(table.getName())){
        			_log.warn("查找到的描述信息中表名不一致, table name="+table.getName()+", fkTableName="+vo.getFkTableName());
        			continue;
        		}
        		//找出关系表的对象
        		Table pkTable = findTableByName(tables,vo.getPkTableName());
        		if(pkTable==null){
        			_log.warn("描述信息中主键表没有发现,可能此次构建参数限制了表名  pktable name="+vo.getPkTableName());
        			
        			continue;
        		}
        		Column pkColumn = pkTable.findColumn(vo.getPkColumnName());
        		if(pkColumn==null){
        			_log.warn("描述信息中主键字段没有发现, pktable name="+vo.getPkTableName()+",pkColumn="+vo.getPkColumnName());
        			continue;        			
        		}
        		Column fkColumn = table.findColumn(vo.getFkColumnName());
        		if(fkColumn==null){
        			_log.warn("描述信息中外键字段没有发现, fktable name="+vo.getFkTableName()+",fkColumn="+vo.getFkColumnName());
        			continue;        			
        		}
        		Association associationImport = new Association(table,fkColumn,pkTable,pkColumn);
        		table.getImportKeys().add(associationImport);
        		//同时把 关系表的 exportKey List放置关系即可。        		
        		pkTable.getExportKeys().add(associationImport);
        	}
        }
        _log.info(" find complete table info ok.");
        return tables;
    	
    }
    
    public List<Table> buildCompleteTablesFaster(Connection connection,String schemaPattern) throws Exception{
    	List<Table> tables = new ArrayList<Table>();
        //1.获取表的名称
        List<TableVO> tableVOs = this.findTableNames(connection, schemaPattern, "%");
        List<ColumnVO> columnVOs = this.findColumnNames(connection, schemaPattern,"%","%");
        //2.构造表对象，
        for (TableVO tableVO: tableVOs) {
        	Table tmp = new Table(tableVO);
        	//(1).构造出每个表下的所有字段列
          
            for(ColumnVO colVO : columnVOs){
            	if(tableVO.getName().equals(colVO.getTableName())){
            	Column column = new Column(tmp,colVO);
            	tmp.addColumn(column);
            	}
            }        	        	
        	//(2).构造出每个表下的关键字段   -- 测试时，这一步运行时间比较长
            List<ColumnVO> primaryKeyVOs = this.findPrimaryKeyNames(connection, tableVO.getSchemaName(),tableVO.getName());
            if(primaryKeyVOs==null || primaryKeyVOs.size()<=0){
            	//没有关键字字段
            }else{
            	PrimaryKey key = new PrimaryKey();
	            for(ColumnVO colVO : primaryKeyVOs){
	            	//列对象已经在前面构建，只需要找出
	            	Column colKey = tmp.findColumn(colVO.getName());
	            	if(colKey!=null){
	            		key.addColumn(colKey);
	            	}
	            }
	            tmp.setPrimaryKey(key);	            
            }
            //(3).构造唯一性约束    getIndexInfo()            
            tables.add(tmp);
        }
        //3.构造表与表之间的关系 --- 必须在所有表和字段的对象构建完之后。否则，外键关系关联的表对象可能还没有建立。
        for(Table table : tables){
        	//(3.1)先找本身有哪些字段(fk)依赖其他表的主键
        	List<AssociationVO> importKeyVOs = findImportedKeys(connection, table.getCatalogName(),table.getSchemaName(),table.getName());
        	for(AssociationVO vo : importKeyVOs){
        		if(!vo.getFkTableName().equals(table.getName())){
        			_log.warn("查找到的描述信息中表名不一致, table name="+table.getName()+", fkTableName="+vo.getFkTableName());
        			continue;
        		}
        		//找出关系表的对象
        		Table pkTable = findTableByName(tables,vo.getPkTableName());
        		if(pkTable==null){
        			_log.warn("描述信息中主键表没有发现,可能此次构建参数限制了表名  pktable name="+vo.getPkTableName());
        			
        			continue;
        		}
        		Column pkColumn = pkTable.findColumn(vo.getPkColumnName());
        		if(pkColumn==null){
        			_log.warn("描述信息中主键字段没有发现, pktable name="+vo.getPkTableName()+",pkColumn="+vo.getPkColumnName());
        			continue;        			
        		}
        		Column fkColumn = table.findColumn(vo.getFkColumnName());
        		if(fkColumn==null){
        			_log.warn("描述信息中外键字段没有发现, fktable name="+vo.getFkTableName()+",fkColumn="+vo.getFkColumnName());
        			continue;        			
        		}
        		Association associationImport = new Association(table,fkColumn,pkTable,pkColumn);
        		table.getImportKeys().add(associationImport);
        		//同时把 关系表的 exportKey List放置关系即可。        		
        		pkTable.getExportKeys().add(associationImport);
        	}
        }
        _log.info(" find complete table info ok.");
        return tables;
    	
    }
    private Table findTableByName(List<Table> tables,String tableName){
    	for(Table table : tables){
    		if(table.getName().equals(tableName)){
    			return table;
    		}
    	}
    	return null;
    }

    public SchemaModel buildSchemaModel(Connection connection) throws Exception{
    	return buildSchemaModel(connection,connection.getMetaData().getUserName());
    }
    
    public SchemaModel buildSchemaModel(Connection connection,String schemaName) throws Exception{
    	SchemaModel model = new SchemaModel(schemaName);
    	//1.判断 schema是否存在？
    	//2.构建表和字段
    	 List<Table> tables = buildCompleteTablesFaster(connection, schemaName);
    	 for(Table table:tables){
    		 model.addTable(table);
    	 }
    	//3.构造视图
    	 //TODO
    	//4.构造存储过程
    	 //TODO
    	return model;
    }
    
    public List<Column> buildColumns(Connection connection,Table table) throws Exception{
    	//(1).构造出每个表下的所有字段列
        List<ColumnVO> columnVOs = findColumnNames(connection, table.getSchemaName(),table.getName(),"%");
        for(ColumnVO colVO : columnVOs){
        	Column column = new Column(table,colVO);
        	table.addColumn(column);
        }        	   
        _log.info("Table "+table.getName()+" find column size ="+columnVOs.size());
    	return table.getColumns();
    }
    
 	/**
     * Checks syntactical correctness of names.
     * 
     * @param name a table or column name
     * @return <code>true</code> if name is syntactically correct
     */
    private boolean isValidName(String name) {
		return name != null && !name.contains("$");
	}


	/**
     * Gets default schema of DB.
     * 
     * @param session the statement executor for executing SQL-statements
     * @param userName schema with this name may be empty
     */ 
    public String findDefaultSchema(Connection connection, String userName) throws Exception {
    	List<String> schemas = new ArrayList<String>();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			String dbName = metaData.getDatabaseProductName();
			boolean isPostgreSQL = dbName != null && dbName.toLowerCase().contains("PostgreSQL".toLowerCase());
			boolean isH2Sql = dbName != null && dbName.toLowerCase().startsWith("H2".toLowerCase());
			ResultSet rs = metaData.getSchemas();
			while (rs.next()) {
				schemas.add(rs.getString("TABLE_SCHEM"));
			}
			rs.close();
			String userSchema = null;
			for (Iterator<String> i = schemas.iterator(); i.hasNext(); ) {
				String schema = i.next().trim();
				if ((isPostgreSQL || isH2Sql) && "public".equalsIgnoreCase(schema)) {
					return schema;
				}
				if (!schema.equalsIgnoreCase(userName.trim())) {
					rs = metaData.getTables(null, schema, "%", new String[] { "TABLE" });
					if (!rs.next()) {
						i.remove();
					}
					rs.close();
				} else {
					userSchema = schema;
				}
			}
			if (userSchema != null) {
				return userSchema;
			}
			return userName;
		} catch (SQLException e) {
			logger.error(e);
			return userName;
		}
    }
    
    /**
     * Filter the length attribute of a column in a DBMS specific way.
     * 
     * @param length the length as given from driver
     * @param the type name
     * @param type the sql type
     * @param dbms the DBMS
     * @return filtered length
     */
    private int filterLength(int length, String typeName, int type, DbTypeEnum dbms, int origLength) {
    	if (length > 0) {
    		if (dbms == DbTypeEnum.POSTGRESQL) {
    			if (type == Types.VARCHAR && length >= 10485760) {
    				length = 0;
    			} else if (type == Types.NUMERIC && length > 1000) {
    				length = 0;
    			} else if ("bytea".equalsIgnoreCase(typeName)) {
    				length = 0;
    			}
    		} else if (dbms == DbTypeEnum.SQLITE) {
    			return 0;
    		}
    	} else {
    		if (dbms == DbTypeEnum.POSTGRESQL) {
    			if ("bit".equalsIgnoreCase(typeName)) {
    				length = origLength;
    			}
    		}
    	}
		return length;
	}

	/**
     * Converts result from {@link DatabaseMetaData#getColumns(String, String, String, String)}
     * into the type name.
     */
    private String toSqlType(String sqlTypeName, DbTypeEnum dbms) {
    	if (sqlTypeName == null) {
    		return null;
    	}
    	sqlTypeName = sqlTypeName.trim();

    	if (dbms == DbTypeEnum.MySQL) {
    		if (sqlTypeName.equalsIgnoreCase("SET") || sqlTypeName.equalsIgnoreCase("ENUM")) {
    			return "VARCHAR";
    		}
    	}
    	if (!sqlTypeName.toLowerCase().endsWith(" identity")) {
	    	// Some drivers (MS SQL Server driver for example) prepends the type with some options,
	    	// so we ignore everything after the first space.
	    	int i = sqlTypeName.indexOf(' ');
	    	if (i > 0) {
	    		sqlTypeName = sqlTypeName.substring(0, i);
	    	}
	    	i = sqlTypeName.indexOf('(');
	    	if (i > 0) {
	    		sqlTypeName = sqlTypeName.substring(0, i);
	    	}
    	}
		return sqlTypeName;
	}
    
    @Override
	public List<String> createDeleteSql(Connection connection, Table table , Long pkValue )  throws Exception{
		logger.debug("begin to find table name="+table.getName()+",pkValue="+pkValue);
		List<String> sqlDeletes = new ArrayList<String>();
		//曾经被遍历过的表名+id
		Set<String> hasFoundTableAndIds = new HashSet<String>();
		//1.本身的记录要被删除		
		sqlDeletes.add("delete from "+table.getName()+" where "+ table.getPrimaryKey().getOneKey().getName() +" =  "+pkValue);
		hasFoundTableAndIds.add(table.getName()+pkValue);
		//2.遍历被关联对象
		if(table.getExportKeys().size()>0){
		     createDeleteByExportKey(connection,sqlDeletes,table.getExportKeys(),pkValue,hasFoundTableAndIds);
		}else{
			//没有被关联，只需要删除自己本身记录
		}
		return sqlDeletes;
	}
    
	private void createDeleteByExportKey(Connection connection, List<String> sqls , List<Association> associationExportKeys , Long fkValue,Set<String> hasFoundTableAndIds)  throws Exception{
		for(Association ass : associationExportKeys ){
			Column col = ass.getFkColumn();
			Table fkTable = ass.getFkTable();
			String sql;
			List<Long> pkLongs = new ArrayList<Long>();
			//先找出该表是否存在外键值的记录
			if(fkTable.getPrimaryKey() != null && fkTable.getPrimaryKey().getOneKey()!=null){
				//有主键
				pkLongs =  findPkValuesByFkValue(connection,fkTable,fkTable.getPrimaryKey().getOneKey(),col,fkValue);
				if(pkLongs==null || pkLongs.size()<=0){
					//被关联的表不存在记录,无需生成语句
					continue;
				}
			}else{
				//没有主键字段(这个表可能比较特殊，一般情况不会出现)
			}
			if(col.isNullable()){
				//置为空即可 , 暂不判断 主键的数据类型    如果是字符串,SQL语句要加上 ''
				 sql = "update "+fkTable.getName()+" set "+ col.getName() +" = null where "+ col.getName() +" =  "+fkValue;
				sqls.add("update "+fkTable.getName()+" set "+ col.getName() +" = null where "+ col.getName() +" =  "+fkValue);
				logger.debug(sql);
				//结束条件1
				continue;
			}else{
				sql = "delete "+fkTable.getName()+" where "+ col.getName() +" =  "+fkValue;
				sqls.add("delete "+fkTable.getName()+" where "+ col.getName() +" =  "+fkValue);
				logger.debug(sql);
				//继续找下级依赖关系
				if(fkTable.getExportKeys().size()<=0){
					//子表没有被关联了, 结束条件2
					continue;
				}
				//子表有被关联了，先找出子表的主键值
				for(Long pk : pkLongs){
					if(hasFoundTableAndIds.contains(fkTable.getName()+pk)){
						//避免死循环
						continue;
					}			
					hasFoundTableAndIds.add(fkTable.getName()+pk);
					//针对每一个主键值, 找孙子依赖
					createDeleteByExportKey(connection,sqls,fkTable.getExportKeys(),pk,hasFoundTableAndIds);
					
				}				
			}
			
			
		}
		
	}    

    @Override
	public List<String> createTruncateTableSql(Table table ,Set<String> hasFoundTables )  throws Exception{
		logger.debug("begin to find table name="+table.getName());
		List<String> sqlDeletes = new ArrayList<String>();		
		if(hasFoundTables.contains(table.getName())){
			//已经遍历过,判断是否可以为空
			return sqlDeletes;
		}
		//1.本身的记录要被删除		
		sqlDeletes.add("delete from "+table.getName());
		hasFoundTables.add(table.getName());
		//2.遍历被关联对象
		if(table.getExportKeys().size()>0){
		     createTruncateTableByExportKey(sqlDeletes,table.getExportKeys(),hasFoundTables);
		}else{
			//没有被关联，只需要删除自己本身记录
		}
		return sqlDeletes;
	}
	
    
	private void createTruncateTableByExportKey(List<String> sqls , List<Association> associationExportKeys,Set<String> hasFoundTables )  throws Exception{
		for(Association ass : associationExportKeys){
			Table fkTable = ass.getFkTable();
			Column col = ass.getFkColumn();
			if(col.isNullable()){
				//可空，累加一个update语句
				sqls.add("update "+fkTable.getName()+" set "+ col.getName() +" = null ");
			}
			if(hasFoundTables.contains(fkTable.getName())){
				//已经遍历过
				continue;
			}
			sqls.add("delete from "+fkTable.getName());
			hasFoundTables.add(fkTable.getName());
			//继续找下级依赖关系
			if(fkTable.getExportKeys().size()<=0){
				//子表没有被关联了, 结束条件2
				continue;
			}
			createTruncateTableByExportKey(sqls,fkTable.getExportKeys(),hasFoundTables);				
		}		
	}
	

	
    private List<Long> findPkValuesByFkValue(Connection connection , Table table , Column pkColumn , Column fkColumn , Long fkValue ) throws Exception{
    	List<Long> pkVals = new ArrayList<Long>();
    	String sql = "select "+ pkColumn.getName() +" from "+table.getName()+" where "+ fkColumn.getName() +" =  "+ fkValue;
    	PreparedStatement ps = connection.prepareStatement(sql);
    	ResultSet resultSet = ps.executeQuery();
    	while(resultSet.next()){
    		Object obj = resultSet.getObject(1);
    		if(obj instanceof Long){
    			pkVals.add((Long)obj);
    		}else if(obj instanceof BigDecimal){
    			pkVals.add(((BigDecimal)obj).longValue());
    		}else{
    			pkVals.add(0L);
    		}
    	}
    	resultSet.close(); 
    	ps.close();
    	return pkVals;
    }
    
    

	/**
     * Gets description.
     */
    public String toString() {
    	return "JDBC based model element finder";
    }
    
}
