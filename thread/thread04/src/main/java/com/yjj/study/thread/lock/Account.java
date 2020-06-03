package com.yjj.study.thread.lock;

public class Account {

    private String name;
    private int money;

    public Account(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public void debit(int amount){ //更新转出方的余额
        this.money-=amount;
    }

    public void credit(int amount){ //更新转入方的余额
        this.money+=amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
