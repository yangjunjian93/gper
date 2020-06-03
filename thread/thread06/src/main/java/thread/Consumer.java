package thread;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

    private Queue<String> queue;
    private int max;

    private int num = 1;

    private Lock lock;
    private Condition condition;

    public Consumer(Queue<String> queue, int max, Lock lock, Condition condition) {
        this.queue = queue;
        this.max = max;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {

        while (true) {
            lock.lock();
            if (queue.isEmpty()) {
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

            System.out.println("收到队列消息：" + queue.remove());
            condition.signal();
            lock.unlock();
        }
    }


}
