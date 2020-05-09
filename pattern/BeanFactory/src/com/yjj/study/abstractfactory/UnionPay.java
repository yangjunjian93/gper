package com.yjj.study.abstractfactory;

public class UnionPay implements IPay{
    @Override
    public void pay() {
        System.out.println("银联支付拉");
    }
}
