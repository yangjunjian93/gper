package com.yjj.study.spring.beans.support;

import com.yjj.study.spring.annotation.MyController;
import com.yjj.study.spring.annotation.MyService;
import com.yjj.study.spring.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

// 解析配置文件, 生成自定义的BeanDefinition
public class MyBeanDifinitionReader {

    private Properties contextConfig = new Properties();

    // 全类名集合
    private List<String> regitryBeanClasses = new ArrayList<String>();


    public MyBeanDifinitionReader(String contextConfigLocation) {

        // 1. 加载配置文件,这里是去web.xml中拿配置的init参数
        doLoadConfig(contextConfigLocation);
        // 2. 扫描包配置
        doScanner(contextConfig.getProperty("scanPackage"));

    }

    // 遍历全类名集合，拿到BeanDefinition对象
    public List<MyBeanDefinition> loadBeanDefinitions(){
        try {
            List<MyBeanDefinition> beanDefinitions = new ArrayList<MyBeanDefinition>();
            for (String className : regitryBeanClasses) {
                Class<?> clazz = Class.forName(className);
                String factoryClassName = toFirstLowerCaseStr(clazz.getSimpleName());
                String beanClassName = clazz.getName();
                beanDefinitions.add(doCreateBeanDefinition(factoryClassName, beanClassName));
            }
            return beanDefinitions;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Properties getConfig(){
        return this.contextConfig;
    }

    private void doLoadConfig(String contextConfigLocation) {
        // 根据web.xml配置的init参数拿到配置文件地址读取成流，然后Properties直接读取流拿到配置
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation.replaceAll("classpath:",""));
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
            regitryBeanClasses.add(scanPackage + "." + file.getName().replace(".class", ""));
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

    private MyBeanDefinition doCreateBeanDefinition(String factoryClassName, String beanClassName){

        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryClassName);
        return beanDefinition;

    }

}
