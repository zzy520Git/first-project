package com.firstproject.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

/**
 * Description：
 *
 * @author zhouzhongyi1
 * DATE： 2018/10/19 13:44
 */
public class DecimalUtil {
    private static final BigInteger MAX_INT = new BigInteger(String.valueOf(Integer.MAX_VALUE)) ;
    private static final BigInteger MIN_INT = new BigInteger(String.valueOf(Integer.MIN_VALUE)) ;
    private DecimalUtil() {}
    public static boolean isDecimal(String digit) {
        return StringUtils.isNotBlank(digit) && PatternUtil.getDecimalPattern().matcher(digit).matches();
    }
    public static boolean isRealDecimal(String digit) {
        return StringUtils.isNotBlank(digit) && PatternUtil.getRealDecimalPattern().matcher(digit).matches();
    }
    public static boolean isInteger(String digit) {
        boolean bool = StringUtils.isNotBlank(digit) && PatternUtil.getIntegerPattern().matcher(digit).matches() ;
        if(bool) {
            BigInteger value = new BigInteger(digit) ;
            return value.compareTo(MIN_INT) >=0 && value.compareTo(MAX_INT) <=0 ;
        }
        return false ;
    }
}
