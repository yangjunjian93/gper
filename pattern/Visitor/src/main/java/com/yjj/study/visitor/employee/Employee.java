package com.yjj.study.visitor.employee;

import com.yjj.study.visitor.visitorimpl.IVisitor;

import java.util.Random;

/**
 * 被访问者
 */
public abstract class Employee {

    public String name;
    public int kpi;

    public Employee(String name) {
        this.name = name;
        this.kpi = new Random().nextInt(10);
    }

    // 接受访问
    public abstract void accept(IVisitor visitor);
}
