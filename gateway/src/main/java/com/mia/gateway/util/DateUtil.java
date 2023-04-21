package com.mia.gateway.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期操作工具类
 * @author cuishikuan
 */
public class DateUtil {

	/**
	 * default date format pattern
	 */
	public final static String DATE_FORMAT_DEFAULT = "yyyyMMdd";
	public final static String DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public final static String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String TIME_FORMAT = "HH:mm";
	public final static String MONTH_DAY_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
	public final static String LOCATE_DATE_FORMAT = "yyyyMMddHHmmss";

	public static final int DAYS_OF_A_WEEK = 7;
	public static final int DAYS_OF_A_MONTH = 30;

	private static final Date ZERO_DAY = new Date(-62135798400000L);

	public static Date getMinDay() {
		return ZERO_DAY;
	}

	public static boolean isMinDay(Date date) {
		return DateUtils.isSameDay(ZERO_DAY, date);
	}

	private DateUtil() {
	}

	/**
	 * parse date with the default pattern
	 *
	 * @param date
	 *            string date
	 * @return the parsed date
	 */
	public static Date parseDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static Date parseDate(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将日期格式化为指定的字符串.<br>
	 * <br>
	 *
	 * @param d
	 *            日期.
	 * @param format
	 *            输出字符串格式.
	 * @return 日期字符串
	 */
	public static String getStringFromDate(Date d, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(d);
	}

	/**
	 * 获取增加小时后的 Date
	 *
	 * @param date
	 * @param i
	 * @return squall add 20100225
	 */
	public static Date addHour(Date date, int i) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, i);
		return calendar.getTime();
	}

	/**
	 * format date with the default pattern
	 *
	 * @param date
	 *            the date that want to format to string
	 * @return the formated date
	 */
	public static String formatDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(FULL_DATE_TIME_FORMAT);
		return format.format(date);
	}

	/**
	 * 格式化时间yyyyMMddHHmmss
	 * @param date
	 * the date that want to format to string
	 * @return the formated date
	 */
	public static String formatDateStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(LOCATE_DATE_FORMAT);
		return format.format(date);
	}

	public static String formatTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
		return format.format(date);
	}

	/**
	 * get current date
	 *
	 * @return the string of current date
	 */
	public static String getCurrentDateFormat() {
		return formatDate(new Date());
	}

	/**
	 * get current date
	 *
	 * @return the string of current date
	 */
	public static String getCurrentDateFormat(String pattern) {
		return formatDate(new Date(), pattern);
	}

	/**
	 * 获取当前日期
	 *
	 * @return 格式 YYYY-MM-DD
	 */
	public static String getTodayDate() {
		return getStringFromDate(new Date(), DATE_FORMAT_DEFAULT);
	}

	public static String getTodayDate2() {
		return getStringFromDate(new Date(), DATE_FORMAT);
	}

	/**
	 * get number of days between the two given date
	 *
	 * @param fromDate
	 *            the begin date
	 * @param endDate
	 *            the end date
	 * @return the number of days between the two date
	 */
	public static int getDateNum(Date fromDate, Date endDate) {
		long days = (endDate.getTime() - fromDate.getTime())
				/ (1000 * 60 * 60 * 24);
		return (int) days;
	}

	/**
	 * add day to the date
	 *
	 * @param date
	 *            the added date
	 * @param number
	 *            the number to add to the date
	 * @return the added date
	 */
	public static Date addDate(Date date, int number) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, number);
		return calendar.getTime();
	}

	public static Date addMonth(Date date, int number) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, number);
		return calendar.getTime();
	}

	/**
	 * get the default calendar
	 *
	 * @return the calendar instance
	 */
	public static Calendar getDefaultCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar;
	}

	/**
	 * format the date into string value
	 *
	 * @param calendar
	 *            the formated calendar
	 * @return the string date
	 */
	public static String getStringDate(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return year + "-" + getNiceString(month) + "-" + getNiceString(day);
	}

	/**
	 * according to the pattern yyyy-MM-dd
	 *
	 * @param value
	 *            the value
	 * @return the formated value
	 */
	public static String getNiceString(int value) {
		String str = "00" + value;
		return str.substring(str.length() - 2, str.length());
	}

	/**
	 * get calendar from date
	 *
	 * @param date
	 *            the passing date
	 * @return the calendar instance
	 */
	public static Calendar getCalendarFromDate(Date date) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * format date with the given pattern
	 *
	 * @param date
	 *            the date that want to format to string
	 * @param pattern
	 *            the formated pattern
	 * @return the formated date
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String getInterval(Date startDate, Date endDate) {
		long intervalTime = endDate.getTime() - startDate.getTime();
		return getInterval(intervalTime);
	}

	public static int getIntervalMinute(Date startDate, Date endDate) {
		long intervalTime = endDate.getTime() - startDate.getTime();
		return (int) (intervalTime / (1000 * 60));
	}

	public static String getInterval(long intervalTime) {
		int hour = (int) (intervalTime / (1000 * 60 * 60));
		int minute = (int) (intervalTime / (1000 * 60) - hour * 60);
		int second = (int) ((intervalTime / 1000) - hour * 60 * 60 - minute * 60);
		if (hour > 0) {
			return hour + "小时 " + minute + "分 " + second + "秒";
		} else if (minute > 0) {
			return minute + "分钟 " + second + "秒";
		} else {
			return second + "秒";
		}
	}

	public static int getYear(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDayOfMonth(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date now) {
		Calendar calendar = getCalendarFromDate(now);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	public static int getWeekOfYear(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		return calendar.get(Calendar.WEEK_OF_YEAR) - 1;
	}

	public static Date getCurrentDate() {
		Calendar calendar = getCalendarFromDate(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date getNextDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 一周的日期
	 *
	 * @param date
	 * @return
	 */
	public static List<Date> getWeekDayOfYear(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		int year = calendar.get(Calendar.YEAR);

		List<Date> result = new ArrayList<Date>();
		result.add(getDateOfYearWeek(year, week, Calendar.MONDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.TUESDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.WEDNESDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.THURSDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.FRIDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.SATURDAY));
		result.add(getDateOfYearWeek(year, week, Calendar.SUNDAY));
		return result;
	}

	/**
	 * 获取一年中某周,星期几的日期
	 *
	 * @param yearNum
	 * @param weekNum
	 * @param dayOfWeek
	 * @return
	 */
	private static Date getDateOfYearWeek(int yearNum, int weekNum,
                                          int dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		cal.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
		cal.set(Calendar.YEAR, yearNum);
		cal.set(Calendar.WEEK_OF_YEAR, weekNum);
		/*
		 * cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0);
		 * cal.set(Calendar.SECOND, 0);
		 */
		return cal.getTime();
	}

	/**
	 * 获取指定日期是一周的第几天,星期日是第一天
	 *
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public static Date parseDateE(String date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException("date pattern should be " + pattern);
		}
	}

	public static Date getDefaultDate() {
		Calendar calendar = getCalendarFromDate(new Date());
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date addSecond(Date date, int number) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, number);
		return calendar.getTime();
	}

	/**
	 * 清空日期的时间
	 *
	 * @param date
	 * @return
	 */
	public static Date clearTime(Date date) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static boolean betweenHour(Date date, int hour) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.add(Calendar.HOUR, hour);
		Calendar now = getDefaultCalendar();
		return calendar.after(now);
	}

	public static boolean betweenMinute(Date date, int minute) {
		Calendar calendar = getCalendarFromDate(date);
		calendar.add(Calendar.MINUTE, minute);
		Calendar now = getDefaultCalendar();
		return calendar.after(now);
	}

	/**
	 * 返回昨日年月日，没有时分秒
	 *
	 * @return
	 */
	public static Date getYesterday() {
		Date date = DateUtil.getCurrentDate();
		return addDate(date, -1);
	}

	public static String getYesterdayFormat() {
		Date date = addDate(new Date(), -1);
		return formatDate(date);
	}

	public static String getYesterdayFormat(String pattern) {
		Date date = addDate(new Date(), -1);
		return formatDate(date, pattern);
	}

	/**
	 * 日期间隔天数
	 *
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static int getInternalDay(String endDate, String beginDate) {
		long intervalTime = parseDate(endDate).getTime()
				- parseDate(beginDate).getTime();
		return (int) (intervalTime / (1000 * 24 * 60 * 60));
	}

	/**
	 * 返回两个日期间的总天数，不是直接相减
	 *
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static int getInternalTotalDays(String endDate, String beginDate) {
		return getInternalDay(endDate,beginDate)+1;
	}

	/**
	 * 日期间隔天数
	 *
	 * @param endDate
	 * @param beginDate
	 * @return
	 */
	public static List<String> getInternalDayStr(String endDate, String beginDate) {
		Date endDay = parseDate(endDate);
		Date beginDay = parseDate(beginDate);
		int intervalDay = getDateNum(beginDay, endDay);
		List<String> result = new ArrayList<>();
		for (int i = intervalDay; i >= 0; i--) {
			Date pastDay = addDate(endDay, -i);
			result.add(formatDate(pastDay, DATE_FORMAT));
		}
		return result;
	}

	/**
	 * 获得当年
	 *
	 * @return
	 */
	public static String getCurrentYear() {
		Calendar calendar = getDefaultCalendar();
		return "" + calendar.get(Calendar.YEAR);
	}

	public static List<String> getYearRange(int fromYear, int toYear) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i <= toYear - fromYear; i++) {
			result.add("" + (toYear - i));
		}
		return result;
	}

	/**
	 * 对字符串日期进行加天数运算
	 *
	 * @param date
	 * @param number
	 * @return
	 */
	public static String addDate(String date, int number) {
		Date addedDate = parseDate(date);
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(addedDate);
		calendar.add(Calendar.DATE, number);
		return formatDate(calendar.getTime());
	}

	/**
	 * 对字符串日期进行加天数运算
	 *
	 * @param date
	 * @param number
	 * @return
	 */
	public static String getAddDate(Date date, int number) {
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, number);
		return formatDate(calendar.getTime());
	}

	/**
	 * * 根据输入的日期字符串 和 提前天数 ， * 获得 指定日期提前几天的日期对象 *
	 *
	 * @param dateNow
	 *            当前时间
	 * @param beforeDays
	 *            倒推的天数 *
	 * @return 指定日期倒推指定天数后的日期对象 *
	 * @throws ParseException
	 */
	public static String getDate(Date dateNow, int beforeDays) {
		Calendar cal = Calendar.getInstance();
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date inputDate = dateFormat.parse(formatDate(dateNow));
			cal.setTime(inputDate);
			int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
			cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatDate(cal.getTime());
	}

	/**
	 * 比较日期是否小于当前日期
	 *
	 * @param startDate
	 * @return boolean
	 */
	public static boolean lessNowDate(Date startDate) {
		if (startDate == null) {
			return true;
		} else {
			return startDate.getTime() < (new Date()).getTime();
		}
	}

	public static boolean lessEndDate(Date startDate, Date endDate) {
		if (startDate == null) {
			return true;
		} else {
			return startDate.getTime() < endDate.getTime();
		}
	}


	public static int getWeek() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获得指定日期的当前的年份和月份
	 *
	 * @param date
	 * @return
	 */
	public static String getCurDateYearAndMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		return year + "-" + month + "-";
	}

	/**
	 * 获取当前时间的日期，具体为几号
	 *
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		return day;
	}

	/**
	 * @return
	 * @description 获得30分钟后的时间
	 */
	public static Date getAfter30minDate() {

		return new Date(System.currentTimeMillis() + 30 * 60 * 1000);
	}

	/**
	 * 获得x分钟前的时间
	 * @param minute x分钟
	 * @return
	 */
	public static Date getBeforeMinuteDate(int minute) {

        Calendar cl = Calendar.getInstance();
        cl.add(Calendar.MINUTE, -minute);

		return cl.getTime();
	}

	/**
	 * @return
	 * @author linyajun
	 * @description 返回日期为0000-00-00 实际返回0002-11-30 00:00:00
	 */

	public static final Date getZeroDate() {

		try {
			return DateUtils.parseDate("0000-00-00", "yyyy-MM-dd");
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return null;
	}

	public static boolean isBetween(Date startDate, Date endDate) {
		long current = System.currentTimeMillis();
		long start = startDate.getTime();
		long end = endDate.getTime();
		boolean result = false;
		if (current >= start && current <= end) {
			result = true;
		}
		return result;
	}

	/**
	 * @return
	 * @description 获得60分钟后的时间
	 */
	public static Date getAfter60minDate() {

		return new Date(System.currentTimeMillis() + 60 * 60 * 1000);
	}


	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal= Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	public static Date parse(String endDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return format.parse(endDate);
		} catch (ParseException e) {
			try{
				format = new SimpleDateFormat("yyyy/MM/dd");
				return format.parse(endDate);
			}catch(ParseException e1){
				return getCurrYearLast();//默认本年最后一年
			}
		}
	}

	public static int getMonthSpace(Date date1, Date date2){
		int result = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(date1);
		c2.setTime(date2);
		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);
	}

	public static Date getCurrentMonthLastDate(){
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.MINUTE, 59);
		ca.set(Calendar.SECOND,59);
		ca.set(Calendar.MILLISECOND, 0);
		return ca.getTime();
	}

	public static Date getMonthLastDate(Date date){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}
	
	public static Date getMonthFirstDate(Date date){
		Calendar calendar = Calendar.getInstance(java.util.Locale.CHINA);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}


	/**
	 * 获取上月的第一天
	 * @return
	 */
	public static Date getPreviousMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	/**
	 * 获取本月的第一天
	 * @return
	 */
	public static Date getCurrentMonthFirstDate(){
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.set(Calendar.HOUR_OF_DAY, 00);
		ca.set(Calendar.MINUTE, 00);
		ca.set(Calendar.SECOND, 00);
		return ca.getTime();
	}
	
	/**
	 * 获取下月的第一天
	 * @return
	 */
	public static Date getNextMonthFirstDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);
		return calendar.getTime();
	}

	public static boolean checkDate(Date startTime, Date endTime, Date time) {
		if (endTime == null) {
			return true;
		}
		if (time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取一年第一天
	 *
	 * @param year
	 * @return
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}



	/**
	 * 判断是否同一天
	 *
	 * @param date           日期
	 * @param dt             日期
	 * @return               布尔值
	 */
	public static boolean isSameDay(Date date, Date dt) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(date, dt);
	}

	/**
	 * 获取本年第一时间
	 *
	 * @return               日期
	 */
	public static Date getCurrentYearFirstTime() {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_YEAR, 1);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE, 0);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);

		return ca.getTime();
	}

	/**
	 * 获取参数时间N秒的时间
	 * @param date
	 * @return
	 */
	public static Date addNSecond(Date date, int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, number);
		return calendar.getTime();
	}

	public static Date formatDateToEndDateTime(String strDate) {
		return parseDate(FULL_DATE_TIME_FORMAT, strDate + " 23:59:59");
	}
	
	public static String formatDateToEndDateTimeStr(Date date) {
		String endDate = DateUtil.getStringFromDate(date,DateUtil.DATE_FORMAT);
		return (endDate + " 23:59:59");
	}
	
	/**
	 * 日期格式后加上  23:59:59
	 * @param endDate
	 * @return
	 */
	public static String formatDateStrToEndDateTimeStr(String endDate) {
		return (endDate + " 23:59:59");
	}

	public static int getTimestampForPhp() {
		long time = System.currentTimeMillis();
		return (int)(time/1000);
	}

	/**
	 * 对字符串日期进行加天数运算后返回
	 * @param date
	 * @param number
	 * @return
	 */
	public static String addDayToStrDate(String date, int number) {
		Date addedDate = parseDate(date);
		Calendar calendar = getDefaultCalendar();
		calendar.setTime(addedDate);
		calendar.add(Calendar.DATE, number);
		Date newDate = calendar.getTime();
		return getStringFromDate(newDate, DateUtil.DATE_FORMAT);
	}

	/**
	 * 验证时间字符串格式(yyyy-MM-dd HH:mm:ss)输入是否正确
	 */
	public static boolean valiDateTimeWithLongFormat(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return false;
		}
		timeStr = timeStr.trim();

		String format = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) "
				+ "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(timeStr);
		if (matcher.matches()) {
			pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
			matcher = pattern.matcher(timeStr);
			if (matcher.matches()) {
				int y = Integer.valueOf(matcher.group(1));
				int m = Integer.valueOf(matcher.group(2));
				int d = Integer.valueOf(matcher.group(3));
				if (d > 28) {
					Calendar c = Calendar.getInstance();
					c.set(y, m-1, 1);
					int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
					return (lastDay >= d);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 查询指定日期范围内所有日期
	 * @param dBegin
	 * @param dEnd
	 * @return
	 */
	public static List<String> findDates(Date dBegin, Date dEnd,String... dateFormat){
		if(dBegin==null || dEnd==null){
			return new ArrayList<>();
		}
        dBegin = parseDate(formatDate(dBegin,DATE_FORMAT));
        dEnd = parseDate(formatDate(dEnd,DATE_FORMAT));

		List<String> dateList = new ArrayList<String>();
		String format = DATE_FORMAT;
		if(dateFormat!=null && dateFormat.length > 0){
			format=dateFormat[0];
		}
		SimpleDateFormat sd = new SimpleDateFormat(format);
		dateList.add(sd.format(dBegin));
		Calendar calBegin = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calBegin.setTime(dBegin);
		Calendar calEnd = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calEnd.setTime(dEnd);
		// 测试此日期是否在指定日期之后
		while (dEnd.after(calBegin.getTime()))
		{
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			dateList.add(sd.format(calBegin.getTime()));
		}
		return dateList;
	}

	public static void main(String[] args) {
//		System.out.println(addDayToStrDate("2019-01-20",-720));
//		System.out.println(parse("2016-02-04"));
//		System.out.println(parse("2017-02-04"));
//
//		int s = getMonth(new Date());
//
//		int e = getMonth(parse("2017-12-21"));
//
//		System.out.println(s + "    ========      " +e);
//		System.out.println(getMonthSpace(parse("2016-02-04"),parse("2017-02-04")));

//		Calendar ctime = Calendar.getInstance();
//		ctime.setTime(DateUtil.getYesterday());
//		long timestamp = ctime.getTimeInMillis();
//		final String search_time = String.valueOf(timestamp).substring(0, 10);
//		System.out.println(search_time);

//		Calendar ca = Calendar.getInstance();
//        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
//        Date last = ca.getTime();
//        System.out.println("===============last:"+last);

		//获取当前月最后一天
//		Calendar para = Calendar.getInstance(java.util.Locale.CHINA);
//        para.setTime(getCurrentDate());
//        para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));
//        para.set(Calendar.HOUR_OF_DAY, 23);
//        para.set(Calendar.MINUTE, 59);
//        para.set(Calendar.SECOND, 59);
//		System.out.println(para.getTime());
		System.out.println(getPreviousMonthFirstDate());
		System.out.println(getCurrentMonthFirstDate());
		System.out.println(formatDate(getBeforeMinuteDate(29 * 24 * 60)));

		Date date1 = parseDate("2019-07-09 12:26:58",DATE_FORMAT);
		Date date2 = parseDate("2019-08-10 12:26:57",DATE_FORMAT);
		List<String> dates = findDates(date1, date2);
		System.out.println(dates);

		String beginDate = DateUtil.addDayToStrDate("2019-09-30", 1-DateUtil.DAYS_OF_A_MONTH);
		System.out.println(beginDate);

		System.out.println(getInternalDay("2020-09-05","2019-09-05"));

//		Date startDate = parseDate("2016-4-9");

//		Date endDate = parseDate("2016-4-8");
//		System.out.println(isBetween(startDate,endDate));
	}
}
