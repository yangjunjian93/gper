package com.yjj.study.abstractfactory;

public class UnionCancelPay implements ICancelPay{
    @Override
    public void cancelPay() {
        System.out.println("银联取消支付拉");
    }
}
