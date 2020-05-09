package com.yjj.study.decorator;

public class MenuDecorator extends Menu{

    private Menu menu;

    public MenuDecorator(Menu menu){
        this.menu = menu;
    }

    String getMenu() {
        return menu.getMenu();
    }
}
