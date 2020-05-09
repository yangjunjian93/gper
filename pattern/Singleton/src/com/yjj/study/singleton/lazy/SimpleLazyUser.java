package com.yjj.study.singleton.lazy;

import com.yjj.study.singleton.service.IUser;

/**
 * 简单懒汉式
 * 优点：需要时才加载类，不会提前占用系统资源
 * 缺点：存在明显的线程安全问题
 * 有可能两个线程进来获取到的对象不一致，
 * 或者生成两次对象，后进来的线程把先进来的线程new的对象给覆盖，导致看上去生成对一个对象但实际生成了两个对象的情况
 */
public class SimpleLazyUser implements IUser {

    private SimpleLazyUser() {
    }

    private static SimpleLazyUser user;

    public static IUser getUser(){
        if(user == null){
            user = new SimpleLazyUser();
        }
        return user;
    }

}
