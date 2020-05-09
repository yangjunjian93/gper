package com.yjj.study.visitor.employee;

import com.yjj.study.visitor.visitorimpl.IVisitor;

import java.util.Random;

public class Leader extends Employee{


    public Leader(String name) {
        super(name);
    }

    public void accept(IVisitor visitor) {
        visitor.visitor(this);
    }

    public int getProductNum(){

        return new Random().nextInt(20);

    }

}
