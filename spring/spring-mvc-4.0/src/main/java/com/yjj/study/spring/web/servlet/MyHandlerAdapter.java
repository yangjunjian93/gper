package com.yjj.study.spring.web.servlet;

import com.yjj.study.spring.annotation.MyRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MyHandlerAdapter {

    public MyModelAndView handler(HttpServletRequest req, HttpServletResponse resp, MyHandlerMapping handlerMapping) throws Exception {

        Method method = handlerMapping.getMethod();
        Class<?> clazz = handlerMapping.getInstance().getClass();

        // 创建map保存形参名称和对应的索引
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();

        // 先处理有自定义注解的
        // 因为一个参数可以有多个注解！所以这个二维数组第一纬对应的是参数索引，第二纬对应的是注解索引
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation realAnnotation : parameterAnnotations[i]) {
                if (realAnnotation instanceof MyRequestParam) {
                    String paramName = ((MyRequestParam) realAnnotation).value();
                    if(null != paramName && !"".equals(paramName.trim())){
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }

        }

        // 再处理没有自定义注解的,这里主要指的是request和response
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class){
                paramIndexMapping.put(parameterType.getName(), i);
            }
        }

        // 有了形参名称和对应位置，就可以去拿到参数列表进行匹配
        Map<String, String[]> parameterMap = req.getParameterMap();
        // 创建一个执行方法的数据
        Object[] params = new Object[parameterTypes.length];
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            // 这里是去拿到请求参数的值，因为可能有多个，处理成数组逗号拼接
            String value = String.join(",", Arrays.asList(entry.getValue()));
            if(!paramIndexMapping.containsKey(entry.getKey())){
                continue;
            }
            Integer index = paramIndexMapping.get(entry.getKey());
            params[index] = value;
        }
        // 如果有这两个对象则进行处理,因为这两个对象并不会在req.getParameterMap()里面
        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())){
            int index = paramIndexMapping.get(HttpServletRequest.class.getName());
            params[index] = req;
        }

        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())){
            int index = paramIndexMapping.get(HttpServletResponse.class.getName());
            params[index] = resp;
        }

        Object result = method.invoke(handlerMapping.getInstance(), params);

        if(result == null || result instanceof Void){
            return null;
        }
        if(method.getReturnType() == MyModelAndView.class){
            MyModelAndView model = (MyModelAndView) result;
            return model;
        }
        return null;
    }

    private Object castStringValue(String value, Class<?> paramType) {
        if(String.class == paramType){
            return value;
        }else if(Integer.class == paramType){
            return Integer.valueOf(value);
        }else if(Double.class == paramType){
            return Double.valueOf(value);
        }else {
            if(value != null){
                return value;
            }
            return null;
        }

    }

}
