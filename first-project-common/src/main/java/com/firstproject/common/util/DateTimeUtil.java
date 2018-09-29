package com.firstproject.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Description：
 * 日期转换工具类
 * @author zhouzhongyi1
 * DATE： 2018/9/28 18:23
 */
public class DateTimeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtil.class) ;
    private static final ZoneId  zoneId = ZoneId.systemDefault() ;

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        try {
            return Date.from(localDateTime.atZone(zoneId).toInstant()) ;
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.localDateTime2Date方法异常，localDateTime={}", localDateTime, e);
        }
        return null ;
    }
    public static LocalDateTime date2LocalDateTime(Date date) {
        try {
            return date.toInstant().atZone(zoneId).toLocalDateTime() ;
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.date2LocalDateTime方法异常，date={}", date, e);
        }
        return null ;
    }

    /**
     *
     * @param dateTimeStr 必须都要包含日期和时间，否则异常
     * @param pattern 日期字符串模式
     * @return
     */
    @Deprecated
    public static Date str2Date(String dateTimeStr, String pattern) {
        try {
            //该方法少用，极易出错
            LocalDateTime temp =  LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern)) ;
            LOGGER.warn("DateTimeUtil.str2Date方法调用，dateTimeStr={},pattern={}", dateTimeStr, pattern);
            return DateTimeUtil.localDateTime2Date(temp) ;
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.str2Date方法异常，dateTimeStr={},pattern={}", dateTimeStr, pattern);
        }
        return null ;
    }
    @Deprecated
    public static String date2Str(Date date, String pattern) {
        try {
            LocalDateTime temp =  DateTimeUtil.date2LocalDateTime(date) ;
            return temp.format(DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.date2Str方法异常，date={},pattern={}", date, pattern, e);
        }
        return null ;
    }

    public static void main(String[] args) {
        /**
         * 实验证明，效率较低
         */
        long start = System.currentTimeMillis() ;
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        long end = System.currentTimeMillis() ;
        System.out.println(end-start);

        /**
         * 实验证明，效率更高
         */
        start = System.currentTimeMillis() ;
        System.out.println(dateToStr(new Date(), "yyyy-MM-dd"));
        end = System.currentTimeMillis() ;
        System.out.println(end-start);

    }

    public static Date strToDate(String d, String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(d) ;
        } catch (ParseException e) {
            LOGGER.error("DateTimeUtil.strToDate方法异常，d={},pattern={}", d, pattern, e);
        }
        return null ;
    }
    public static String dateToStr(Date date, String pattern){
        try {
            return new SimpleDateFormat(pattern).format(date) ;
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.dateToStr方法异常，date={},pattern={}", date, pattern, e);
        }
        return null ;
    }

}











