package com.yjj.study.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Cloneable, Serializable {

    private String name;
    private Integer age;
    private int sex;

    private List<String> aihao;

    private User user;


    @Override
    protected Object clone() throws CloneNotSupportedException {
       return super.clone();
    }

    public User deepClone() throws Exception{

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (User)ois.readObject();

    }

    public User(String name, Integer age, int sex, List<String> aihao) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.aihao = aihao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<String> getAihao() {
        return aihao;
    }

    public void setAihao(List<String> aihao) {
        this.aihao = aihao;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", aihao=" + aihao +
                '}';
    }

    public User() {
    }

    public void setUser(User user) {
        this.user = user;
    }
}
