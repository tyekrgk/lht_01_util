package lht.wangtong.core.utils.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertyUtils {
	
	public static Logger logger = Logger.getLogger(PropertyUtils.class.getClass());

	private static String resourcePath;//properties文件位置
	private static Properties configProperties = System.getProperties();
	public static String getResourcePath() {
		return resourcePath;
	}
	public static Properties getConfigProperties() {
		return configProperties;
	}
	static{
		int pos=0;
		URL url = PropertyUtils.class.getResource("PropertyUtils.class");
		String pkgName = PropertyUtils.class.getPackage().getName();
		do{
			pos = pkgName.indexOf(".");
			if(pos>0){
				pkgName = pkgName.substring(0,pos)+"/"+pkgName.substring(pos+1);
			}
		}while(pos>0);
		String filePath = url.getFile();
		String appPath = filePath.substring(0,filePath.indexOf("/classes/"+pkgName));
		resourcePath = appPath+"/commonfiles.properties";//引号中部分就是你资源文件的包和名称
		try {
			configProperties.load(new FileInputStream(PropertyUtils.getResourcePath()));
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	/**
	 * 根据Name 获取Properties的参数值
	 * @param name
	 * @return
	 * @Author MaBingYang
	 */
	public static String getProperty(String name){
		return getConfigProperties().getProperty(name);
	}
	
	public static boolean isfileExists(){
		File distfile = new File(resourcePath);
		return  distfile.exists();
	}
	
}
