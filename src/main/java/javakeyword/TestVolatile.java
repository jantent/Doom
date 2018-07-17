package javakeyword;

public class TestVolatile {

    private static boolean ready;

    private static int number;

    private static class InnerThread extends Thread{
        @Override
        public void run() {
            while (!ready){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(number);
            }
        }
    }

    public static void main(String args[]) throws InterruptedException {
        new InnerThread().start();
        Thread.sleep(500);
        number = 63;
        ready = false;
    }

}
