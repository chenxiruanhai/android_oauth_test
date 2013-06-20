package com.zgy.oauthtest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static String TIME_DATE_TIME_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";// String 鏃堕棿鏍煎紡
	private static String TIME_DATE_STRING_FORMAT = "yyyy-MM-dd";// String 鏃堕棿鏍煎紡

	/**
	 * 获得当前时间
	 * 
	 * @Description:
	 * @return long 秒
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * string时间日期转为long
	 * 
	 * @Description:
	 * @param dateTime
	 *            (如： 2013-03-08 13:51:00)
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long dateTimeStringToLong(String dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(dateTime);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * long型日期时间转为String
	 * 
	 * @Description:
	 * @param dateTimeMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static String longToDateTimeString(long dateTimeMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_TIME_STRING_FORMAT);
		Date dt = new Date(dateTimeMillis);
		return sdf.format(dt);
	}

	/**
	 * string型日期转为long
	 * 
	 * @Description:
	 * @param date
	 *            (如： 2013-03-08)
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long dateStringToLong(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt2;
		try {
			dt2 = sdf.parse(date);
			return dt2.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * long型日期转为String
	 * 
	 * @Description:
	 * @param dateMillis
	 * @return
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static String longToDateString(long dateMillis) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_DATE_STRING_FORMAT);
		Date dt = new Date(dateMillis);
		return sdf.format(dt);
	}
}
