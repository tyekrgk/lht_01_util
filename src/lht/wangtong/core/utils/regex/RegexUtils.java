package lht.wangtong.core.utils.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式校验
 * @author lyl
 *
 */
public class RegexUtils {
	
	public static boolean checkPhoneNum (String mobile){
		if (mobile == null){
			return false;
		}
		String regex = "^((\\+86))?(0)?(13[0-9]?|15[0-9]?|18[0-9])[0-9]{8}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		if (!m.find()){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 根据正则表达式判断数据，匹配返回真，反之返回false
	 * @see ^[1-9]d*$　 　 //匹配正整数
     * @see ^-[1-9]d*$ 　 //匹配负整数
	 * @see ^-?[0-9]{1,}$　　 //匹配整数
	 * @see ^[1-9]d*|0$　 //匹配非负整数（正整数 + 0）
	 * @see ^-[1-9]d*|0$　　 //匹配非正整数（负整数 + 0）
	 * @see ^[1-9]d*.d*|0.d*[1-9]d*$　　 //匹配正浮点数
	 * @see ^-([1-9]d*.d*|0.d*[1-9]d*)$　 //匹配负浮点数
	 * @see ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　 //匹配浮点数
	 * @see ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
	 * @see ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
	 * @see ^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
	 * @see ^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
	 * @see ^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
	 * @see ^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
	 * @see ^w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串
	 * @see 只能输入数字：^[0-9]*$
	 * @see 只能输入零和非零开头的数字：^(0|[1-9][0-9]*)$
	 * @see 只能输入有两位小数的正实数：^[0-9]+(.[0-9]{2})?$
	 * @see 只能输入有1-3位小数的正实数：^[0-9]+(.[0-9]{1,3})?$
	 * @see 只能输入非零的正整数：^+?[1-9][0-9]*$
	 * @see 只能输入非零的负整数：^-[1-9][0-9]*$
	 * @see 只能输入长度为3的字符：^.{3}$
	 * @see 只能输入由26个英文字母组成的字符串：^[A-Za-z]+$
	 * @see 只能输入由26个大写英文字母组成的字符串：^[A-Z]+$
	 * @see 只能输入由26个小写英文字母组成的字符串：^[a-z]+$
	 * @see 只能输入由数字和26个英文字母组成的字符串：^[A-Za-z0-9]+$
	 * @see 只能输入由数字、26个英文字母或者下划线组成的字符串：^w+$
	 * @see 验证用户密码:^[a-zA-Z]w{5,17}$正确格式为：以字母开头，长度在6-18之间，
	 * @see 匹配中文字符的正则表达式： [u4e00-u9fa5]
	 * @see 匹配双字节字符(包括汉字在内)：[^x00-xff]
	 * @see 匹配空行的正则表达式：n[s| ]*r
	 * @see 匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/
	 * @see 匹配首尾空格的正则表达式：(^s*)|(s*$)
	 * @see 匹配Email地址的正则表达式：w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*
	 * @see 匹配网址URL的正则表达式：http://([w-]+.)+[w-]+(/[w- ./?%&=]*)? 
	 * @param num
	 * @param regex
	 * @return
	 */
	public static boolean checkStrByRegex (String num,String regex) {
		if (num == null){
			return false;
		}
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(num);
		if (!m.find()){
			return false;
		} else {
			return true;
		}
	}
}
