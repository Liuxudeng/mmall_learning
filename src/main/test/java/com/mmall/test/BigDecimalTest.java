package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {
    @Test
    public void test1(){
        System.out.println(0.05+0.01);
        System.out.println(1.0-0.41);
        System.out.println(0.05*0.01);
        System.out.println(0.089/0.1);
    }

    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));

    }

    @Test
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.06");
        BigDecimal b2 = new BigDecimal("0.02");
        System.out.println(b1.add(b2));

    }


}
