package com.springboot.cs.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yyw
 * @Description: 时间工具类
 * @date 2018/6/2816:03
 */
public class DateUtil {

    public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT2 = "yyyy-MM-dd";

    public static final String FORMAT3 = "yyyyMMDDHHmmss";

    public static final String FORMAT4 = "yyyyMMdd";

    public static final String FORMAT5 = "MM/dd HH:mm";

    /**
     * 获取当前时间
     * @return
     */
    public static String getNow() {
        return format(new Date(),FORMAT1);
    }

    /**
    　　* @Description: 日期格式化
    　　* @author yyw
    　　* @date 2018/7/24 14:06
    */
    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }

    /**
    　　* @Description: 日期转换
    　　* @author yyw
    　　* @date 2018/7/26 14:42
    */
    public static Date parse(String dateStr,String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
    　　* @Description: 两个时间相减
    　　* @author yyw
    　　* @date 2018/7/26 11:01
    */
    public static Long subtractTime(Date bigDate,Date smallDate) {
        return bigDate.getTime()-smallDate.getTime();
    }

    public static Date addDay(Date date,Integer count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date time = c.getTime();
        return time;
    }

    /**
     * 获取当月的第几天
     *
     * @return
     */
    public static int getNowMonDay() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.DATE);
    }



    public static String getYear(Date date) {
        return format(date,"yyyy");
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getNowYear() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getNowMonth() {
        Calendar d = Calendar.getInstance();
        return d.get(Calendar.MONTH) + 1;
    }

//    /**
//     * 獲取拼接後的時間
//     * @return
//     */
//    public static Date getParse(String date){
//
//        try {
//            String format = DateUtil.format(new Date(), FORMAT2);
//            String s = format+" "+date+":00";
//            System.out.println(s);
//            return parse(s,FORMAT1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("時間格式出錯");
//        }
//    }

    /**
     * 將yyyy-MM-dd和HH:mm時間格式轉成時間格式
     * @param date
     * @param time
     * @return
     */
    public static Date getComplete(String date,String time){
        String s = date+" "+time+":00";
        return parse(s,FORMAT1);
    }

    /**
     * 增加时间（秒）
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, seconds);
        return c.getTime();
    }

    /**
     * 增加时间（分）
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, minutes);
        return c.getTime();
    }

    /**
     * 增加时间（时）
     *
     * @param date
     * @param hours
     * @return
     */
    public static Date addHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    /**
     * 增加时间（月）
     *
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * @Description 增加日期（天）
     * @author hudl
     * @date 2017年9月12日 下午4:05:14
     * @param date
     * @param days
     * @return
     * @lastModifier
     */
    public static Date addDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }

    public static String getYMDHS(Date date){
        String format1 = format(date, FORMAT1);
        return format1.substring(0,16);
    }

    public static String getMonthAndDay(Date date){
        String format = format(date, FORMAT4);
        return format.substring(4,6) + "月" + format.substring(6,8) + "号";
    }

    /**
     * 根据日期获得星期
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
//        String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        String[] weekDays = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
//        String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[intWeek];
    }

    /**
     * 將mm/dd/yyyy的格式轉成時間
     * @param s
     */
    public static String formatmmddYYYY(String s){
        // 01/34/6789
        String mm = s.substring(0, 2);
        String dd = s.substring(3, 5);
        String yyyy = s.substring(6, 10);
        String date = yyyy+mm+dd;

//        System.out.println("拼接==="+date);

        Date result = parse(date, FORMAT4);
//        System.out.println("結果==="+result);

        return format(result,FORMAT2);
    }

    /**
     * 將時間格式轉為mm/dd/yyyy
     * @param date
     */
    public static String getmmddYYYY(Date date){
        String format = format(date, FORMAT2);

        String yyyy = format.substring(0, 4);
        String mm = format.substring(5, 7);
        String dd = format.substring(8, 10);
        return mm+"/"+dd+"/"+yyyy;
    }

    /**
     * 將時間格式轉成HH:mm
     * @param date
     * @return
     */
    public static String getHHmm(Date date){
        String format = format(date, FORMAT1);
        String substring = format.substring(11, 16);
        return substring;
    }

    public static void main(String[] args) {
//        String dateStr = "2010-1-2 1:21:28";
//        String dateStr2 = "2010-1-2 23:21:28";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try
//        {
//            Date date2 = format.parse(dateStr2);
//            Date date = format.parse(dateStr);
//
//            Date d1 = new Date();
//            Date d2 = new Date();
//
//            System.out.println("两个日期的差距：" + differentDaysByMillisecond(d1,d2));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        String weekOfDate = getWeekOfDate(new Date());
//        System.out.println(weekOfDate);
//        String eng = LanguageEnums.Week.getMessage(DateUtil.getWeekOfDate(new Date()), "zh_hk");
//        System.out.println(eng);

//        String s = getmmddYYYY(new Date());
//        System.out.println("======"+s);
//        Date date = formatmmddYYYY("07/18/2019");
//        System.out.println(date);
//        System.out.println(getmmddYYYY(date));
//        getComplete("sa","sda");
//        Date parse = DateUtil.parse(DateUtil.format(new Date(), DateUtil.FORMAT2), DateUtil.FORMAT2);
//        System.out.println(parse);
//        if (DateUtil.differentDaysByMillisecond(new Date(),parse) <= 0){
//            System.out.println("當前時間<=0");
//        }
//        Date complete = getComplete("2019-07-25", "15:23");
//        Date now = new Date();
//        Long l = complete.getTime() - now.getTime();
//        if ( (complete.getTime()-now.getTime()) <= 0 ){
//            throw new RuntimeException();
//        }
//        Date now = new Date();
//        System.out.println(format(now,FORMAT2));
//        Date date = addDays(now, 90);
//        System.out.println(format(date,FORMAT2));


        String fromTime = "2019-10-10" + " 00:00:00";
        Date startTime = DateUtil.parse(fromTime, DateUtil.FORMAT1);

        String endTime = "2019-11-11" + " 23:59:59";
        Date lastTime = DateUtil.parse(endTime, DateUtil.FORMAT1);

        System.out.println(startTime);
        System.out.println(lastTime);

    }



}
