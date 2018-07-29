package concurrent.chapter1;

public class ManyThread {
    int count = 0;

    public synchronized void autoIncrement() {
        count++;
    }

    public static void main(String args[]) {
        ManyThread manyThread = new ManyThread();
        Runnable runnable = new MyRunnable2(manyThread);
        new Thread(runnable, "a").start();
        new Thread(runnable, "b").start();
        new Thread(runnable, "c").start();
        new Thread(runnable, "d").start();
    }


}

class MyRunnable2 implements Runnable {

    private  ManyThread manyThread;

    public MyRunnable2(ManyThread manyThread) {
        this.manyThread = manyThread;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            manyThread.autoIncrement();
            System.out.println(Thread.currentThread().getName() + " 执行中 " + "count:" + manyThread.count);
        }
    }


}
