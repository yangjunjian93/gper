package com.yjj.study.abstractfactory;

public class CrossBborderFactory extends PayAbstractFactory{
    @Override
    protected IPay createPay() {
        super.init();
        return new CrossBborderPay();
    }

    @Override
    protected ICancelPay createCancelPay() {
        super.init();
        return new CrossBborderCancelPay();
    }
}
