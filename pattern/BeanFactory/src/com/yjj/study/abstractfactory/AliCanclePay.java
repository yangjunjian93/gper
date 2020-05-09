package com.yjj.study.abstractfactory;

public class AliCanclePay implements ICancelPay{
    @Override
    public void cancelPay() {
        System.out.println("支付宝取消支付了");
    }
}
