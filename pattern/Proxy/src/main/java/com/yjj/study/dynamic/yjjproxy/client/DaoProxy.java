package com.yjj.study.dynamic.yjjproxy.client;

import com.yjj.study.dynamic.yjjproxy.proxy.YjjClassLoader;
import com.yjj.study.dynamic.yjjproxy.proxy.YjjInvocationHandler;
import com.yjj.study.dynamic.yjjproxy.proxy.YjjProxy;

import java.lang.reflect.Method;

public class DaoProxy implements YjjInvocationHandler {

    private IDao dao ;

    public IDao getInstance(IDao dao){

        this.dao = dao;

        return (IDao) YjjProxy.getProxyInstance(new YjjClassLoader(), dao.getClass().getInterfaces(), this);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();
        Object object = method.invoke(this.dao, args);
        after();

        return object;
    }

    private void after() {
        System.out.println("关闭了数据库连接");
    }

    private void before() {
        System.out.println("打开了数据库连接");
    }

}
