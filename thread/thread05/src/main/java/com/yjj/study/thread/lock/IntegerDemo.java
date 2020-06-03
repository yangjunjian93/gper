package com.yjj.study.thread.lock;

public class IntegerDemo {

    static int a = 1;
    static int b = 2;


    public static void main(String[] args) {

        a = b = 3;
        System.out.println(a);
        System.out.println(b);

    }

}
