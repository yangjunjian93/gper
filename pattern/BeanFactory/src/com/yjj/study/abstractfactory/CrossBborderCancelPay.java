package com.yjj.study.abstractfactory;

public class CrossBborderCancelPay implements ICancelPay{
    @Override
    public void cancelPay() {
        System.out.println("跨境取消支付拉！");
    }
}
