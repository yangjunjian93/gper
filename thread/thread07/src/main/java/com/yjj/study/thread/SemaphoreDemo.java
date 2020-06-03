package com.yjj.study.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable{

    static Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 开始执行拉！");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        semaphore.release();

    }

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            new Thread(new SemaphoreDemo()).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
