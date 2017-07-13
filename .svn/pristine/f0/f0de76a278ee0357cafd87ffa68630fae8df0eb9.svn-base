package lht.wangtong.core.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 放置常用的与日期时间处理有关的功能
 * @author aibo zeng
 *
 */

public class DateTimeTool {
	public static Logger logger = Logger.getLogger(DateTimeTool.class.getClass());
	public static boolean isWindow(){
    	if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")>=0){
    		return true;
    	}
    	else{
    		return false;
    	}
	}

	/**
	 * 取出日期中，今天凌晨的时间
	 * @param date
	 * @return
	 */
	public static Date getBeginTime(Date date){
		if(date==null){
			return getMinDate();
		}
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS").parse(dateStr+" 00:00:00 000");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return date;
		}
	}
	
	/**
	 * 取出日期中，今天结束的时间
	 * @param date
	 * @return
	 */
	public static Date getEndTime(Date date){
		if(date==null){
			return getMaxDate();
		}
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss SSS").parse(dateStr+" 23:59:59 999");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return date;
		}
	}

	/**
	 * 取出今天日期中，今天凌晨的时间
	 * @param date
	 * @return
	 */
	public static Date getTodayBeginTime(){
		return getBeginTime(new Date());
	}
	
	/**
	 * 取出今天日期中，今天结束的时间
	 * @param date
	 * @return
	 */
	public static Date getTodayEndTime(){
		return getEndTime(new Date());
	}
	
	/**
	 * 得到最小的日期。1900-01-01 00:00:00  ，java里面的日期是从这一天开始计算
	 * @return
	 */
	public static Date getMinDate(){
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("19000101 00:00:00");
		} catch (ParseException e) {
			logger.error(e);
			return new Date();
		}
	}
	
	/**
	 * 得到最大的日期。2900-01-01 00:00:00 
	 * @return
	 */
	public static Date getMaxDate(){
		try {
			return new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse("29000101 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			return new Date();
		}
	}
	
	public static void main(String[] str){
		logger.debug(DateTimeTool.getBeginTime(new Date()));
	}
}
