package com.yjj.study.singleton.lazy;

import com.yjj.study.singleton.service.IUser;

/**
 * 加上同步锁的饿汉式单例
 * 优点：懒加载、没有线程安全问题
 * 缺点：一个线程进来时其他线程会变成等待状态，影响性能
 */
public class SimpleSynchrnoizeUser implements IUser {

    private static SimpleSynchrnoizeUser user;

    private SimpleSynchrnoizeUser(){

    }

    public synchronized static IUser getUser(){
       if(user == null){
           user = new SimpleSynchrnoizeUser();
       }
        return user;
    }

}
