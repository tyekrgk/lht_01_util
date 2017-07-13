package lht.wangtong.core.utils.str;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lht.wangtong.core.utils.vo.LoginUser;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Rong.Xing
 * @date 2014-04-08
 */
public class CommonUtils {

	/**
	 * request转换 Map
	 * 
	 * @param request
	 * @return HashMap
	 * @author Rong.Xing
	 * @date 2014-05-15
	 */
	public static Map<String, Object> requestMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			try {
				request.setCharacterEncoding("utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String request_method = request.getParameter("request_method");

		try {
			if ("ajax".equalsIgnoreCase(request_method)) {
				request.setCharacterEncoding("UTF-8");
			} else {
				request.setCharacterEncoding("ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			if (!"ajax".equalsIgnoreCase(request_method)) {
				try {
					for (int i = 0; values != null && i < values.length; i++) {
						values[i] = new String(
								values[i].getBytes("iso-8859-1"), "utf-8");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (values != null && values.length == 1) {
				map.put(name, values[0]);
			} else {
				map.put(name, values);
			}
		}
		map.remove("request_method");
		return map;
	}

	/**
	 * 获取系统当前的时间的字符串
	 * 
	 * @param format
	 *            返回時間格式 yyyy-MM-dd , yyyy-MM-dd HH:mm:ss
	 * @author rx
	 * @return
	 */
	public static String getNowDatetoStr(String format) {
		Date nowTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(nowTime);
	}

	/**
	 * 当前月份有多少天；
	 * 
	 * @author rx
	 * @return
	 */
	public static int getDaysOfCurMonth() {
		// 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
		// 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
		int curyear = new Integer(getNowDatetoStr("yyyy")).intValue(); // 当前年份

		int curMonth = new Integer(getNowDatetoStr("MM")).intValue();// 当前月份

		int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30,

		31 };
		// 判断闰年的情况 ，2月份有29天；

		if ((curyear % 400 == 0)

		|| ((curyear % 100 != 0) && (curyear % 4 == 0))) {

			mArray[1] = 29;

		}
		return mArray[curMonth - 1];
	}

	/**
	 * 时间差
	 * 
	 * @author rx
	 * @param startdate开始时间
	 * @param startdate结束时间
	 * @param str返回类型
	 *            ，day为返回天数，hour为小时，second为秒
	 */
	public static long difference(String startdate, String enddate, String str) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date one;
		Date two;
		long day = 0;
		long hour = 0;
		try {
			one = df.parse(startdate);
			two = df.parse(enddate);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			if (str.equals("day")) {
				return diff / (24 * 60 * 60 * 1000);
			} else if (str.equals("hour")) {
				return (diff / (60 * 60 * 1000) - day * 24);
			} else if (str.equals("min")) {
				return ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			} else if (str.equals("sec")) {
				return (long) Math
						.ceil(Math.abs((one.getTime() - two.getTime()) / (1000)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 生成全部红包
	 * 
	 * @param moeny
	 *            分配金额
	 * @param allnum
	 *            分配人数
	 * @return
	 */
	public static double[] fenPei(double moeny, int allnum) {
		double balance = moeny;
		double all = moeny;
		double cunfang[] = new double[allnum];
		for (int i = allnum; i > 1; i--) {
			double d = shengChengHongBao(all, balance, allnum);
			cunfang[i - 1] = d;
			balance -= d;
		}
		cunfang[0] = Double.valueOf(new DecimalFormat("#.##").format(balance));
		double cc = 0;
		for (int i = cunfang.length; i > 0; i--) {
			cc += cunfang[i - 1];
		}
		if (cc == moeny) return cunfang;
		return fenPei(moeny, allnum);
	}

	/**
	 * 生成单个红包
	 * 
	 * @param allmoeny
	 *            总金额
	 * @param balance
	 *            余额
	 * @param allnum
	 *            总人数
	 * @return
	 */
	private static double shengChengHongBao(double allmoeny, double balance,
			int allnum) {
		double a = allmoeny / allnum;
		double d = Double.valueOf(new DecimalFormat("#.##").format(Math
				.random() * balance));
		// 避免金额差距过大。获得宝箱最高不能高于平均数的1.3倍,最低不能低于0.5
		if (d <= a * 1.3 && d >= a * 0.5)
			return d;
		return shengChengHongBao(allmoeny, balance, allnum);
	}
	/**
	  * 获取当前登陆用户
	  * @return SystemUser
	  * @author Rong.Xing
	  * @date 2015-04-15
	  */
	public static LoginUser user(){
		return (LoginUser)session().getAttribute("_____user");
	}
	/**
	  * 获取当前请求参数
	  * @return Map
	  * @author Rong.Xing
	  * @date 2014-05-15
	  */
	public static Map<String,Object> param(){
		return requestMap(req());
	}
	/**
	  * 获取request
	  * @return req
	  * @author Rong.Xing
	  * @date 2014-05-15
	  */
	public static HttpServletRequest req(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	/**
	  * 获取Session
	  * @return Session
	  * @author Rong.Xing
	  * @date 2014-05-15
	  */
	public static HttpSession session(){
		return req().getSession();
	}
	/**
	 * 获取当前Session中的参数
	 * @param c
	 * @param name
	 * @author Rong.Xing
	 * @date 2014-05-15
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>  T getSeeionAttribute(Class<T> c,String name){
		return (T) session().getAttribute(name);
	}
	
}
