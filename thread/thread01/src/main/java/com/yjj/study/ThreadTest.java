package com.yjj.study;

public class ThreadTest {

    private static int count = 0;

    private static void incr(){

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        count++;

    }

    public static void main(String[] args) {

        for (int i = 0; i < 2000; i++) {

            new Thread(() -> ThreadTest.incr()).start();

        }

        System.out.println(count);

    }

}
