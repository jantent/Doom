package concurrent.chapter2.consumeone;

public class Application {

	public static void main(String[] args) {
		Service service = new Service();
		Runnable produce = new MyThreadProduce(service);
		Runnable consume = new MyThreadConsume(service);
		new Thread(produce, "������  ").start();
		new Thread(consume, "������  ").start();
	}

}
