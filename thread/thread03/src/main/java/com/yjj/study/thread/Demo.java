package com.yjj.study.thread;

import java.util.LinkedList;
import java.util.Queue;

public class Demo {

    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        int max = 5;

        Producer producer = new Producer(queue, max);
        Consumer consumer = new Consumer(queue, max);

        new Thread(producer).start();
        new Thread(consumer).start();



    }

}
