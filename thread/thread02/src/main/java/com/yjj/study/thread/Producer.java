package com.yjj.study.thread;

import java.util.LinkedList;
import java.util.Queue;

public class Producer implements Runnable{

    private Queue<String> queue;
    private int max;

    private int num = 1;

    public Producer(Queue<String> queue, int max) {
        this.queue = queue;
        this.max = max;
    }

    @Override
    public void run() {

        while (true){
            synchronized (queue){
                if(queue.size() == max){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                queue.add("发送消息: " + ++num);
                System.out.println("发送消息: " + ++num);
                queue.notify();
            }
        }


    }
}
