package com.yjj.study.singleton.lazy;

import com.yjj.study.singleton.service.IUser;

/**
 * 双重同步锁饿汉式
 * 优点：懒加载，线程能进来
 */
public class DoubleSynchrnoizeUser implements IUser{

    private static DoubleSynchrnoizeUser user;
    private DoubleSynchrnoizeUser(){}

    public static IUser getUser(){

        if(user == null){

            synchronized (DoubleSynchrnoizeUser.class){
                if(user == null){
                    user = new DoubleSynchrnoizeUser();
                }
            }

        }
        return user;
    }

}
