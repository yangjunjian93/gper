package com.yjj.study.dynamic.cglibproxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


public class CglibDynamicProxy implements MethodInterceptor {

    public Object getInstance(Class clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     *
     * @param o             代理对象
     * @param method        被代理的方法，这里不能调用，一调用就无限循环进入该方法了！
     * @param objects       方法入参！
     * @param methodProxy   代理方法，这里可以执行原来的方法或者自己的方法，但是在当前方法中不能执行自己的方法，
     *                      也会无限循环进入该方法，这里正确的做法应该是执行父类的方法！因为代理对象是被代理对象的子类
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        before();
        Object obj = methodProxy.invokeSuper(o, objects);
        after();

        return obj;
    }

    private void after() {
        System.out.println("关闭了数据库连接");
    }

    private void before() {
        System.out.println("打开了数据库连接");
    }
}
