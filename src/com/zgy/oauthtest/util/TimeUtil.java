package com.zgy.oauthtest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	private static String TIME_DATE_TIME_STRING_FORMAT = "yyyy-MM-dd HH:mm:ss";// String 时间格式
	private static String TIME_DATE_STRING_FORMAT = "yyyy-MM-dd";// String 时间格式

	/**
	 * ��õ�ǰʱ��
	 * 
	 * @Description:
	 * @return long ��
	 * @see:
	 * @since:
	 * @author: zhuanggy
	 * @date:2013-3-8
	 */
	public static long getCurrentTimeMillis() {
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * stringʱ������תΪlong
	 * 
	 * @Description:
	 * @param dateTime
	 *            (�磺�� 2013-03-08 13:51:00)
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
	 * long������ʱ��תΪString
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
	 * string������תΪlong
	 * 
	 * @Description:
	 * @param date
	 *            (�磺�� 2013-03-08)
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
	 * long������תΪString
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
