package net.learning.jvmperformance.concurrent;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Xuesong Mei on 10/9/15.
 */
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        Object obj2 = new Object();

        Thread t1 = new Thread(new SyncThread(obj1, obj2), "t1");
        Thread t2 = new Thread(new SyncThread(obj2, obj1), "t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}

class SyncThread implements Runnable {
    private Object obj1;
    private Object obj2;

    public SyncThread(Object o1, Object o2) {
        this.obj1 = o1;
        this.obj2 = o2;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " acquiring lock on " + obj1);
        synchronized (obj1) {
            System.out.println(name + " acquired lock on " + obj1);
            doSomething();
            System.out.println(name + " acquiring lock on " + obj2);
            synchronized (obj2) {
                System.out.println(name + " acquired lock on " + obj2);
                doSomething();
            }
            System.out.println(name + " released lock on " + obj2);
        }
        System.out.println(name + " released lock on " + obj1);
        System.out.println(name + " finished execution.");
    }

    private void doSomething() {
        try {
            int workTime = ThreadLocalRandom.current().nextInt(900, 1100);
            Thread.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}