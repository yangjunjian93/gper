package com.yjj.study.spring.aop.aspect;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class MyAdvice {
    // 配置的是需要被代理的类、方法、抛出异常的名称
    private Object instance;
    private Method method;

    private String throwName;

    public MyAdvice(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }
}
