package com.yjj.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo implements Runnable{

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(6);

    @Override
    public void run() {

        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " 开始执行拉!");

    }

    public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            new Thread(new CyclicBarrierDemo()).start();
            System.out.println("第" + i + "个线程准备就绪!");
        }



    }


}
