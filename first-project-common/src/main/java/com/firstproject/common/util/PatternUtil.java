package com.firstproject.common.util;

import java.util.regex.Pattern;

/**
 * Description：线程安全
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/19 13:55
 */
public class PatternUtil {
    private PatternUtil() {}

    public static Pattern getDecimalPattern() {
        return DecimalPatternClass.DecimalPattern ;
    }
    private static class DecimalPatternClass {
        private static final Pattern  DecimalPattern = Pattern.compile("^[-\\+]?[0-9]+(\\.[0-9]+)?$") ;
    }

    public static Pattern getRealDecimalPattern() {
        return RealDecimalPatternClass.RealDecimalPattern ;
    }
    private static class RealDecimalPatternClass {
        private static final Pattern  RealDecimalPattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?([eE][-+]?[0-9]+)?$") ;
    }

    public static Pattern getIntegerPattern() {
        return IntegerPatternClass.IntegerPattern ;
    }
    private static class IntegerPatternClass {
        private static final Pattern  IntegerPattern = Pattern.compile("^[-\\+]?[0-9]+$") ;
    }
}
