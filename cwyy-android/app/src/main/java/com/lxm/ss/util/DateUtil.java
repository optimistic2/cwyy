package com.lxm.ss.util;


import android.annotation.SuppressLint;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式化
 * 
 * @author tyrbl
 *
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
  public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
  public static final String MM_DD_HH_MM_2 = "MM月dd日 HH:mm";
  public static final String YYYY_MM_DD_HH_MM_3 = "yyyy年MM月dd日 HH:mm";
  public static final String YYYY_MM_DD_HH_MM_4 = "yyyy年MM月";
  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYY = "yyyy";
  public static final String MM_DD = "MM/dd";
  public static final String MM = "MM";
  public static final String DD = "dd";
  public static final String MM_DD_E_HH_MM = "MM/dd E HH:mm";
  public static final String MM_DD_HH_MM = "MM/dd HH:mm";
  public static final String HH_MM = "HH:mm";
  public static final String EEE_HH_MM = "EEE HH:mm";
  public static final String YY_MM_DD = "yy-MM-dd";

  /**
   * 格式化2个时间戳，同一天的去掉第二个的日期；07/29 （周三） 13:00 ~ 17:00
   * 
   * @param date1
   * @param date2
   * @param formatStr1
   * @param formatStr2
   * @return
   */
  @SuppressWarnings("deprecation")
  public static String long2string(long date1, long date2, String formatStr1, String formatStr2) {
    Date d1 = new Date(date1);
    Date d2 = new Date(date2);

    SimpleDateFormat sdf1 = new SimpleDateFormat(formatStr1);
    String str1 = sdf1.format(d1);
    String str2 = null;
    if (d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth()
        && d1.getDate() == d2.getDate()) {
      SimpleDateFormat sdf2 = new SimpleDateFormat(formatStr2);
      str2 = sdf2.format(d2);
    } else {
      str2 = sdf1.format(d2);
    }
    return str1 + " ~ " + str2;
  }

  public static String long2string(long date1, String formatStr1) {
    Date d1 = new Date(date1);

    SimpleDateFormat sdf1 = new SimpleDateFormat(formatStr1);
    String str1 = sdf1.format(d1);
    return str1;
  }

  /**
   * 格式化日期对象
   * 
   * @param date
   * @param formatStr
   * @return
   */
  public static Date date2date(Date date, String formatStr) {
    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    String str = sdf.format(date);
    try {
      date = sdf.parse(str);
    } catch (Exception e) {
      return null;
    }
    return date;
  }

  /**
   * 时间对象转换成字符串
   * 
   * @param date
   * @param formatStr
   * @return
   */
  public static String date2string(Date date, String formatStr) {
    String strDate = "";
    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    strDate = sdf.format(date);
    return strDate;
  }

  /**
   * sql时间对象转换成字符串
   * 
   * @param timestamp
   * @param formatStr
   * @return
   */
  public static String timestamp2string(Timestamp timestamp, String formatStr) {
    String strDate = "";
    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    strDate = sdf.format(timestamp);
    return strDate;
  }

  /**
   * 字符串转换成时间对象
   * 
   * @param dateString
   * @param formatStr
   * @return
   */
  public static Date string2date(String dateString, String formatStr) {
    Date formateDate = null;
    DateFormat format = new SimpleDateFormat(formatStr);
    try {
      formateDate = format.parse(dateString);
    } catch (ParseException e) {
      return null;
    }
    return formateDate;
  }

  /**
   * Date类型转换为Timestamp类型
   * 
   * @param date
   * @return
   */
  public static Timestamp date2timestamp(Date date) {
    if (date == null) return null;
    return new Timestamp(date.getTime());
  }

  /**
   * 指定时间距离当前时间的中文信息
   * 
   * @param time
   * @return
   */
  public static String getLnow(long time) {
    Calendar cal = Calendar.getInstance();
    long timel = cal.getTimeInMillis() - time;
    if (timel / 1000 < 60) {
      return "1分钟以内";
    } else if (timel / 1000 / 60 < 60) {
      return timel / 1000 / 60 + "分钟前";
    } else if (timel / 1000 / 60 / 60 < 24) {
      return timel / 1000 / 60 / 60 + "小时前";
    } else {
      return timel / 1000 / 60 / 60 / 24 + "天前";
    }
  }

  /**
   * 格式化聊天时间
   * 
   * @param date
   * @return
   */
  @SuppressWarnings("deprecation")
  public static String getChatTime(Date date) {
    Date now = new Date();
    SimpleDateFormat sdf1 = null;
    String str1 = null;
    long diffDay = getDiffDay(date, now);
    Zlog.ii("sfj:" + diffDay);
    Zlog.ii("sfj:" + date.getDay());
    if (diffDay == 0) {
      sdf1 = new SimpleDateFormat(HH_MM);
      str1 = sdf1.format(date);
    } else if (diffDay == 1) {
      sdf1 = new SimpleDateFormat(HH_MM);
      str1 = "昨天 " + sdf1.format(date);
    } else if (date.getDay() > 2 && diffDay < 7) {
      sdf1 = new SimpleDateFormat(EEE_HH_MM);
      str1 = sdf1.format(date);
    } else {
      sdf1 = new SimpleDateFormat(EEE_HH_MM);
      str1 = sdf1.format(date);
    }

    return str1;
  }

  public static Long getDiffDay(Date beginDate, Date endDate) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String strBeginDate = format.format(beginDate);

    String strEndDate = format.format(endDate);
    return getDiffDay(strBeginDate, strEndDate);
  }

  public static Long getDiffDay(String beginDate, String endDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Long checkday = 0l;
    // 开始结束相差天数
    try {
      checkday =
          (formatter.parse(endDate).getTime() - formatter.parse(beginDate).getTime())
              / (1000 * 24 * 60 * 60);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      checkday = null;
    }
    return checkday;
  }

  /**
   * 格式化时间，不足两位的前面补0
   * 
   * @param time
   * @return
   */
  public static String formatTime(int time) {
    if (time > 9) {
      return String.valueOf(time);
    } else {
      return "0" + time;
    }
  }

  public static long getMillionSeconds(String date, String formatStr){
    SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
    long millionSeconds = 0;
    try {
      millionSeconds = sdf.parse(date).getTime();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return millionSeconds;
  }

}
