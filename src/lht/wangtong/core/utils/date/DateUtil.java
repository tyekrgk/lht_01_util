package lht.wangtong.core.utils.date;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;



/**
 * DateUtil 负责字符串/Long跟日期之间的转换。 <br>
 * 
 * @author <a href="mailto:senton1101@gmail.com">TianXiangdong</a> at 2010-9-15 上午11:59:23
 * @version 1.0
 */
public class DateUtil {
	public static Logger logger = Logger.getLogger(DateUtil.class.getClass());
	/**
	 * 将一个字符串的日期按照指定的pattern转换成Date对象。
	 * 
	 * @param dateStr
	 *            字符串的日期
	 * @param pattern
	 * @return Date
	 */
	public static Date string2Date(String dateStr, String pattern) {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		if (pattern == null || pattern.equals("")) {
			pattern = FormatConstants.DATE_FORMAT_PATTERN;
		}
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		}
		catch (ParseException e) {
			logger.error(e);
			return null;
		}
	}

	/**
	 * 将一个Date按照指定的pattern转换成String对象。
	 * 
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String date2String(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null || pattern.equals("")) {
			pattern = FormatConstants.DATE_FORMAT_PATTERN;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	// 20100601010101 => 2010-06-01 01:01:01
	public static String longToReadableDateTime(long longTime) {
		// 20100513200124 -> 2010-10-05 20:01:24
		String slongTime = longTime + "";
		StringBuffer sb = new StringBuffer();
		sb.append(slongTime.substring(0, 4));
		sb.append("-");
		sb.append(slongTime.substring(4, 6));
		sb.append("-");
		sb.append(slongTime.substring(6, 8));
		sb.append(" ");
		sb.append(slongTime.substring(8, 10));
		sb.append(":");
		sb.append(slongTime.substring(10, 12));
		sb.append(":");
		sb.append(slongTime.substring(12, 14));
		return sb.toString();
	}

	// 20100601 => 2010-06-01
	public static String longToReadableDate(long longDate) {
		// 20100513200124 -> 2010-10-05 20:01:24
		String slongTime = longDate + "";
		StringBuffer sb = new StringBuffer();
		sb.append(slongTime.substring(0, 4));
		sb.append("-");
		sb.append(slongTime.substring(4, 6));
		sb.append("-");
		sb.append(slongTime.substring(6, 8));
		return sb.toString();
	}

	// new Date() => 20100601
	public static long dateToLongDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String year = c.get(Calendar.YEAR) + "";
		String month = c.get(Calendar.MONTH) + 1 + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String day = c.get(Calendar.DAY_OF_MONTH) + "";
		if (day.length() < 2) {
			day = "0" + day;
		}
		String slong = year + month + day;
		return Long.parseLong(slong);
	}

	// new Date() => 20100601010101
	public static long dateToLongDateTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String year = c.get(Calendar.YEAR) + "";
		String month = c.get(Calendar.MONTH) + 1 + "";
		if (month.length() < 2) {
			month = "0" + month;
		}
		String day = c.get(Calendar.DAY_OF_MONTH) + "";
		if (day.length() < 2) {
			day = "0" + day;
		}

		String hour = c.get(Calendar.HOUR_OF_DAY) + "";
		if (hour.length() < 2) {
			hour = "0" + hour;
		}

		String minute = c.get(Calendar.MINUTE) + "";
		if (minute.length() < 2) {
			minute = "0" + minute;
		}

		String second = c.get(Calendar.SECOND) + "";
		if (second.length() < 2) {
			second = "0" + second;
		}
		String slong = year + month + day + hour + minute + second;
		return Long.parseLong(slong);
	}

	public static Date longDateToDate(long longDate) {
		String slongTime = longDate + "";
		int year = Integer.parseInt(slongTime.substring(0, 4));
		int month = Integer.parseInt(slongTime.substring(4, 6)) - 1;
		int date = Integer.parseInt(slongTime.substring(6, 8));
		Calendar c = Calendar.getInstance();
		c.set(year, month, date);
		return c.getTime();
	}

	public static Date longDateTimeToDate(long longDate) {
		String slongTime = longDate + "";
		int year = Integer.parseInt(slongTime.substring(0, 4));
		int month = Integer.parseInt(slongTime.substring(4, 6)) - 1;
		int date = Integer.parseInt(slongTime.substring(6, 8));
		int hour = Integer.parseInt(slongTime.substring(8, 10));
		int minute = Integer.parseInt(slongTime.substring(10, 12));
		int second = Integer.parseInt(slongTime.substring(12, 14));
		Calendar c = Calendar.getInstance();
		c.set(year, month, date, hour, minute, second);
		return c.getTime();
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
//		logger.debug(DateUtil.dateToLongDate(new Date()));
//		logger.debug(DateUtil.dateToLongDateTime(new Date()));
//		logger.debug(DateUtil.longToReadableDateTime(20100601010101L));
//		logger.debug(DateUtil.longToReadableDate(20100601L));
//		logger.debug(DateUtil.longDateTimeToDate(20100601010101L).toLocaleString());
//		logger.debug(DateUtil.longDateToDate(20100601L).toLocaleString());
		logger.debug(DateUtil.getCurrentDate());
	}
	/**
	 * 获取N周前的周一
	 * @param week 几周前
	 * @return
	 */
	public static String firstDayOfWeek(int week){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
    	int iWeekNum = calendar.get(Calendar.WEEK_OF_YEAR);
        // 取得本周一
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.WEEK_OF_YEAR, iWeekNum-week);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
       return 	sf.format(calendar.getTime() );

	}
	public static String getCurrentDate() {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
		return sf.format(new Date());
	}
}
