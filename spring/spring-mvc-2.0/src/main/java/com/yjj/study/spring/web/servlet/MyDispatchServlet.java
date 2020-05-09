package com.yjj.study.spring.web.servlet;

import com.yjj.study.spring.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import com.yjj.study.spring.context.MyApplicationContext;

public class MyDispatchServlet extends HttpServlet {

    private Map<String, Method> handlerMapping = new HashMap<String, Method>();

    private MyApplicationContext applicationContext;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doDispatch(req, resp);

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 拿到请求连接，去掉前面的域名端口项目名什么的，拿到相对请求路径
            String url = req.getRequestURI();
            String contextPath = req.getContextPath();
            url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

            Method method = handlerMapping.get(url);
            if (method == null) {
                resp.getWriter().write("404 Not Found!");
                return;
            }

            Class<?> clazz = method.getDeclaringClass();
            MyController annotation = clazz.getAnnotation(MyController.class);
            String beanName = "".equals(annotation.value()) ? toFirstLowerCaseStr(clazz.getSimpleName()) :  annotation.value();

            Class<?>[] parameterTypes = method.getParameterTypes();
            Object[] params = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                if(parameterTypes[i] == HttpServletRequest.class){
                    params[i] = req;
                } else if(parameterTypes[i] == HttpServletResponse.class){
                    params[i] = resp;
                } else {
                    // 因为一个参数可以有多个注解！所以这个二维数组第一纬对应的是参数索引，第二纬对应的是注解索引
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    for (Annotation[] parameterAnnotation : parameterAnnotations) {
                        for (Annotation realAnnotation : parameterAnnotation) {
                            if(realAnnotation instanceof MyRequestParam){
                                String paramName = ((MyRequestParam) realAnnotation).value();
                                Object param = req.getParameter(paramName);
                                params[i] = param;
                            }
                        }
                    }
                }
            }


            Object obj = method.invoke(applicationContext.getBean(beanName), params);
            if (obj != null) {
                resp.getWriter().write(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1、创建上下文
        applicationContext = new MyApplicationContext(config.getInitParameter("contextConfigLocation"));
        // =========== mvc ===========

        // 5. 配置handlerMapping
        doInitHandlerMapping();

        System.out.println("My Spring MVC start success!");

    }

    private void doInitHandlerMapping() {

        if (applicationContext.getBeanDefinitionCount() == 0) {
            return;
        }

        for (String beanName : applicationContext.getBeanDefinitionNames()) {

            Class<?> clazz = applicationContext.getBean(beanName).getClass();
            // 判断是否有controller注解，如果没有，那肯定没有requestMapping注解
            if (!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            String baseUrl = "/";
            // 判断controller上有没有requestMapping注解，有就去拿值
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                baseUrl = baseUrl + clazz.getAnnotation(MyRequestMapping.class).value();
            }
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                String url = (baseUrl + "/" + method.getAnnotation(MyRequestMapping.class).value()).replaceAll("/+", "/");
                handlerMapping.put(url, method);
                System.out.println("HandlerMapping Put " + url + ", Value " + method);
            }
        }
    }

    // 转换为首字母小写的类名
    private String toFirstLowerCaseStr(String simpleClassName) {

        // 根据ASCII码表，大写字母转小写只需要ASCII值加32
        char[] chars = simpleClassName.toCharArray();
        if (chars[0] >= 65 && chars[0] <= 90) {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }

}
