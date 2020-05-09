package com.yjj.study.visitor.employee;

import com.yjj.study.visitor.visitorimpl.IVisitor;

import java.util.Random;

public class Programmer extends Employee{


    public Programmer(String name) {
        super(name);
    }

    public void accept(IVisitor visitor) {
        visitor.visitor(this);
    }

    public int getCodeLine(){

        return new Random().nextInt(100000);

    }

}
