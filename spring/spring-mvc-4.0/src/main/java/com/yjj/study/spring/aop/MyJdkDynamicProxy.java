package com.yjj.study.spring.aop;

import com.yjj.study.spring.aop.aspect.MyAdvice;
import com.yjj.study.spring.aop.support.MyAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class MyJdkDynamicProxy implements InvocationHandler {

    private MyAdvisedSupport config;

    public MyJdkDynamicProxy(MyAdvisedSupport config) {
        this.config = config;
    }

    // 生成自己的代理类
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), this.config.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取通知
        Map<String, MyAdvice> advice = config.getAdvice(method);
        Object returnValue = null;

        try {
            invokeAdivce(advice.get("before"));

            returnValue = method.invoke(this.config.getTarget(), args);

            invokeAdivce(advice.get("after"));
        }catch (Exception e){
            invokeAdivce(advice.get("afterThrow"));
        }

        return returnValue;
    }

    private void invokeAdivce(MyAdvice advice) {
        try {
            advice.getMethod().invoke(advice.getInstance());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
