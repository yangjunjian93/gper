package com.yjj.study.abstractfactory;

public class AliPay implements IPay{


    @Override
    public void pay() {
        System.out.println("支付宝支付了");
    }
}
