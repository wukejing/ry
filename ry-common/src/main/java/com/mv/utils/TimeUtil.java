package com.mv.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日期时间格式处理类
 * 
 * @author Administrator
 *
 */
public class TimeUtil {

	/**
	 * 字符串转日期
	 * 
	 * @param str
	 *            日期时间字符串，如"20130905 12:12:12"
	 * @param format
	 *            匹配格式，如"yyyyMMdd HH:mm:ss"
	 * @param defaultValue
	 *            转换发生异常时，默认的返回时间，如new Date()
	 * @return 日期时间
	 */
	public static Date parse(String str, String format, Date defaultValue) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 日期时间转字符串
	 * 
	 * @param date
	 *            日期时间
	 * @param format
	 *            匹配格式
	 * @return 日期时间字符串
	 */
	public static String format(Date date, String format) {
		String str = DateFormatUtils.format(date, format);
		return str;
	}

	public static Date getCurrentDate() {
		Date date = new Date();
		String dateStr = format(date, "yyyy-MM-dd");
		return parse(dateStr, "yyyy-MM-dd", date);
	}
}
