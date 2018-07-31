package javakeyword;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomVolatile {
    public volatile AtomicInteger i = new AtomicInteger(0);

    public void increase() {
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
        final TestAtomVolatile test = new TestAtomVolatile();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                    System.out.println(test.i.get());
                };
            }.start();
        }

    }
}
