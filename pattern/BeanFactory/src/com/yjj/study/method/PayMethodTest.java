package com.yjj.study.method;

public class PayMethodTest {

    public static void main(String[] args) {
        IPayMethodFactory factory = new WechatPayFactory();
        factory.create().pay();
    }

}
