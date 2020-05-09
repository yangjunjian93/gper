package com.yjj.study.simple;

public class PaySimpleFactory {

    public IPay createPay(String payType) {
        if("wechat".equals(payType)){
            return new WechatPay();
        }else{
            return new AliPay();
        }
    }

}
