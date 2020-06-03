package com.yjj.study.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {

    private static int num = 0;

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        for (int i = 0; i < 5000; i++) {
            Thread thread = new Thread(() -> {
                while (true) {
                    lock.lock();
                    num++;
                    try {
                        Thread.sleep(5000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            });
            thread.start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(num);



    }

}
