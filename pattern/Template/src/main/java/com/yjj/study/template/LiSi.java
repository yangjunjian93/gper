package com.yjj.study.template;

public class LiSi extends Shopping{
    protected void createOrder() {
        System.out.println("付款了100元");
    }

    protected void buyProduct() {
        System.out.println("购买了足球");
    }

    protected void registerUser() {
        System.out.println("注册了账号李四");
    }
}
