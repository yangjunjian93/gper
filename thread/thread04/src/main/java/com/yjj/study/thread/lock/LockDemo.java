package com.yjj.study.thread.lock;

/**
 * 死锁示例，这里演示的情况是两个对象互相转帐时都去申请了两个对象的锁，
 * 两个线程分别持有一个锁然后去申请另一个锁引起的死锁
 */
public class LockDemo implements Runnable {

    private Account fromAccount;
    private Account toAccount;
    private int amount;

    public LockDemo(Account fromAccount, Account toAccount, int amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public static void main(String[] args) {

        Account fromAccount = new Account("杨君健", 20000);
        Account toAccount = new Account("鲍康林", 10000);

        Thread from = new Thread(new LockDemo(fromAccount, toAccount,10));
        Thread to = new Thread(new LockDemo(toAccount, fromAccount,20));
        from.start();
        to.start();

    }


    @Override
    public void run() {

        while (true) {

            synchronized (fromAccount) {

                synchronized (toAccount) {

                    if (toAccount.getMoney() > amount) {

                        fromAccount.credit(amount);
                        toAccount.debit(amount);
                    }

                }

            }
            System.out.println("杨君健收到转账 " + amount + " ,目前余额" + fromAccount.getMoney());
            System.out.println("鲍康林转账 " + amount + " ,目前余额" + toAccount.getMoney());
        }

    }
}
