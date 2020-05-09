package com.yjj.study.singleton.registry;

import com.yjj.study.singleton.service.IUser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册式单例
 * 优点：懒加载
 * 缺点：线程不安全！
 */
public class RegistryUser implements IUser {

    private static ConcurrentHashMap<String,RegistryUser> map = new ConcurrentHashMap<>();

    private RegistryUser (){

    }

    public static IUser getUser(){
        IUser user;
        if(!map.contains("user")){

            user = new RegistryUser();
            return user;

        }
        map.put("user", new RegistryUser());
        return map.get("user");

    }


}
