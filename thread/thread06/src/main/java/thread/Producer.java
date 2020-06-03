package thread;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {

    private Queue<String> queue;
    private int max;

    private int num = 1;

    private Lock lock;
    private Condition condition;


    public Producer(Queue<String> queue, int max, Lock lock, Condition condition) {
        this.queue = queue;
        this.max = max;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {

        while (true) {
            lock.lock();
            if (queue.size() == max) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            num ++;
            queue.add("发送消息: " + num);
            System.out.println("发送消息: " + num);
            condition.signal();
            lock.unlock();
        }
    }


}
