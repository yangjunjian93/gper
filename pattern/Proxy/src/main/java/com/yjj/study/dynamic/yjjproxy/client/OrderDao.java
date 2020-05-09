package com.yjj.study.dynamic.yjjproxy.client;


public class OrderDao implements IDao {

    @Override
    public void insert() {
        System.out.println("插了订单到数据库了！");
    }

    @Override
    public void delete() {
        System.out.println("数据库删除订单拉！");
    }

    @Override
    public void update(String id, int num, boolean flag){
        System.out.println("数据库修改订单啦！修改的id为: " + id + "改了" + num + "条，是否更新：" + flag );
    }


}
