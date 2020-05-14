package com.yjj.study.youngbatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
* mapper接口代理对象
*/
public class MapperProxy implements InvocationHandler {

    private Configuration configuration;
    private Executor executor;

    public MapperProxy(Configuration configuration) {
        this.configuration = configuration;
        this.executor = new Executor(this.configuration);
    }

    /**
     * 这里要知道到底调什么SQL，需要读取配置文件，所以需要配置中心
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ResourceBundle properties = configuration.getProperties();

        String sqlName = method.getDeclaringClass().getName() + "." + method.getName();
        String[] split = properties.getString(sqlName).split("--");
        String sql = split[0];
        String resultType = split[1].trim();

        return executor.query(sql, args, resultType);
    }

} 