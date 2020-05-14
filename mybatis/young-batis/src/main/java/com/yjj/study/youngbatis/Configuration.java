package com.yjj.study.youngbatis;

import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

/**
* 配置中心，读取配置信息
*/
public class Configuration {

    private ResourceBundle properties;

    public Configuration() {
        this.properties = ResourceBundle.getBundle("config");
    }

    public ResourceBundle getProperties() {
        return properties;
    }

    /**
     * 采用动态代理的方式传入
     */
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[] {clazz}, new MapperProxy(this));
    }
} 