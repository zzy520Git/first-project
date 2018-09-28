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
    public static Date str2Date(String dateTimeStr, String pattern) {
        try {
            LocalDateTime temp =  LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern)) ;
            return DateTimeUtil.localDateTime2Date(temp) ;
        } catch (Exception e) {
            LOGGER.error("DateTimeUtil.str2Date方法异常，dateTimeStr={},pattern={}", dateTimeStr, pattern, e);
        }
        return null ;
    }
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
        long start = System.currentTimeMillis() ;
        for(int i=0 ; i<10000 ; i++) {
            str2Date("1993-05-20","yyyy-MM-dd") ;
            date2Str(new Date(), "yyyy-MM-dd") ;
        }
        long end = System.currentTimeMillis() ;
        System.out.println(end-start);

        start = System.currentTimeMillis() ;
        for(int i=0 ; i<10000 ; i++) {
            t1("1993-05-20","yyyy-MM-dd") ;
            t2(new Date(), "yyyy-MM-dd") ;
        }
        end = System.currentTimeMillis() ;
        System.out.println(end-start);

    }

    public static Date t1(String d, String pattern){
        try {
            return new SimpleDateFormat(pattern).parse(d) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null ;
    }
    public static String t2(Date d, String pattern){
        return new SimpleDateFormat(pattern).format(d) ;
    }

}











