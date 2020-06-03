package com.yjj.study.thread;

import java.util.Queue;

public class Consumer implements Runnable{

    private Queue<String> queue;
    private int max;

    private int num = 1;

    public Consumer(Queue<String> queue, int max) {
        this.queue = queue;
        this.max = max;
    }

    @Override
    public void run() {

        while (true){
            synchronized (queue){
                if(queue.isEmpty()){
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

                System.out.println("收到队列消息：" + queue.remove());
                queue.notify();
            }
        }


    }


}
