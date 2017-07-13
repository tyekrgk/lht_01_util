package lht.wangtong.core.utils.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转拼音工具
 * @author zsc
 *
 */
public class Pinyin4jUtil {
	
	/**
	 * 将汉字字符串转换为拼音全拼
	 * @param src
	 * @return
	 */
	public static String str2Pinyin(String src) {
		if(src != null && !"".equals(src.trim())) {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);	//小写
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);	//无声调
			format.setVCharType(HanyuPinyinVCharType.WITH_V);	//女->nv
			String des = "";
			for(int i=0; i<src.length(); i++) {
				try {
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(src.charAt(i), format);
					if(strs != null) {
						des += strs[0];
					} else {
						des += src.charAt(i);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					continue;
				}
			}
			return des;
		} else {
			return "";
		}
	}
	
	/**
	 * 取汉字字符串的首字母
	 * @param str
	 * @return
	 */
	public static String str2PinyinInitial(String src) {
		if(src != null && !"".equals(src.trim())) {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.UPPERCASE);	//大写
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);	//无声调
			format.setVCharType(HanyuPinyinVCharType.WITH_V);	//女->nv
			String des = "";
			for(int i=0; i<src.length(); i++) {
				try {
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(src.charAt(i), format);
					if(strs != null) {
						des += strs[0].charAt(0);
					} else {
						des += src.charAt(i);
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					continue;
				}
			}
			return des;
		} else {
			return "";
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(Pinyin4jUtil.str2Pinyin("张%!@#$()- ^"));
		System.out.println(Pinyin4jUtil.str2PinyinInitial("阿莫西宁胶囊"));
	}

}
