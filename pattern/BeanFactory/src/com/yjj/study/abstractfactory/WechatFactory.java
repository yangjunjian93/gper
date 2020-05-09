package com.yjj.study.abstractfactory;

public class WechatFactory extends PayAbstractFactory{
    @Override
    protected IPay createPay() {
        super.init();
        return new WechatPay();
    }

    @Override
    protected ICancelPay createCancelPay() {
        super.init();
        return new WechatCancelPay();
    }
}
