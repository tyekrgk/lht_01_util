package lht.wangtong.core.utils.sql;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;



/**
 * 
 * 链接需要对接的系统的数据库
 * 
 * @author aibozeng
 *
 */
public class DataSourceTool {
	 public static Logger logger = Logger.getLogger(DataSourceTool.class.getClass());
	//易桥系统的DB
	private static String ET_DS = "jdbc/OracleDS4ET";
	
	public static DataSource getDataSource(){
		return getDataSource(ET_DS);
	}

	/**
	 * 根据 ds名字，得到DateSource
	 * @param ds_name
	 * @return
	 */
	public static DataSource getDataSource(String ds_name){
		DataSource ds = null; 
		InitialContext context=null;
		try {
			context = new InitialContext();           //JNDIPrefix           
		} catch (NamingException e) {
			logger.error(e);
		}
		try{
			ds = (DataSource) context.lookup(ds_name);
		}
		catch(Exception e){				
			logger.debug("lookup "+ds_name+" is not found");
			ds = null;
		}
		try{
			if(ds==null){
				logger.debug("begin to find java:/"+ds_name);
				ds = (DataSource) context.lookup( "java:/"+ds_name );				
			}
		}
		catch(Exception e){				
			logger.debug("lookup java:/"+ds_name+" is not found");
		}
		if(ds!=null){
			logger.debug("ok."+ds);
		}
		return ds;
	}
	
	/**
	 * 根据默认的 "jdbc/OracleDS4ET" 创建一个连接
	 * @return
	 */
	public static Connection getConnection(){
		DataSource ds = getDataSource();
		if(ds==null){
			logger.debug("ds is null");
			return null;
		}
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 根据传入的ds 创建一个连接
	 * @param ds_name
	 * @return
	 */
	public static Connection getConnection(String ds_name){
		DataSource ds = getDataSource(ds_name);
		if(ds==null){
			logger.debug("ds is null");
			return null;
		}
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
		conn = DataSourceTool.getConnection();
		logger.debug("read conn is "+conn);
	}

}
