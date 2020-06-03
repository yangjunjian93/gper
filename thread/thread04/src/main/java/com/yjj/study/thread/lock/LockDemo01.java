package com.yjj.study.thread.lock;

/**
 * 这个示例演示的是同时申请锁，通过Registr里面来判断是否拿到锁
 * 本质上就是换了个锁，之前是去的转入转出对象的锁，现在变成了取Registry的锁
 * 因为Registry里的方法都是同步的，利用同步插入集合和同步移除集合来实现
 *
 * 主要是为了掩饰批量申请锁的场景
 */
public class LockDemo01 implements Runnable {

    private Account fromAccount;
    private Account toAccount;
    private int amount;
    private Registry registry;

    public LockDemo01(Account fromAccount, Account toAccount, int amount, Registry registry) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.registry = registry;
    }

    public static void main(String[] args) {
        Registry registry = new Registry();
        Account fromAccount = new Account("杨君健", 20000);
        Account toAccount = new Account("鲍康林", 10000);

        Thread from = new Thread(new LockDemo01(fromAccount, toAccount, 10, registry));
        Thread to = new Thread(new LockDemo01(toAccount, fromAccount, 20, registry));
        from.start();
        to.start();

    }


    @Override
    public void run() {

        while (true) {
            if (registry.get(fromAccount, toAccount)) {
                try {
                    synchronized (fromAccount) {
                        synchronized (toAccount) {
                            if (toAccount.getMoney() > amount) {
                                fromAccount.credit(amount);
                                toAccount.debit(amount);
                            }
                        }
                    }
                    System.out.println(fromAccount.getName() + " -> " + fromAccount.getMoney());
                    System.out.println(toAccount.getName() + " -> " + toAccount.getMoney());

                } finally {
                    registry.free(fromAccount, toAccount);
                }
            }

        }

    }
}
