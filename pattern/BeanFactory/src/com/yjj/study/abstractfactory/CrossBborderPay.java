package com.yjj.study.abstractfactory;

public class CrossBborderPay implements IPay{
    @Override
    public void pay() {
        System.out.println("跨境支付拉");
    }
}
