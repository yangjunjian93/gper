package com.yjj.study.decorator;

public class Test {

    public static void main(String[] args) {

        DefaultMenu defaultMenu = new DefaultMenu();
        System.out.println(defaultMenu.getMenu());


        LoginMenu loginMenu = new LoginMenu(defaultMenu);
        System.out.println(loginMenu.getMenu());

    }

}
