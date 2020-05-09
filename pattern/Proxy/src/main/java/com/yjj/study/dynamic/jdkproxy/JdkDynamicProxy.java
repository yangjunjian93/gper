package com.yjj.study.dynamic.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 这里的核心就是通过Proxy创建出来的对象的任何方法
 * 执行的时候都是走的接口的invoke方法，
 * invoke方法在当前代理类中被重写了，
 * 所以代理对象不管执行什么方法都会被增强！
 */
public class JdkDynamicProxy implements InvocationHandler {

    private IDao dao;
    public IDao getInstance(IDao dao) {
        this.dao = dao;
        Class<? extends IDao> clazz = dao.getClass();
        return (IDao) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        before();
        Object o = method.invoke(this.dao, args);
        after();
        return o;
    }

    private void after() {
        System.out.println("关闭了数据库连接");
    }

    private void before() {
        System.out.println("打开了数据库连接");
    }
}
