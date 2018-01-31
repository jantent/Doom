package concurrent.chapter4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 模拟投票，最终的选票是所有数据的简单求和
 */
public class AtomicReferenceFieldUpdaterDemo {
    public static class Cadidate{
        int id;
        volatile int score;
    }

    public static final AtomicIntegerFieldUpdater<Cadidate> scoreUpdate = AtomicIntegerFieldUpdater.newUpdater(Cadidate.class,"score");
    public static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String args[]){
        final Cadidate stu = new Cadidate();
        Thread[] t = new Thread[10000];
        for (int i=0;i<10000;i++){
            t[i] = new Thread(){
                @Override
                public void run() {
                    super.run();
                    if (Math.random()>0.4){
                        allScore.incrementAndGet();
                        scoreUpdate.incrementAndGet(stu);
                    }
                }
            };
            t[i].start();
        }
        System.out.println("score="+stu.score);
        System.out.println("allScore="+allScore);
    }
}
