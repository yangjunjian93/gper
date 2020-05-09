package com.yjj.study.dynamic.yjjproxy.proxy;

import java.lang.reflect.Method;

/**
 * 这里就只是单纯的模仿一下InvocationHandler的设计搞个接口
 */
public interface YjjInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
