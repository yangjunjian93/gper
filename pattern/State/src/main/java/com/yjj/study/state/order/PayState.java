package com.yjj.study.state.order;

public class PayState extends OrderState{


    public void createOrder() {

        System.out.println("开始支付订单");
        this.orderContext.setState(this.orderContext.finish);
        this.orderContext.getState().createOrder();
    }
}
