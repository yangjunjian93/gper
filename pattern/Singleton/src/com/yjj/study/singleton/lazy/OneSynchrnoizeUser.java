package com.yjj.study.singleton.lazy;

import com.yjj.study.singleton.service.IUser;

/**
 * 单检查
 * 优点：没有线程安全问题
 * 缺点：还是会影响性能
 */
public class OneSynchrnoizeUser implements IUser {

    private static OneSynchrnoizeUser user;

    private OneSynchrnoizeUser() {
    }

    public static IUser getUser() {

        // 当同步锁锁在判断外面时，还是会导致其他线程等待
        // 当同步锁锁在判断里面时，还是有线程安全问题
        if (user == null) {
            synchronized (OneSynchrnoizeUser.class) {
                user = new OneSynchrnoizeUser();
            }
        }

        return user;

    }

}
