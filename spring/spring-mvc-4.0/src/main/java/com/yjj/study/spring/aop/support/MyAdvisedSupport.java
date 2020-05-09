package com.yjj.study.spring.aop.support;

import com.yjj.study.spring.aop.aspect.MyAdvice;
import com.yjj.study.spring.aop.config.MyAopConfig;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyAdvisedSupport {

    // Aop配置文件信息
    private MyAopConfig aopConfig;
    // 被代理的类
    private Object target;
    // 被代理的字节码对象
    private Class<?> targetClass;

    // 匹配代理类的正则
    private Pattern pointCutClassPattern;
    // 在设置被代理类的时候初始化需要被代理的方法和需要哪些通知，先缓存起来
    private Map<Method, Map<String, MyAdvice>> methodCache;

    // 构造方法拿到Aop配置的相关信息
    public MyAdvisedSupport(MyAopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }


    // 判断是否需要生成代理类,直接正则匹配目标类路径
    public boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    // 初始化相关配置
    private void parse() {

        //把Spring的Excpress变成Java能够识别的正则表达式
        String pointCut = aopConfig.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");

        //保存专门匹配Class的正则
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

        // 初始化缓存
        methodCache = new HashMap<>();

        //保存专门匹配方法的正则
        Pattern pointCutPattern = Pattern.compile(pointCut);
        // 拿到代理通知类, 即切点
        try {
            Class<?> aspectClazz = Class.forName(aopConfig.getAspectClass());
            // 拿到配置的所有通知
            Method[] methods = aspectClazz.getMethods();
            // 先保存所有通知
            Map<String, Method> aspectMethodMap = new HashMap<>();
            for (Method method : methods) {
                aspectMethodMap.put(method.getName(), method);
            }

            // 循环被代理类所有方法，去匹配哪些要代理的，封装出方法与对应通知的Map
            for (Method method : targetClass.getMethods()) {

                String methodString = method.toString();
                Matcher matcher = pointCutPattern.matcher(methodString);
                // 如果匹配到了，则开始代理
                if(matcher.matches()){
                    // 创建该方法有哪些通知,并且保存起来
                    Map<String,MyAdvice> advices = new HashMap<String, MyAdvice>();

                    if(!(null == aopConfig.getAspectBefore() || "".equals(aopConfig.getAspectBefore()))){
                        advices.put("before",new MyAdvice(aspectClazz.newInstance(),aspectMethodMap.get(aopConfig.getAspectBefore())));
                    }
                    if(!(null == aopConfig.getAspectAfter() || "".equals(aopConfig.getAspectAfter()))){
                        advices.put("after",new MyAdvice(aspectClazz.newInstance(),aspectMethodMap.get(aopConfig.getAspectAfter())));
                    }
                    if(!(null == aopConfig.getAspectAfterThrow() || "".equals(aopConfig.getAspectAfterThrow()))){
                        MyAdvice advice = new MyAdvice(aspectClazz.newInstance(),aspectMethodMap.get(aopConfig.getAspectAfterThrow()));
                        advice.setThrowName(aopConfig.getAspectAfterThrowingName());
                        advices.put("afterThrow",advice);
                    }
                    methodCache.put(method, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, MyAdvice> getAdvice(Method method) throws Exception{
        Map<String, MyAdvice> adviceMap = methodCache.get(method);
        if(adviceMap == null){

            method = targetClass.getMethod(method.getName(), method.getParameterTypes());
            adviceMap = methodCache.get(method);
        }
        return adviceMap;
    }


    public MyAopConfig getAopConfig() {
        return aopConfig;
    }

    public void setAopConfig(MyAopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }


}
