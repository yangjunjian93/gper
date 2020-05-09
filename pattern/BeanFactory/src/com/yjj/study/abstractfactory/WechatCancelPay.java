package com.yjj.study.abstractfactory;

public class WechatCancelPay implements ICancelPay{
    @Override
    public void cancelPay() {
        System.out.println("微信取消支付了。");
    }
}
