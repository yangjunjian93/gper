package com.yjj.study.method;

import com.yjj.study.abstractfactory.*;
import com.yjj.study.abstractfactory.AliPay;
import com.yjj.study.abstractfactory.IPay;

public class AliPayFactory extends PayAbstractFactory {

    @Override
    protected IPay createPay() {
        return new AliPay();
    }

    @Override
    protected ICancelPay createCancelPay() {
        return new AliCanclePay();
    }
}
