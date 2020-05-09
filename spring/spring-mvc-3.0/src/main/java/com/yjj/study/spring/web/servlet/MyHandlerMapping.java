package com.yjj.study.spring.web.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class MyHandlerMapping {

    private Pattern urlPattern;

    private Method method;

    private Object instance;

    public MyHandlerMapping(Pattern urlPattern, Object instance, Method method) {
        this.urlPattern = urlPattern;
        this.method = method;
        this.instance = instance;
    }

    public Pattern getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(Pattern urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }
}
