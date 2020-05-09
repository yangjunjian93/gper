package com.yjj.study.abstractfactory;



public class PayTest {

    public static void main(String[] args) {

        PayAbstractFactory factory = new UnionFactory();
        IPay pay = factory.createPay();
        pay.pay();
        ICancelPay cancelPay = factory.createCancelPay();
        cancelPay.cancelPay();


    }

}
