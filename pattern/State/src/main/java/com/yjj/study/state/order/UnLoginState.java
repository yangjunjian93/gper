package com.yjj.study.state.order;

public class UnLoginState extends OrderState{

    public void createOrder() {

        System.out.println("没有登录！请进行登录");
        this.orderContext.setState(this.orderContext.login);
        this.orderContext.getState().createOrder();
    }
}
