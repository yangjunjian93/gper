package com.yjj.study.strategy.old;

public class Test {

    public static void main(String[] args) {

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLoginType("mobile");
        loginDTO.setMobile("18607957182");
        Login.Login(loginDTO);

    }

}
