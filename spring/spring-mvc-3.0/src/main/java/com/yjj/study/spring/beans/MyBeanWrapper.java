package com.yjj.study.spring.beans;

public class MyBeanWrapper {

    private Object instance;
    private Class<?> clazz;

    public MyBeanWrapper(Object instance) {
        this.instance = instance;
        this.clazz = instance.getClass();
    }

    public Object getInstance() {
        return instance;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
