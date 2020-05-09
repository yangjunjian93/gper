package com.yjj.study.simple;

public class WechatPay implements IPay{


    @Override
    public void pay() {
        System.out.println("使用了微信支付!");
    }


}
