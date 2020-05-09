package com.yjj.study.abstractfactory;

public class AliFactory extends PayAbstractFactory{
    @Override
    protected IPay createPay() {
        super.init();
        return new AliPay();
    }

    @Override
    protected ICancelPay createCancelPay() {
        super.init();
        return new AliCanclePay();
    }
}
