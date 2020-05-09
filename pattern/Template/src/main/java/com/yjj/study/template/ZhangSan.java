package com.yjj.study.template;

public class ZhangSan extends Shopping{


    protected void createOrder() {
        System.out.println("开始下单，付款200元");
    }

    protected void buyProduct() {
        System.out.println("购买了篮球");
    }

    protected void registerUser() {
        System.out.println("注册了账号张三");
    }
}
