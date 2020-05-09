package com.yjj.study.singleton.hangry;

import com.yjj.study.singleton.service.IUser;

/**
 * 饿汉式单例
 * 优点：加载快，性能高，没有线程安全问题
 * 缺点：启动项目即加载该类，不使用也会占用资源，而且类一多起来会影响项目启动速度，大量占用系统资源
 */
public class SimpleUser implements IUser {

    private static final SimpleUser user = new SimpleUser();

    private SimpleUser(){

    }

    public static SimpleUser getUser(){
        return user;
    }


}
