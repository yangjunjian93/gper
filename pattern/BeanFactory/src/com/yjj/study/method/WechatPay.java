package com.yjj.study.method;

public class WechatPay implements IPay {


    @Override
    public void pay() {
        System.out.println("使用了微信支付!");
    }


}
