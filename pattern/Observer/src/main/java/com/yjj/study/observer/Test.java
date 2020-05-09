package com.yjj.study.observer;

import com.google.common.eventbus.EventBus;

public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        Teacher teacher = new Teacher("Tom");
        GPer gper = new GPer();
        eventBus.register(teacher);
        eventBus.post(gper.createQuestion("张三", "设计模式为什么这么难"));
    }
}
