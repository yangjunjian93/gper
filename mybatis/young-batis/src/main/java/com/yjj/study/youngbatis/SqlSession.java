package com.yjj.study.youngbatis;

/**
* 用户操作API
*/
public class SqlSession {

    private Configuration configuration;

    public SqlSession() {

        configuration = new Configuration();
    }

    /**
     * 根据最少知道原则，提供给用户操作的API不需要知道怎么获取的，暂时改为从Configuration中获取
     */
    public <T> T getMapper(Class<T> clazz){
        return configuration.getMapper(clazz);
    }

} 