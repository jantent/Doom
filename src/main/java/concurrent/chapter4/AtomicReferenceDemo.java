package concurrent.chapter4;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author tangj
 * @date 2018/1/30 22:29
 */
public class AtomicReferenceDemo {

    static AtomicReference<Integer> money = new AtomicReference<>();

    public static void main(String[] args) {
        money.set(19);


        new Thread() {
            @Override
            public void run() {
                Integer m = money.get();
                while (true) {

                    if (m > 10) {
                        System.out.println("大于10元");
                        if (money.compareAndSet(m, m - 10)) {
                            System.out.println("成功消费10元， 余额：" + money.get() + " 元");
                            break;
                        }
                    } else {
                        System.out.println("没有足够的金额");
                        break;
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        for (int i = 0; i < 3; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        while (true) {
                            Integer m = money.get();
                            if (m < 20) {
                                if (money.compareAndSet(m, m + 20)) {
                                    System.out.println("余额小于20元，充值成功，余额： " + money.get() + " 元");
                                    break;
                                } else {
                                    System.out.println("没有足够的金额");
                                    break;
                                }
                            }
                        }
                    }
                }
            }.start();
        }

    }
}
