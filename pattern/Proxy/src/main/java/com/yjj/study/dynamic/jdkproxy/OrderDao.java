package com.yjj.study.dynamic.jdkproxy;


public class OrderDao implements IDao{

    @Override
    public void insert() {
        System.out.println("插了订单到数据库了！");
    }

    @Override
    public void delete() {
        System.out.println("数据库删除订单拉！");
    }

    public void update(){
        System.out.println("数据库修改订单啦！");
    }


}
