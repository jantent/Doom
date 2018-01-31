package concurrent.chapter4;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计算所有线程使用某个方法的总数,不使用锁
 */
public class ThreadCountNumber {

    private static AtomicInteger counter = new AtomicInteger();

    public static void main(String args[]) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                threadMethod();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(3);
        System.out.println(counter.get());
    }

    private static void threadMethod() {
        counter.incrementAndGet();
    }


}
