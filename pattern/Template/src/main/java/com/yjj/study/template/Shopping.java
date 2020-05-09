package com.yjj.study.template;

public abstract class Shopping {

    public void shopping(){

        // 1.打开网站
        openHttp();
        // 2.注册账号
        registerUser();
        // 3.购买商品
        buyProduct();
        // 4.下单
        createOrder();
        // 5.退出网站
        closeHttp();


    }

    private void closeHttp() {
        System.out.println("关闭了淘宝。");
    }

    protected abstract void createOrder();

    protected abstract void buyProduct();

    protected abstract void registerUser();

    private void openHttp() {
        System.out.println("打开了淘宝");
    }

}
