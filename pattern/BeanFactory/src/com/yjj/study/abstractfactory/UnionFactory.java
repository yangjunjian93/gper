package com.yjj.study.abstractfactory;

public class UnionFactory extends PayAbstractFactory{
    @Override
    protected IPay createPay() {
        super.init();
        return new UnionPay();
    }

    @Override
    protected ICancelPay createCancelPay() {
        super.init();
        return new UnionCancelPay();
    }
}
