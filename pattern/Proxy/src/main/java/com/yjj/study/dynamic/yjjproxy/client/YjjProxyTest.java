package com.yjj.study.dynamic.yjjproxy.client;

public class YjjProxyTest {

    public static void main(String[] args) {

        DaoProxy proxy = new DaoProxy();
        IDao dao = proxy.getInstance(new CourseDao());
        dao.update("123", 100, true);
        System.out.println("==========");

    }

}
