package com.yjj.study.state.order;

public abstract class OrderState {

    protected OrderContext orderContext;

    public void setOrderContext(OrderContext orderContext) {
        this.orderContext = orderContext;
    }

    abstract void createOrder();

}
