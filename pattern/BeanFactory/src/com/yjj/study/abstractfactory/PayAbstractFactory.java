package com.yjj.study.abstractfactory;

public abstract class PayAbstractFactory {

    public void init(){
        System.out.println("创建订单准备支付");
    }


    protected abstract IPay createPay();

    protected abstract ICancelPay createCancelPay();

}
