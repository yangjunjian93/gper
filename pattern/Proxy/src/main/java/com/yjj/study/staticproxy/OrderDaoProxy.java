package com.yjj.study.staticproxy;

public class OrderDaoProxy implements IDao{

    private IDao dao;

    public OrderDaoProxy(IDao dao) {
        this.dao = dao;
    }

    @Override
    public void insert() {

        System.out.println("打开数据库连接");

        dao.insert();

        System.out.println("关闭数据库连接");
    }

    @Override
    public void delete() {
        dao.delete();
    }

}
