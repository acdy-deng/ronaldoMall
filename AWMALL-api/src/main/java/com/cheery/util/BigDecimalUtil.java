package com.cheery.util;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @desc: BigDecimal工具类 用于解决精度缺失的问题
 * @className: BigDecimalUtil
 * @author: RONALDO
 * @date: 2019-03-04 15:16
 */
@NoArgsConstructor
public class BigDecimalUtil {

    public static BigDecimal add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        //除不尽的情况 四舍五入,保留2位小数
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

}
