package com.yjj.study.thread;


import java.util.concurrent.CountDownLatch;

public class CountDownDemo implements Runnable{

    // 计数器，每调用一次会state-1，当state=0的时候唤醒所有线程！
    static CountDownLatch countDownLatch = new CountDownLatch(3);

    @Override
    public void run() {

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("全部停止！");
    }

    public static void main(String[] args) {


        for (int i = 0; i < 5; i++) {
            new Thread(new CountDownDemo()).start();

        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
        countDownLatch.countDown();
        countDownLatch.countDown();
    }

}
