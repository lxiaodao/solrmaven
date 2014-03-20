/*
 * Copyright Huawei Symantec Technologies Co.,Ltd. 2008-2009. All rights reserved.
 *
 *
 */
package com.huaweisymantec.search.solr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Document DateUtil
 * <p />
 *
 * @author l90003709
 * Feb 2, 20102:05:52 PM
 */
public final class DateUtil {
	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	/**
	 * 默认日期格式
	 */
	private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final String TIME_ZONE="yyyy-MM-dd'T'HH:mm:ssZ";
	private static final long DAY_IN_MINUTE = 24L * 60L * 60L * 1000L;
	private static final int TEN = 10;

	private DateUtil() {
	}

	/**
	 * 根据日期对象获取一个新的日期对象，类似clone
	 * @param oldDate 日期对象
	 * @return 新日期对象
	 */
	public static Date getNewDate(Date oldDate) {
		if (null == oldDate) {
			return null;
		} else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(oldDate);
			return cal.getTime();
		}
	}

	/**
	 * 将字符串数据格式化为日期类型
	 * @param str 日期字符串
	 * @return 日期类型
	 */
	public static Date parseDate(String str) {
		if (str == null) {
			return null;
		}
		try {
			if(str.length()<=TEN){
				return DATE_FORMAT.parse(str);
			}
			return DEFAULT_FORMAT.parse(str);
		} catch (ParseException e) {
			LOG.error("Can not parse date str!", e);
			return null;
		}
	}

	/**
	 * 将字符串数据格式化为日期类型
	 * @param str 日期字符串
	 * @return 日期类型
	 */
	public static Date parseDate(String str, String pattern) {
		if (str == null || pattern == null) {
			return null;
		}
		try {
			DateFormat format = new SimpleDateFormat(pattern);
			return format.parse(str);
		} catch (ParseException e) {
			LOG.error("Can not parse date str!", e);
			return null;
		}
	}

	/**
	 * 将日期数据按照默认格式格式化为字符串
	 * @param date 要格式化的日期对象
	 * @return 格式化后的日期字符串
	 */
	public static String format(Date date) {
		if (date == null) {
			return null;
		}
		return DEFAULT_FORMAT.format(date);
	}

	/**
	 * 将日期数据按照指定格式格式化为字符串
	 * @param date 要格式化的日期对象
	 * @param pattern 日期格式
	 * @return 格式化后的日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null || pattern == null) {
			return null;
		}
		DateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 计算两个日期之间的时间差（单位：天）
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 差值
	 */
	public static int getMinusInDay(Date start, Date end) {	
		return (int) ((end.getTime() - start.getTime()) / DAY_IN_MINUTE);
	}
}
