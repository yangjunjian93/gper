package com.yjj.study.chain;

public class UserInfoValidate extends LoginChain{

    public void login(Member member) {
        if(member.getUsername() == null || member.getPassword() == null){
            System.out.println("用户名或密码不能为空！");
            return;
        }
        System.out.println("用户名密码校验通过！");
        if(next != null){
            next.login(member);
        }
    }
}
