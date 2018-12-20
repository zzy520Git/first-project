package com.firstproject.common.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Description：线程安全的日期时间处理工具类
 *
 * @author zhouzhongyi1
 * @date 2018/12/20 10:49
 */
public final class DateTimeUtil {
    public static void main(String[] args) {
        Date date1 = newDate(2016,2,29,0,0,1);
        Date date2 = newDate(2016,3,1,0,0,0);
        System.out.println(periodDays(date1, date2));
    }
    /**
     * 线程安全
     */
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * 根据字符串格式化日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 根据枚举类格式化日期
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static String format(Date date, DateFormatPattern formatPattern) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(formatPattern.getPattern()).format(date);
    }

    /**
     * 默认字符串呈现
     * @param date
     * @return
     */
    public static String toString(Date date) {
        return format(date, DateFormatPattern.YYYYMMDDHHMMSS);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date strToDate(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 字符串转日期(按枚举格式)
     *
     * @param date
     * @param formatPattern
     * @return
     */
    public static Date str2Date(String date, DateFormatPattern formatPattern) {
        try {
            return new SimpleDateFormat(formatPattern.getPattern()).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 判断param是否在start和end之间；(start,end)不包含两端
     *
     * @param param
     * @param start
     * @param end
     * @return
     */
    public static boolean betweenDate(Date param, Date start, Date end) {
        if (start.after(end)) {
            return false;
        }
        return param.after(start) && param.before(end);
    }

    /**
     * 求两个日期之间存在多少个24小时，向下取整
     * 例如47.9小时，只算一天
     *
     * @param start
     * @param end
     * @return
     */
    public static long periodDays(Date start, Date end) {
        return ChronoUnit.DAYS.between(start.toInstant(), end.toInstant());
    }

    /**
     * 当前date日期的下一天,实际是加24小时
     * @param date
     * @return
     */
    public static Date plusOneDay(Date date) {
        LocalDateTime time = date.toInstant().atZone(ZONE_ID).toLocalDateTime();
        time = time.plusDays(1);
        return Date.from(time.atZone(ZONE_ID).toInstant());
    }

    /**
     * 当前date日期的上一天,实际是减24小时
     * @param date
     * @return
     */
    public static Date minusOneDay(Date date) {
        LocalDateTime time = date.toInstant().atZone(ZONE_ID).toLocalDateTime();
        time = time.minusDays(1);
        return Date.from(time.atZone(ZONE_ID).toInstant());
    }

    /**
     * 根据年月日时分秒创建Date
     *
     * @param year   1970+
     * @param month  1-12
     * @param day    dayOfMonth 1-31
     * @param hour   0-23
     * @param minute 0-59
     * @param second 0-59
     * @return
     */
    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute, second);
        return Date.from(time.atZone(ZONE_ID).toInstant());
    }

    /**
     * 日期设置年月日，返回一个副本
     *
     * @param date  需要设置年月日的日期
     * @param year  1970+
     * @param month 1-12
     * @param day   dayOfMonth 1-31
     * @return 时分秒不变的Date副本
     */
    public static Date setDate(Date date, int year, int month, int day) {
        LocalDateTime time = date.toInstant().atZone(ZONE_ID).toLocalDateTime();
        time = time.withYear(year).withMonth(month).withDayOfMonth(day);
        return Date.from(time.atZone(ZONE_ID).toInstant());
    }

    /**
     * 日期设置时分秒毫秒，返回一个副本
     *
     * @param date        需要设置时分秒毫秒的日期
     * @param hour        0-23
     * @param minute      0-59
     * @param second      0-59
     * @param millisecond 0-999
     * @return 年月日不变的Date副本
     */
    public static Date setTime(Date date, int hour, int minute, int second, int millisecond) {
        long mill = date.getTime();
        mill = (mill / 1000) * 1000 + millisecond;
        Date d = new Date(mill);
        LocalDateTime time = d.toInstant().atZone(ZONE_ID).toLocalDateTime();
        time = time.withHour(hour).withMinute(minute).withSecond(second);
        return Date.from(time.atZone(ZONE_ID).toInstant());
    }

}
