package com.firstproject.common.util.time;

import lombok.Getter;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * @date 2018/12/20 12:35
 */
public enum DateFormatPattern {
    /**
     * 斜杠分割年月日
     */
    YMD("yyyy/MM/dd"),
    /**
     * 横杠分割年月日
     */
    YYYYMMDD("yyyy-MM-dd"),
    /**
     * 时分秒
     */
    HHMMSS("HH:mm:ss"),
    /**
     * 斜杠分割年月日，时分秒
     */
    YMDHHMMSS("yyyy/MM/dd HH:mm:ss"),
    /**
     * 横杠分割年月日，时分秒
     */
    YYYYMMDDHHMMSS("yyyy-MM-dd HH:mm:ss");

    /**
     * 格式化日期模式
     */
    @Getter
    private String pattern;

    /**
     * 构造方法
     *
     * @param pattern 模式
     */
    DateFormatPattern(String pattern) {
        this.pattern = pattern;
    }
}
