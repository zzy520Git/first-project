package com.firstproject.common.util;

import java.text.SimpleDateFormat;

/**
 * Description:
 * 线程安全的SimpleDateFormat，每个线程有一个副本
 * @author zhouzhongyi
 * Date: 2018/7/28 17:06
 */
public class DateFormatUtil {
    /**
     * 转换的日期格式，按需求可进行更改
     */
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss" ;

    private static final ThreadLocal<SimpleDateFormat> localSimpleDateFormat = new ThreadLocal<>() ;

    public static SimpleDateFormat synchronizedSimpleDateFormat() {
        if(localSimpleDateFormat.get() == null) {
            localSimpleDateFormat.set(new SimpleDateFormat(PATTERN));
        }
        return localSimpleDateFormat.get() ;
    }

    public static void remove() {
        localSimpleDateFormat.remove();
    }

}
