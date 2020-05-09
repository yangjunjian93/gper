package com.yjj.study.state.order;

public class FinishState extends OrderState{

    public void createOrder() {

        System.out.println("支付完成，结束订单");
        return;
    }
}
