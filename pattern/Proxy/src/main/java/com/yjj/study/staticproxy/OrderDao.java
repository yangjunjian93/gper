package com.yjj.study.staticproxy;

public class OrderDao implements IDao{


    @Override
    public void insert() {
        System.out.println("插了订单到数据库了！");
    }

    @Override
    public void delete() {
        System.out.println("数据库删订单拉！");
    }
}
