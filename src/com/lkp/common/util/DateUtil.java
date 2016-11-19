/*
/*
 * Copyright (c) SHADANSOU 2014 All Rights Reserved
 *
 */

package com.lkp.common.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import org.apache.log4j.Logger;

/**
 * <p>class function description.<p>
 *
 * create  2014-5-13<br>
 * @author  $Author$<br>
 * @version $Revision$ $Date$
 * @since   1.0
 */

public class DateUtil { 
	public DateUtil() {// null
	}
	// ���ڸ�ʽ����ݣ����磺2004��2008
	public static final String DATE_FORMAT_YYYY = "yyyy";
	// ���ڸ�ʽ����ݺ��·ݣ����磺200707��200808
	public static final String DATE_FORMAT_YYYYMM = "yyyy-MM";
	// ���ڸ�ʽ�������գ����磺20050630��20080808
	public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	// ���ڸ�ʽ�������գ��ú�ֿܷ������磺2006-12-25��2008-08-08
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	// ���ڸ�ʽ��������ʱ���룬���磺20001230120000��20080808200808
	public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";
	// ʱ���ʽ��ʱ���룬���磺12:00:00��20:08:08
	public static final String DATE_TIME_FORMAT_HHMISS = "HH:mm:ss";
	// ���ڸ�ʽ��������ʱ���룬�������ú�ֿܷ���ʱ������ð�ŷֿ���
	// ���磺2005-05-10 23��20��00��2008-08-08 20:08:08
	public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";
	/**
	 * �ַ���ת��Ϊ����
	 * 
	 * @param String
	 *            strDate�����ڵ��ַ�����ʽ
	 * @param String
	 *            format��ת����ʽ
	 * @return String
	 * @throws
	 */
	public static Date strToDate(String strDate, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			// //System.out.println(e.getMessage());
		}
		return date;
	}
	/**
	 * �ַ���ת��Ϊ����ʱ��
	 * 
	 * @param String
	 *            strDateTime������ʱ����ַ�����ʽ
	 * @param String
	 *            format��ת����ʽ
	 * @return String
	 * @throws
	 */
	public static Date strToDateTime(String strDateTime, String fromat) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(fromat);
		Date dateTime = null;
		try {
			dateTime = dateTimeFormat.parse(strDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
			// //System.out.println(e.getMessage());
		}
		return dateTime;
	}
	/**
	 * ����ת��Ϊ�ַ���
	 * 
	 * @param Date
	 *            date����Ҫת��������
	 * @param String
	 *            format��ת����ʽ
	 * @return String
	 * @throws
	 */
	public static String dateToStr(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	/**
	 * ����ʱ��ת��Ϊ�ַ���
	 * 
	 * @param Date
	 *            date����Ҫת��������
	 * @param String
	 *            format��ת����ʽ
	 * @return String
	 * @throws
	 */
	public static String dateTimeToStr(Date date, String format) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(format);
		return dateTimeFormat.format(date);
	}
	/**
	 * �õ���������ʱ��,today���ַ�������"yyyy-mm-dd", ��������������"yyyy-mm-dd 23:59:59"
	 * 
	 * @param String
	 *            today
	 * @return Date
	 * @throws
	 */
	public static Date getTodayLastTime(String today) {
		String todayLastTime = today + " 23:59:59";
		return strToDateTime(todayLastTime, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
	}
	/**
	 * dateStr���ַ�������"yyyymmddhhmiss", ��������������"yyyy��mm��dd��,hhʱmi��ss��"
	 * 
	 * @param String
	 *            dateStr
	 * @return String
	 * @throws
	 */
	public static String timeStrToTimeCNFormat(String dateStr) {
		String y = dateStr.substring(0, 4) + "��";
		String m = dateStr.substring(4, 6) + "��";
		String d = dateStr.substring(6, 8) + "��";
		String h = dateStr.substring(8, 10) + "ʱ";
		String i = dateStr.substring(10, 12) + "��";
		String s = dateStr.substring(12, 14) + "��";
		return (y + m + d + h + i + s);
	}
	/**
	 * dateStr���ַ�������"yyyymmddhhmiss", ��������������"yyyy��mm��dd��"
	 * 
	 * @param String
	 *            dateStr
	 * @return String
	 * @throws
	 */
	public static String timeStrToDateCNFormat(String dateStr) {
		String y = dateStr.substring(0, 4) + "��";
		String m = dateStr.substring(4, 6) + "��";
		String d = dateStr.substring(6, 8) + "��";
		return (y + m + d);
	}
	/**
	 * ����ʱ��֮�������
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static float getDaysBy2Time(String date1, String date2, String format) {
		float day = 0;
		if (date1 == null || date1.equals("")) {
			return 0;
		}
		if (date2 == null || date2.equals("")) {
			return 0;
		}
		// ת��Ϊ��׼ʱ��
		SimpleDateFormat myFormatter = new SimpleDateFormat(format);
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}
	/**
	 * ����ʱ��֮�������
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Integer getSecondsBy2Time(String date1, String date2, String format) {
		Integer seconds = null;
		if (date1 == null || date1.equals("")) {
			return 0;
		}
		if (date2 == null || date2.equals("")) {
			return 0;
		}
		// ת��Ϊ��׼ʱ��
		SimpleDateFormat myFormatter = new SimpleDateFormat(format);
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
			seconds = (int) (date.getTime() - mydate.getTime()) / 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seconds;
	}
	public static Date GMTStringToData(String strgmt) {
		if (strgmt != null && strgmt.length() > 0) {
			try {
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String oldtime = strgmt.replace('T', ' ').substring(0, strgmt.indexOf('+'));
				Date sourceDate = sf.parse(oldtime);
				return sourceDate;
			} catch (ParseException e) {
			}
		}
		return null;
	}
	public static String GMTStringToDataString(String strgmt) {
		if (strgmt != null && strgmt.length() > 0) {
			try {
				String oldtime = strgmt.replace('T', ' ').substring(0, strgmt.indexOf('+'));
				return oldtime;
			} catch (Exception e) {
			}
		}
		return null;
	}
	public static String minusTime(Date startDate, Date endDate) {
		if (null == startDate || null == endDate) {
			return "0";
		}
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		if (endTime < startTime) {
			return "0";
		}
		long between = endTime - startTime;
		//long dayTime = between / (24 * 60 * 60 * 1000);
		long hourTime = (between / (60 * 60 * 1000));
		long minuteTime = ((between / (60 * 1000)) -  hourTime * 60);
		long secondTime = (between / 1000 - hourTime * 60 * 60 - minuteTime * 60);
		String spendTime = formatTime(String.valueOf(hourTime)) + ":" + formatTime(String.valueOf(minuteTime)) + ":"
				+ formatTime(String.valueOf(secondTime));
		return spendTime;
	}
	public static String getIntervalIime(long between) {
		long dayTime = between / (24 * 60 * 60 * 1000);
		long hourTime = (between / (60 * 60 * 1000) - dayTime * 24);
		long minuteTime = ((between / (60 * 1000)) - dayTime * 24 * 60 - hourTime * 60);
		long secondTime = (between / 1000 - dayTime * 24 * 60 * 60 - hourTime * 60 * 60 - minuteTime * 60);
		String spendTime = String.valueOf(dayTime) + " ��" + formatTime(String.valueOf(hourTime)) + " Сʱ" + formatTime(String.valueOf(minuteTime)) + " ����"
				+ formatTime(String.valueOf(secondTime))+" ��";
		return spendTime;
	}
	public static int compare_date(String DATE1, String DATE2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		if (null == DATE2) {
			return 3;
		}
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 ��dt2ǰ");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1��dt2��");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 3;
	}
	
	public static int compare_date(Date dt1, Date dt2) {
		try {
			if (dt1.getTime() > dt2.getTime()) {
				// System.out.println("dt1 ��dt2ǰ");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// System.out.println("dt1��dt2��");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 3;
	}
	
	
	private static String formatTime(String timeStr) {
		if (timeStr.length() == 1) {
			timeStr = "0" + timeStr;
		}
		return timeStr;
	}
	public static void main(String[] args) throws InterruptedException {
//		Date t1 = new Date();
//		System.out.println(timeStrToTimeCNFormat(SdsDateUtil.dateTimeToStr(t1, SdsDateUtil.DATE_TIME_FORMAT_YYYYMMDDHHMISS)));
		
		//1408464000000
		//1408377600000
		//0000086400000
		//insertdate:[1408377600000 TO 1408464000000]
		Date d = new Date(1403702683000l);
		System.out.println("d="+DateUtil.dateToStr(d, DateUtil.DATE_FORMAT_YYYY_MM_DD));
		Date date = strToDateTime("2014-08-01 00:00:00", DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
		System.out.println("date="+date.getTime());
		 date = strToDateTime("2015-08-07 00:00:00", DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
			System.out.println("date2="+date.getTime());
		
		//long ss = 1425139200000l-1406217600000l;
	}
}
