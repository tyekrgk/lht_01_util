package lht.wangtong.core.utils.str;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class StringUtil {
	
	 public static Logger logger = Logger.getLogger(StringUtil.class.getClass());

	private static int STRING_LENGTH = 10;

	/**
	 * 判断一个字符是Ascii字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param char c, 需要判断的字符
	 * @return boolean 如果是Ascii字符则返回true，否则返回false
	 */
	private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 * 
	 * @param String
	 *            s ,需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int length(String s) {
		if (s == null) {
			return 0;
		}
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
				len++;
			}
		}
		return len;
	}

	/**
	 * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	 * 
	 * @param String
	 *            origin, 原始字符串
	 * @param int len, 截取长度(一个汉字长度按2算的)
	 * @return String 返回的字符串
	 */
	public static String substring(String origin, int len) {
		if (origin == null || origin.equals("") || len < 1) {
			return "";
		}
		if (len > length(origin)) {
			return origin;
		}
		int k = 0;
		String temp = "";
		for (int i = 0; i < origin.length(); i++) {
			byte[] b = {};
			try {
				b = (origin.charAt(i) + "").getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
			k = k + b.length;
			if (k > len) {
				break;
			}
			temp = temp + origin.charAt(i);
		}
		return temp;
	}

	// 截取字符串长度(中文2个字节，半个中文显示一个)
	public static String subTextString(String str, int len) {
		if (str.length() < len / 2)
			return str;
		int count = 0;
		StringBuffer sb = new StringBuffer();
		String[] ss = str.split("");
		for (int i = 1; i < ss.length; i++) {
			count += ss[i].getBytes().length > 1 ? 2 : 1;
			sb.append(ss[i]);
			if (count >= len)
				break;
		}
		return (sb.toString().length() < str.length()) ? sb.append("...")
				.toString() : str;
	}

	public static String substring(String origin) {
		if (origin == null || origin.equals("")) {
			return "";
		}
		if (STRING_LENGTH > length(origin)) {
			return origin;
		}
		return substring(origin, STRING_LENGTH) + "...";
	}

	public static boolean isNullOrEmpty(String sourceStr) {
		return sourceStr == null || sourceStr.trim().equals("");
	}

	public static String[] split(String src , char c){
		List<String> strList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<src.length();i++){
			char theC = src.charAt(i);
			if(theC==c){
				strList.add(sb.toString());
				sb = new StringBuffer();
			}else{
				sb.append(theC);
			}
		}
		strList.add(sb.toString());
		String[] retStr = new String[strList.size()];
		for(int j=0;j<strList.size();j++){
			retStr[j] = strList.get(j);
		}
		return retStr;
	}
	
	public  static  void main(String[] args){
		String test="XC1-BZ-MCBC5BA002-LF01-MCBC6BA001-KZ01-MCBC1BA002-MJ01-MCCC5BD001-DJ01-MCDA2BB002-YW-XY-01-01---default.jpg";
		String test2="XC1-BZ-MCBC5BA002-LF01-MCBC6BA001-KZ01-MCBC1BA002-MJ01-MCCC5BD001-DJ01-MCDA2BB002-YW-XY-01-01---";
		String[] ret = StringUtil.split(test2,'-');
		System.out.println(ret.length);
		for(String s:ret){
		   System.out.println(s);
		}
	}
}
