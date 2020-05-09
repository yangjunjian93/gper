package com.yjj.study.method;

public class AliPay implements IPay {


    @Override
    public void pay() {
        System.out.println("使用了支付宝支付!");
    }


}
