package com.yjj.study.simple;

public class PayTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

        PaySimpleFactory paySimpleFactory = new PaySimpleFactory();
        paySimpleFactory.createPay("wechat");
    }
}
