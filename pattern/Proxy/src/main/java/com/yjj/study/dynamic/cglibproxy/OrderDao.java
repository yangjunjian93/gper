package com.yjj.study.dynamic.cglibproxy;


public class OrderDao{


    public void insert() {
        System.out.println("插了订单到数据库了！");
    }

    public void delete() {
        System.out.println("数据库删除订单拉！");
    }

    public void update() {
        System.out.println("数据库修改订单拉！");
    }


}
