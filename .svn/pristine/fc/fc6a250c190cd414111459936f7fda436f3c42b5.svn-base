package lht.wangtong.core.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 日期处理
 * @author lyl
 *
 */
public class CalenderUtil {
	public static Logger logger = Logger.getLogger(CalenderUtil.class.getClass());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*Date date1 = CalenderUtil.getDate("2008-12-15");
		logger.debug(date1);
		Date date2 = CalenderUtil.nextDate(date1);

		logger.debug(date2);
		String ymd = CalenderUtil.getDateYmd(date2);
		logger.debug(ymd);
		logger.debug(date1);
		int i = CalenderUtil.getMyDateOfWeek(date2);

		logger.debug(i);
		CalenderUtil.test();
		Object [] obj = CalenderUtil.preWeek(date1);
		logger.debug(CalenderUtil.getDateYmd((Date) obj[0]));
		logger.debug(CalenderUtil.getDateYmd((Date) obj[1]));
		obj = CalenderUtil.preMonth(date1);
		logger.debug(CalenderUtil.getDateYmd((Date) obj[0]));
		logger.debug(CalenderUtil.getDateYmd((Date) obj[1]));*/
		logger.debug(preWeek(new Date()));
		logger.debug(CalenderUtil.getDateYmd((Date) preWeek(new Date())[0]));
		logger.debug(CalenderUtil.getDateYmd((Date) preWeek(new Date())[1]));

	}

	public static void test() {
		String ymd1 = "2008-10-06";
		String ymd2 = "2009-06-19";
		Date d1 = CalenderUtil.getDate(ymd1);
		Date d2 = CalenderUtil.getDate(ymd2);

		while (d1.before(d2)) {
			int week = CalenderUtil.getMyDateOfWeek(d1);
			logger.debug(CalenderUtil.getDateYmd(d1) + ">>" + week);
			d1 = CalenderUtil.nextDate(d1);

		}
	}

	/**
	 * 把日期以yyyy-MM-dd的格式换成字符串
	 * @param date
	 * @return
	 */
	public static String getDateYmd(Date date) {
		return getDateYmd(date, "yyyy-MM-dd");
	}

	/**
	 * 把日期以 pattern 的格式换成字符串
	 * @param date
	 * @param pattern 日期字符串的格式
	 * @return
	 */
	public static String getDateYmd(Date date, String pattern) {
		String dateymd = "";
		if (date == null)
			return null;
		if (pattern == null)
			pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			dateymd = df.format(date);
		} catch (Exception e) {
			logger.error(e);
		}
		return dateymd;

	}

	/**
	 * 把 @pattern 格式的字符串换成日期
	 * @param ymd
	 * @param pattern 
	 * @return
	 */
	public static Date getDate(String ymd, String pattern) {
		Date date = null;
		if (ymd == null)
			return null;
		if (pattern == null)
			pattern = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			date = df.parse(ymd);
		} catch (ParseException e) {
			logger.error(e);
		}
		return date;
	}
	
	/**
	 * 把 "yyyy-MM-dd" 格式的字符串换成日期
	 * @param ymd
	 * @return
	 */
	public static Date getDate(String ymd) {
		return getDate(ymd, "yyyy-MM-dd");
	}

	/**
	 * 得到输入日期的下一天，当前日期为字符串， 格式为"yyyy-MM-dd"
	 * @param ymd
	 * @return
	 */
	public static Date nextDate(String ymd) {
		return nextDateByDay(getDate(ymd),1);
	}

	/**
	 * 得到输入日期的下一天，
	 * @param date
	 * @return
	 */
	public static Date nextDate(Date date) {
		return nextDateByDay(date,1);
	}

	/**
	 * 得到输入日期是一年中的星期数
	 * @param date 
	 * @return 如果是-1，则表示出错了
	 */
	public static int getMyDateOfWeek(Date date) {
		return getMyDate(date, Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 根据dayType得到输入日期的数量，dayType参考java.util.Calendar
	 * @param date
	 * @param dayType
	 * @see java.util.Calendar
	 * @return
	 */
	public static int getMyDate(Date date,int dayType) {
		int dow = -1;
		try {
			Calendar cdr = Calendar.getInstance();
			dow = 0;
			cdr.setTime(date);
			dow = cdr.get(dayType);
		} catch (Exception e) {
			logger.error(e);
		}
		return dow;
	}

	/**
	 * 把日期以int的方式返回，比如“2012-01-01” 20120101 错误返回-1
	 * @param date
	 * @return
	 */
	public static int getDateInteger(Date date) {
		if (date == null){
			return -1;
		}
		String ymd = CalenderUtil.getDateYmd(date,"yyyyMMdd");
		if (ymd == null){
			return -1;
		}
		int result = Integer.parseInt(ymd);
		return result;
	}

	/**
	 * 得到输入日期的前一天，
	 * @param date
	 * @return
	 */
	public static Date preDate(Date date) {
		return nextDateByDay(date,-1);
	}
	
	/**
	 * 输入日期，得到当前输入日期前一个星期的两个区间，例如 2013-02-04 得到 2013-01-28,2013-02-03
	 * @param date
	 * @return
	 */
	public static Date [] preWeek(Date date) {
		Date [] obj = new Date[2];
		Calendar cdr = Calendar.getInstance();
		try {
			if (date != null) {
				cdr.setTime(date);
				int i = getMyDateOfWeek(date);
				if(i == 1){
					cdr.add(Calendar.DATE,-7);
					obj[1] = cdr.getTime();
					cdr.setTime((Date)obj[1]);
					cdr.add(Calendar.DATE,-6);
					obj[0] = cdr.getTime();
				} else{
					logger.debug(i);
					cdr.add(Calendar.DATE, -i+1);
					obj[1] = cdr.getTime();
					cdr.setTime((Date)obj[1]);
					cdr.add(Calendar.DATE,-6);
					obj[0] = cdr.getTime();
				}
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return obj;
	}
	
	/**
	 * 输入日期，得到当前输入日期前一个月的两个区间，例如 2013-02-04 得到 2013-01-01,2013-01-31
	 * @param date
	 * @return
	 */
	public static Date[] preMonth(Date date) {
		Date [] obj = new Date[2];
		Calendar cdr = Calendar.getInstance();
		try {
			if (date != null) {
				cdr.setTime(date);
				int i = cdr.get(5);
				cdr.add(Calendar.DATE, -i);
				obj[1] = cdr.getTime();
				cdr.setTime((Date)obj[1]);
				i = cdr.get(5);
				cdr.add(Calendar.DATE,-i+1);
				obj[0] = cdr.getTime();
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return obj;
	}
	
	/**
	 * 得到输入其他的下一年的这个日期
	 * @param date
	 * @return
	 */
	public static Date nextYear(Date date){
		try {
			Calendar cdr = Calendar.getInstance();
			if(date != null){
				cdr.setTime(date);
			}
			cdr.add(Calendar.YEAR, 1);
			return cdr.getTime();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 取出某个日期的多少个月之后的日期
	 * @param date
	 * @param months
	 * @return
	 */
	public static Date getAfterMonths(Date date,int months){
		try {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.add(Calendar.MONTH,months);
			return cal.getTime();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}		
	}

	/**
	 * 得到当前时间戳addMinute分钟后的时间
	 * @param addMinute
	 * @return
	 */
	public static Date nextMinute(int addMinute){
		return nextMinute(new Date(),addMinute);
	}
	
	/**
	 * 得到当前输入日期 addMinute分钟后的时间
	 * @param addMinute
	 * @return
	 */
	public static Date nextMinute(Date date,int addMinute){
		try {
			Calendar cdr = Calendar.getInstance();
			if(date != null){
				cdr.setTime(date);
			}
			cdr.add(Calendar.MINUTE,addMinute);
			return cdr.getTime();
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	/**
	 * 得到输入日期，day天后的日期，如果day为负数，则为前多少天。
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date nextDateByDay(Date date,int day){
		Calendar cdr = Calendar.getInstance();
		try {
			if(date != null){
				cdr.setTime(date);
			}
			cdr.add(Calendar.DATE, day);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return cdr.getTime();
	}
	
	/**
	 * 获取 当前月的最后一天（23点59分59秒）
	 * @return
	 */
	public static Date getMonthLastDay() {     
	    Calendar calendar = Calendar.getInstance();     
	    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	    Date tt = calendar.getTime();
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    String dateStr=sdf.format(tt);
	    logger.debug("%%%" + tt.toString());
	    SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d=null;
		try {
			d = sdf2.parse(dateStr+" 23:59:59");
		} catch (Exception e) {
			logger.error(e);
		}

	    return d;
	}  
	
	/**
	 * 获取 某年某月的最后一天（23点59分59秒）
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year, int month) {  
        Calendar cal = Calendar.getInstance();  
        cal.set(Calendar.YEAR, year);  
        cal.set(Calendar.MONTH, month);  
        // 某年某月的最后一天  
        int rr = cal.getActualMaximum(Calendar.DATE);
        String rt = year+"-"+month+"-"+rr +" 23:59:59";
        logger.debug("####  " + rt);
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date tr =null;
        try {
			tr = sdf2.parse(rt);
		} catch (Exception e) {
			logger.error(e);
		}
		return tr;
   }
	
}
