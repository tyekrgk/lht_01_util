package lht.wangtong.core.utils.lang;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

public class StringTool {
	public static Logger logger = Logger.getLogger(StringTool.class.getClass());
	/**
	 * 分隔字符  , 注意：如果前后有分隔符，String.split会多出来一个。该方法自动去掉前后分隔符再调用 String.split
	 *            注意：特殊字符 $ % 等，需要使用 转义   $, 改为 \\$ 
	 * aibo zeng 2009-06-09
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String[] split(String str , char ch){
		if(str==null){
			return null;
		}
		if(str.charAt(0)==ch){
			str = str.substring(1);
		}
		if(str.charAt(str.length()-1)==ch){
			str = str.substring(0,str.length()-1);
		}
		return str.split("\\" + String.valueOf(ch));
	}
	
	/**
	 * 自动拼装like语句的字符串，不支持正则表达式的匹配方式。如果model为真，则在前后加上%%，
	 * 		如果model为FALSE，则根据传入的值来判断，如果传入的值中含有通配符，则直接返回字符串，如果不包含通配符，则加上%%
	 * @param str 传入的字符串
	 * @param model 模型，是否一直添加 %%
	 * @return String
	 */
	public static String likeStr(String str,boolean model){
		if (str == null){
			return null;
		}
		if (model){
			return "%" + str.trim() + "%";
		}
		if (str.indexOf("%") != -1 || str.indexOf("_") != -1){
			return str.trim();
		}
		return "%" + str.trim() + "%";
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] strArg = StringTool.split("$1%WX0102$0000000000000001$25036671$", '$');
		logger.debug(strArg.length);
		for(int i=0;i<strArg.length;i++){
			logger.debug(strArg[i]);
		}
		/*String[] strArg = "1$WX0102$0000000000000001$25036671".split("\\$");
		logger.debug(strArg.length);
		for(int i=0;i<strArg.length;i++){
			logger.debug(strArg[i]);
		}*/

	}

	/**
	 * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
	 */
	public static boolean isEmpty(String str){
		return str==null || str.length()==0 || str.trim().length()==0;
	}
	
	/**
	 * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
	 */
	public static boolean isNotEmpty(String str){
		return (str!=null && !"".equals(str.trim()));
	}
	
	public static boolean isNotEmpty(Long o){
		return (o!=null);
	}
	public static boolean isNotEmpty(Integer o){
		return (o!=null );
	}	
	public static boolean isNotEmpty(Date o){
		return (o!=null );
	}
	
	public static boolean isNotEmpty(BigDecimal o){
		return (o!=null );
	}
	
	public static boolean isNotEmpty(Object o){
		return (o!=null );
	}
	
	public static String replaceNull(String o){
		if(o==null){
			o="";
		}
		return o;
	}
	public static String replaceNull(Object o){
		if(o==null){
			return "";
		}
		return o.toString();
	}
}
