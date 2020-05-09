package com.yjj.study.spring.context;

import com.yjj.study.spring.annotation.MyAutowired;
import com.yjj.study.spring.annotation.MyController;
import com.yjj.study.spring.annotation.MyService;
import com.yjj.study.spring.aop.MyJdkDynamicProxy;
import com.yjj.study.spring.aop.config.MyAopConfig;
import com.yjj.study.spring.aop.support.MyAdvisedSupport;
import com.yjj.study.spring.beans.MyBeanWrapper;
import com.yjj.study.spring.beans.config.MyBeanDefinition;
import com.yjj.study.spring.beans.support.MyBeanDifinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

// 自定义的上下文
public class MyApplicationContext {

    private MyBeanDifinitionReader reader;

    private Map<String, MyBeanDefinition> beanDefinitionMap = new HashMap<String, MyBeanDefinition>();

    private Map<String, Object> factoryBeanObjectCache = new HashMap<String, Object>();

    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new HashMap<String, MyBeanWrapper>();


    // 模仿Spring中的使用，一般是传入配置文件地址进来
    public MyApplicationContext(String... configLocations) {
        try {
            // 创建读取配置文件的reader,创建时会自动加载配置文件，扫描包
            reader = new MyBeanDifinitionReader(configLocations[0]);
            // 拿到封装好的配置信息
            List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
            // 放到缓存中
            doRegisterBeanDefinition(beanDefinitions);
            // 开始预处理
            doAutowired();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 预处理，这里还没有真正的注入!
    private void doAutowired() {
        // 因为Spring是懒加载，只有到getBean的时候才开始依赖注入！
        for (Map.Entry<String, MyBeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            getBean(beanName);
        }

    }

    // 注册到本类缓存中，如果要getBean的时候可以直接拿
    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions) throws Exception {

        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            if(clazz.isInterface()){
                continue;
            }
            if (beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The " + beanDefinition.getFactoryBeanName() + "is exists");
            }
            // 再判断是否是接口，如果是，判断该接口有没有已经保存的实现类
            for (Class<?> i : clazz.getInterfaces()) {
                // 如果该接口有了一个默认的实现类在ioc中，则抛出异常！
                if (beanDefinitionMap.containsKey(i.getName())) {
                    throw new Exception("The " + i.getSimpleName() + " is exists!!");
                }
                // 其实这里相当于存了两份！
                // 一份是<类名，类>
                // 一份是<类实现接口名，类>
                beanDefinitionMap.put(toFirstLowerCaseStr(i.getSimpleName()), beanDefinition);
                beanDefinitionMap.put(i.getName(), beanDefinition);
            }
            // 把全类名和默认首字母小写的名称都缓存一边，这样能支持多种查询
            beanDefinitionMap.put(beanDefinition.getBeanClassName(), beanDefinition);
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);

        }

    }

    // bean的实例化，DI是从这里开始的
    public Object getBean(String beanName) {

        // 拿到BeanDefinition
        MyBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        // 根据bean名称和BeanDefinition生成bean，
        // 这里传BeanDefinition主要是为了和Spring保持统一，
        // 在Spring中BeanDefinition远比我们自己的复杂，方法也有很多处理
        Object instance = instantiateBean(beanName, beanDefinition);
        // 根据生成的实例封装BeanWrapper
        MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);
        // 缓存BeanWrapper
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        // 开始依赖注入
        populateBean(beanName, beanDefinition, beanWrapper);
        // 因为依赖注入的对象封装在BeanWrapper中，所以返回他
        return beanWrapper.getInstance();
    }

    // 依赖注入的方法
    private void populateBean(String beanName, MyBeanDefinition beanDefinition, MyBeanWrapper beanWrapper) {
        // 拿到BeanWrapper中的实例进行注入
        Object instance = beanWrapper.getInstance();
        Class<?> clazz = beanWrapper.getClazz();

        //在Spring中@Component
        if(!(clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class))){
            return;
        }

        for (Field field : clazz.getDeclaredFields()) {
            // 不需要自动注入即跳出当前循环
            if (!field.isAnnotationPresent(MyAutowired.class)) {
                continue;
            }
            // 要注入的，就开始注入
            MyAutowired annotation = field.getAnnotation(MyAutowired.class);
            Class<?> type = field.getType();
            String autoWiredName = "".equals(annotation.value().trim()) ? toFirstLowerCaseStr(type.getSimpleName()) : annotation.value();
            if(!factoryBeanInstanceCache.containsKey(autoWiredName)){
                continue;
            }
            try {
                // 设置暴力访问私有属性！
                field.setAccessible(true);
                field.set(instance, factoryBeanInstanceCache.get(autoWiredName).getInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    // 实例化bean,只有在实例化的时候才知道要不要生成代理类
    private Object instantiateBean(String beanName, MyBeanDefinition beanDefinition) {
        Object instance = null;
        try {
            if(factoryBeanObjectCache.containsKey(beanName)){
                instance = factoryBeanObjectCache.get(beanName);
            } else{
                String beanClassName = beanDefinitionMap.get(beanName).getBeanClassName();
                Class<?> clazz = Class.forName(beanClassName);
                instance = clazz.newInstance();

                // ============== AOP 开始 ==============
                MyAdvisedSupport advisedSupport = instantiateAopConfig(beanDefinition);
                advisedSupport.setTarget(instance);
                advisedSupport.setTargetClass(clazz);
                // 判断是否需要生成代理对象
                if(advisedSupport.pointCutMatch()){
                    instance = new MyJdkDynamicProxy(advisedSupport).getProxy();
                }
                // ============== AOP 结束 ==============
                factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(), instance);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    // 初始化Aop配置
    private MyAdvisedSupport instantiateAopConfig(MyBeanDefinition beanDefinition) {
        Properties config = reader.getConfig();
        MyAopConfig aopConfig = new MyAopConfig();
        aopConfig.setPointCut(config.getProperty("pointCut"));
        aopConfig.setAspectClass(config.getProperty("aspectClass"));
        aopConfig.setAspectBefore(config.getProperty("aspectBefore"));
        aopConfig.setAspectAfter(config.getProperty("aspectAfter"));
        aopConfig.setAspectAfterThrow(config.getProperty("aspectAfterThrow"));
        aopConfig.setAspectAfterThrowingName(config.getProperty("aspectAfterThrowingName"));
        return new MyAdvisedSupport(aopConfig);

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

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[beanDefinitionMap.size()]);
    }

    public Properties getConfig(){
        return this.reader.getConfig();
    }

}
