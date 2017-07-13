package lht.wangtong.core.utils.file;

import java.io.File;


import org.apache.log4j.Logger;

/**
 * 文件处理
 * @author lyl
 *
 */
public class Common {

	 public static Logger logger = Logger.getLogger(Common.class.getClass());
	
	/**
	 * 判断文件路径是否存在
	 * @param path
	 * @return
	 */
	public static boolean markDir(String path) {
		File dirFile  = null ;
		try  {
            dirFile = new File(path);
             if (!(dirFile.exists()) && !(dirFile.isDirectory()))  {
                 boolean creadok = dirFile.mkdirs();
                 if (creadok) {
                    return true;
                } else {
                	return false;
                } 
            }
         } catch (Exception e)  {
            logger.error(e);
            return false;
        } 
        return true;
	}
}
