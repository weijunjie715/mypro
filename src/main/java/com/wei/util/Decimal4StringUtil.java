package com.wei.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@SuppressWarnings("all")
public class Decimal4StringUtil {

    private static final int DEFAULT_SCALE_MODE = BigDecimal.ROUND_HALF_DOWN;

    public static String add(String first, String second){
        BigDecimal firstBD = StringUtils.isNotBlank(first) ? new BigDecimal(first) : BigDecimal.ZERO;
        BigDecimal secondBD = StringUtils.isNotBlank(second) ? new BigDecimal(second) : BigDecimal.ZERO;
        return firstBD.add(secondBD).toString();
    }

    public static String sub(String first, String second){
        BigDecimal firstBD = StringUtils.isNotBlank(first) ? new BigDecimal(first) : BigDecimal.ZERO;
        BigDecimal secondBD = StringUtils.isNotBlank(second) ? new BigDecimal(second) : BigDecimal.ZERO;
        return firstBD.subtract(secondBD).toString();
    }

    public static String mul(String first, String second){
        BigDecimal firstBD = StringUtils.isNotBlank(first) ? new BigDecimal(first) : BigDecimal.ZERO;
        BigDecimal secondBD = StringUtils.isNotBlank(second) ? new BigDecimal(second) : BigDecimal.ZERO;
        return firstBD.multiply(secondBD).toString();
    }

    public static String mul(String first, String second, int scale){
        BigDecimal firstBD = StringUtils.isNotBlank(first) ? new BigDecimal(first) : BigDecimal.ZERO;
        BigDecimal secondBD = StringUtils.isNotBlank(second) ? new BigDecimal(second) : BigDecimal.ZERO;
        return firstBD.multiply(secondBD).setScale(scale, DEFAULT_SCALE_MODE).toString();
    }

    public static String div(String first, String second, int scale){
        if(StringUtils.isBlank(first) || StringUtils.isBlank(second)){
            return BigDecimal.ZERO.toString();
        }
        BigDecimal firstBD = new BigDecimal(first);
        BigDecimal secondBD = new BigDecimal(second);
        if(firstBD.compareTo(BigDecimal.ZERO) == 0){
            return firstBD.toString();
        }
        if(secondBD.compareTo(BigDecimal.ZERO) == 0){
            return "";
        }
        return firstBD.divide(secondBD, scale, DEFAULT_SCALE_MODE).toString();
    }

    public static String percent(String first, String second){
        String percentValue = Decimal4StringUtil.div(first, second, 6);
        return Decimal4StringUtil.mul(percentValue, "100", 2)+"%";
    }

    public static String percentEmpty(String first, String second){
        first = StringUtils.isBlank(first)?"0":first;
        second = StringUtils.isBlank(second)?"0":second;
        if("0".equals(second) || "0".equals(first)){
            return "";
        }
//        if("0".equals(first)){
//            return "0%";
//        }
        String percentValue = Decimal4StringUtil.div(first, second, 6);
        return Decimal4StringUtil.mul(percentValue, "100", 2)+"%";
    }

    public static BigDecimal percentDecimal(String first, String second){
        if(StringUtils.isBlank(first) || StringUtils.isBlank(second)){
            return BigDecimal.ZERO;
        }
        BigDecimal firstBD = new BigDecimal(first);
        BigDecimal secondBD = new BigDecimal(second);
        BigDecimal divide = firstBD.divide(secondBD, 6, DEFAULT_SCALE_MODE);
        return divide.multiply(new BigDecimal(100)).setScale(2, DEFAULT_SCALE_MODE);
    }

    /**
     * @Description 比较结果值  小于最小值取0
     * @Author: weijunjie
     * @Date: 2021/1/19 16:10
     **/
    public static String compareForMinValue(String first,String second){
        if((new BigDecimal(first).abs()).compareTo(new BigDecimal(second)) == -1){
            return "0";
        }else{
            return first;
        }

    }
}
