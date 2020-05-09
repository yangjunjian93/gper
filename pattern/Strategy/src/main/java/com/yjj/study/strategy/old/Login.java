package com.yjj.study.strategy.old;

public class Login {


    public static void Login(LoginDTO loginDTO){

        if("mobile".equals(loginDTO.getLoginType())){
            System.out.println("用手机号登了, 手机号为: " + loginDTO.getMobile());
        } else if("qq".equals(loginDTO.getLoginType())){
            System.out.println("用QQ号登陆了, QQ号为 " + loginDTO.getQqNum());
        }else if("wechat".equals(loginDTO.getLoginType())){
            System.out.println("用微信号登陆了, 微信号为 " + loginDTO.getWechatNum());
        }else {
            System.out.println("用帐号密码登陆了, 用户名为 " + loginDTO.getUsername());
        }
    }


}
