package com.yjj.study.observer;

import com.google.common.eventbus.Subscribe;

public class Teacher {

    String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Subscribe
    public void getQuestion(Question question){
        System.out.println(name + "老师收到了一个来自于 " + question.getUserName() + "的提问 \n" +

        "提问内容：" + question.getContent());
    }
}
