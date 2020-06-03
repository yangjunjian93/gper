package com.yjj.study.thread.join;

public class JoinDemo {

    static int num = 5;

    public static void main(String[] args) {

        Thread thread = new Thread(() ->{
            num = 10;
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);

    }


}
