package com.yjj.study.chain;

public class AddressValidate extends LoginChain{

    public void login(Member member) {
        if(member.getAddress() == null){
            System.out.println("用户地址不能为空！");
            return;
        }
        System.out.println("用户地址校验通过！");
        if(next != null){
            next.login(member);
        }
    }
}
