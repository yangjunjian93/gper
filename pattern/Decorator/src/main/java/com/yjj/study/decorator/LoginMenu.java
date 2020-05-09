package com.yjj.study.decorator;

public class LoginMenu extends MenuDecorator{


    public LoginMenu(Menu menu) {
        super(menu);
    }

    @Override
    String getMenu() {

        return super.getMenu() + "、作业、题库、成长墙";

    }
}
