package thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        int max = 5;

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Producer producer = new Producer(queue, max, lock, condition);
        Consumer consumer = new Consumer(queue, max, lock, condition);

        new Thread(producer).start();
        new Thread(consumer).start();



    }

}
