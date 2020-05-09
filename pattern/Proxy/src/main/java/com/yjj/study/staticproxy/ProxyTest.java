package com.yjj.study.staticproxy;

/**
 * 静态代理需要手动编码各种增强，每一个方法都得单独配置
 */
public class ProxyTest {

    public static void main(String[] args) {

//        OrderDao dao = new OrderDao();

        IDao dao = new OrderDaoProxy(new OrderDao());
        dao.insert();
        dao.delete();

    }

}
