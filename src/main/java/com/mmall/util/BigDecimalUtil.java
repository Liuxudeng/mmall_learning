package com.mmall.util;

import java.math.BigDecimal;

public class BigDecimalUtil {

    private BigDecimalUtil(){

    }

        //加减乘除方法，先写加法
    public static BigDecimal  add(double v1,double v2){
        //把double转成string
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2);
    }

    //减法
    public static BigDecimal sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2);
    }

    //乘法
    public static BigDecimal mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2);
    }
    //除法
    public static BigDecimal div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        //除不尽的情况，第二个参数指明保留几位小数，第三个参数指明按照四舍五入的方式保留数据
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);


    }



}
