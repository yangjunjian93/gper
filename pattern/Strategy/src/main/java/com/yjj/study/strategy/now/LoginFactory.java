package com.yjj.study.strategy.now;

import java.util.HashMap;
import java.util.Map;

public class LoginFactory {

    private static Map<String, BaseLogin> LOGIN_MAP = new HashMap<String, BaseLogin>(){{
        put("QQ", new QQLogin());
        put("Wechat", new WechatLogin());
        put("mobile", new MobileLogin());
    }};

    public static void Login(String loginType){

        BaseLogin baseLogin = LOGIN_MAP.get(loginType);
        baseLogin.login();


    }

}
