package concurrent.chapter3.threadpool;

import java.util.concurrent.*;

public class MyThreadPool {

    public static void main(String[] args){
       ExecutorService saveThreadPool = new ThreadPoolExecutor(2, 40, 10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        saveThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getId()+"工作");
            }
        });
    }


}
