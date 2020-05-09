package com.yjj.study.model;

public abstract class SystemFile {

    public String name;

    public SystemFile(String name) {
        this.name = name;
    }

    public void show(){
        System.out.println(this.name);
    }

    public String getName() {
        return this.name;
    }
}
