package com.yjj.study.spring.web.servlet;

import com.yjj.study.spring.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yjj.study.spring.context.MyApplicationContext;

public class MyDispatchServlet extends HttpServlet {

    private List<MyHandlerMapping> handlerMappings = new ArrayList<MyHandlerMapping>();

    private Map<MyHandlerMapping, MyHandlerAdapter> handlerAdapters = new HashMap<MyHandlerMapping, MyHandlerAdapter>();

    private List<MyViewResolver> viewResolvers = new ArrayList<>();

    private MyApplicationContext applicationContext;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        doDispatch(req, resp);

    }

    // 真正的处理请求
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 先去找到handlerMapping真正的处理方法
            MyHandlerMapping handler = getHandler(req);
            if (handler == null) {
                resp.getWriter().write("404 Not Found!");
                return;
            }
            // 拿到对应的handlerAdapter
            MyHandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            // 使用handlerAdapter去拿到适配的参数并执行方法
            MyModelAndView mv = handlerAdapter.handler(req, resp, handler);
            // 就把ModelAndView变成一个ViewResolver
            processDispatchResult(req,resp,mv);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 返回请求结果
    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, MyModelAndView mv) throws Exception {
        if(null == mv){return;}
        if(this.viewResolvers.isEmpty()){return;}

        for (MyViewResolver viewResolver : viewResolvers) {

            String viewName = mv.getViewName();
            MyView view = viewResolver.resolveViewName(viewName);
            view.render(mv.getModel(), req, resp);
            return;
        }
    }

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        return handlerAdapters.get(handler);
    }

    private MyHandlerMapping getHandler(HttpServletRequest req) {

        // 拿到请求连接，去掉前面的域名端口项目名什么的，拿到相对请求路径
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        for (MyHandlerMapping handlerMapping : handlerMappings) {

            Pattern urlPattern = handlerMapping.getUrlPattern();
            Matcher matcher = urlPattern.matcher(url);
            if(matcher.find()){
                return handlerMapping;
            }

        }
        return null;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 1、创建上下文
        applicationContext = new MyApplicationContext(config.getInitParameter("contextConfigLocation"));
        // =========== mvc ===========

        initStrategies(applicationContext);

        System.out.println("My Spring MVC start success!");

    }

    // 初始化九大组件
    private void initStrategies(MyApplicationContext context) {

//        //多文件上传的组件
//        initMultipartResolver(context);
//        //初始化本地语言环境
//        initLocaleResolver(context);
//        //初始化模板处理器
//        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
//        //初始化异常拦截器
//        initHandlerExceptionResolvers(context);
//        //初始化视图预处理器
//        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
//        //FlashMap管理器
//        initFlashMapManager(context);

    }

    private void initViewResolvers(MyApplicationContext context) {

        // 拿到配置文件信息，获取模版存放在哪个文件夹下
        Properties properties = context.getConfig();
        String templateRoot = properties.getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        // 遍历这个文件夹，拿到所有模版，转换为视图解析器保存
        for (File file : templateRootDir.listFiles()) {
            viewResolvers.add(new MyViewResolver(templateRoot));
        }

    }

    private void initHandlerAdapters(MyApplicationContext context) {
        // 先一对一绑定，因为HandlerMapping里存的是url和方法啥的
        // 而HandlerAdapter则是可以拿来处理方法的动态参数
        for (MyHandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapters.put(handlerMapping, new MyHandlerAdapter());
        }

    }

    private void initHandlerMappings(MyApplicationContext context) {

        if (applicationContext.getBeanDefinitionCount() == 0) {
            return;
        }

        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object instance = applicationContext.getBean(beanName);
            Class<?> clazz = instance.getClass();

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

                Pattern urlPattern = Pattern.compile(url);

                handlerMappings.add(new MyHandlerMapping(urlPattern, instance, method));

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
