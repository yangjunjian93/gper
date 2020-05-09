package com.yjj.study.state.order;

public class OrderContext {

    protected static final OrderState unLogin = new UnLoginState();
    protected static final OrderState login = new LoginState();
    protected static final OrderState pay = new PayState();
    protected static final OrderState finish = new FinishState();

    {
        unLogin.setOrderContext(this);
        login.setOrderContext(this);
        pay.setOrderContext(this);
        finish.setOrderContext(this);
    }

    private OrderState currentState = unLogin;

    public void setState(OrderState orderState){
        this.currentState = orderState;
    }

    public OrderState getState(){ return this.currentState; }


    public void createOrder(){
        this.currentState.createOrder();
    }


}
