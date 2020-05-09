package com.yjj.study.dynamic.cglibproxy;

public class CglibDynamicTest {

    public static void main(String[] args) {

        CglibDynamicProxy proxy = new CglibDynamicProxy();
        OrderDao dao = (OrderDao) proxy.getInstance(OrderDao.class);
        dao.insert();
        dao.delete();
        dao.update();

    }

}
