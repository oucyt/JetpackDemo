package com.yt.jetpackdemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * description
 *
 * @author tianyu
 * @create 2019.04.12 15:10
 * @since 1.0.0
 */
public class DateHelper {

    /**
     * 将时间戳格式化为指定的字符串形式
     *
     * @param milliseconds
     * @param pattern
     * @return
     */
    public static String format(long milliseconds, String pattern) {
        return format(new Date(milliseconds), pattern);
    }

    /**
     * 将 {@link Date}格式化为指定的字符串形式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);
    }

    /**
     * 计算两个时间戳的间隔天数
     *
     * @param start
     * @param end
     * @return
     */
    public static int getDaysBetween(long start, long end) {
        return getBetweenDays(new Date(start), new Date(end));
    }

    /**
     * 得到两个日期的间隔天数
     *
     * @param start
     * @param end
     * @return -1说明开始日期大于结束日期
     */
    public static int getBetweenDays(Date start, Date end) {
        if (start.after(end)) {
            return -1;
        }
        Calendar startC = Calendar.getInstance();
        startC.setTime(start);
        startC.set(Calendar.MILLISECOND, 0);
        startC.set(Calendar.SECOND, 0);
        startC.set(Calendar.MINUTE, 0);
        startC.set(Calendar.HOUR_OF_DAY, 0);


        Calendar endC = Calendar.getInstance();
        endC.setTime(end);
        endC.set(Calendar.MILLISECOND, 0);
        endC.set(Calendar.SECOND, 0);
        endC.set(Calendar.MINUTE, 0);
        endC.set(Calendar.HOUR_OF_DAY, 0);

        int days = 0;
        while (endC.after(startC)) {
            days++;
            endC.add(Calendar.DAY_OF_YEAR, -1);
        }
        return days;
//        endC.add(Calendar.DAY_OF_YEAR, 1);
//        int days = 0;
//        do {
//            days++;
//            startC.add(Calendar.DAY_OF_YEAR, 1);
//        } while (startC.before(endC));
//        return days - 1;
    }



    /**
     * 获取指定日期是周几?
     *
     * @param date
     * @return
     */
    public static String getDay(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    public static boolean within(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException("开始时间大于截止时间");
        }

        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 设置起始时刻
        calendar.set(Calendar.HOUR_OF_DAY, start);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        Date startDate = calendar.getTime();

        // 设置截止时刻
        calendar.set(Calendar.HOUR_OF_DAY, end);

        Date endDate = calendar.getTime();

//        System.out.println(format(current, "yyyyMMdd HHmmss"));
//        System.out.println(format(startDate, "yyyyMMdd HHmmss"));
//        System.out.println(format(endDate, "yyyyMMdd HHmmss"));
        return (startDate.before(current)) && (endDate.after(current));
    }


    /**
     * 获取当天0点
     *
     * @return
     */
    public static Date getToday0Clock() {
        Long  time = System.currentTimeMillis();  //当前时间的时间戳
        long zero = time/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
//        System.out.println(new Timestamp(zero));//今天零点零分零秒
//        System.out.println(zero/1000);
        return new Date(zero);
    }

    public static Date getToday24Clock(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),23,59,59);
        long tt = calendar.getTime().getTime();
//        System.out.println(tt);
//        System.out.println(new Timestamp(tt));

        return new Date(tt);
    }

    public static void main(String[] args) {
        getToday0Clock();
        getToday24Clock();
    }
}
