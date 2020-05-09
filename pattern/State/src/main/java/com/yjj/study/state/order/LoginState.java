package com.yjj.study.state.order;

public class LoginState extends OrderState{

    public void createOrder() {

        System.out.println("已登录，开始创建订单");
        this.orderContext.setState(this.orderContext.pay);
        this.orderContext.getState().createOrder();
    }
}
