package com.yjj.study.method;

public class WechatPayFactory implements IPayMethodFactory{
    @Override
    public IPay create() {

        return new WechatPay();

    }
}
