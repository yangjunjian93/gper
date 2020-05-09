package com.yjj.study.chain;

public class MobileValidate extends LoginChain{

    public void login(Member member) {
        if(member.getMobile() == null){
            System.out.println("手机号不能为空！");
            return;
        }
        System.out.println("用户名手机校验通过！");
        if(next != null){
            next.login(member);
        }
    }
}
