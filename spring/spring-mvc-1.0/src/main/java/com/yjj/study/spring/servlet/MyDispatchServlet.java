package com.yjj.study.spring.servlet;

import com.yjj.study.spring.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class MyDispatchServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> iocMap = new HashMap<String, Object>();

    private Map<String, Method> handlerMapping = new HashMap<String, Method>();


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


            Object obj = method.invoke(iocMap.get(beanName), params);
            if (obj != null) {
                resp.getWriter().write(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // =========== init ===========
        // 1. 加载配置文件,这里是去web.xml中拿配置的init参数
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        // 2. 扫描包配置
        doScanner(contextConfig.getProperty("scanPackage"));

        // =========== ioc ===========

        // 3. 初始化ioc容器，实例化并加入到IOC容器中
        doInstance();

        // =========== di ===========

        // 4. 依赖注入
        doAutowired();

        // =========== mvc ===========

        // 5. 配置handlerMapping
        doInitHandlerMapping();

        System.out.println("My Spring MVC start success!");

    }

    private void doLoadConfig(String contextConfigLocation) {
        // 根据web.xml配置的init参数拿到配置文件地址读取成流，然后Properties直接读取流拿到配置
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void doInitHandlerMapping() {

        if (iocMap.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {

            Class<?> clazz = entry.getValue().getClass();
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
                System.out.println("HandlerMapping Put " + url + "Value " + method);
            }
        }
    }

    private void doAutowired() {
        if (iocMap.isEmpty()) {
            return;
        }
        // 直接拿到所有的ioc的类，去循环遍历里面所有的字段是否需要注入
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {

            Class<?> clazz = entry.getValue().getClass();
            for (Field field : clazz.getDeclaredFields()) {
                // 不需要自动注入即跳出当前循环
                if (!field.isAnnotationPresent(MyAutowired.class)) {
                    continue;
                }
                // 要注入的，就开始注入
                MyAutowired annotation = field.getAnnotation(MyAutowired.class);
                Class<?> type = field.getType();
                String autoWiredName = "".equals(annotation.value().trim()) ? toFirstLowerCaseStr(type.getSimpleName()) : annotation.value();
                try {
                    // 设置暴力访问私有属性！
                    field.setAccessible(true);
                    field.set(entry.getValue(), iocMap.get(autoWiredName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doInstance() {

        if (classNames.isEmpty()) {
            return;
        }

        try {
            // 直接遍历所有classname，去判断哪些要做ioc
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                // 判断有没有注解先
                if (clazz.isAnnotationPresent(MyController.class)) {
                    // 拿到注解，如果注解有配置别名就使用别名，没有则使用类名首字母小写
                    MyController annotation = clazz.getAnnotation(MyController.class);
                    Object obj = clazz.newInstance();
                    String simpleClassName = "".equals(annotation.value()) ? toFirstLowerCaseStr(clazz.getSimpleName()) : annotation.value();
                    iocMap.put(simpleClassName, obj);
                } else if (clazz.isAnnotationPresent(MyService.class)) {
                    // 拿到注解，如果注解有配置别名就使用别名，没有则使用类名首字母小写
                    MyService annotation = clazz.getAnnotation(MyService.class);
                    Object obj = clazz.newInstance();
                    String simpleClassName = "".equals(annotation.value()) ? toFirstLowerCaseStr(clazz.getSimpleName()) : annotation.value();
                    iocMap.put(simpleClassName, obj);

                    // 再判断是否是接口，如果是，判断该接口有没有已经保存的实现类
                    for (Class<?> i : clazz.getInterfaces()) {
                        // 如果该接口有了一个默认的实现类在ioc中，则抛出异常！
                        if (iocMap.containsKey(i.getName())) {
                            throw new Exception("The " + i.getSimpleName() + " is exists!!");
                        }
                        // 其实这里相当于存了两份！
                        // 一份是<类名，类>
                        // 一份是<类实现接口名，类>
                        iocMap.put(toFirstLowerCaseStr(i.getSimpleName()), obj);
                    }
                } else {
                    continue;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
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

    private void doScanner(String scanPackage) {
        // 拿到配置文件要扫描的包
        // 根据包名拼接的相对路径拿到该目录，即可遍历该目录下所有文件
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            // 如果是目录，则拼接出路径来递归调用
            if (file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            }

            // 如果不是class文件则不操作
            if (!file.getName().endsWith(".class")) {
                continue;
            }
            // 到这里就全部是各种class了，把传入的相对路劲加上classname拼接上去
            // 到时候就可以通过Class.forName()拿到字节码对象
            classNames.add(scanPackage + "." + file.getName().replace(".class", ""));
        }

    }
}
