package concurrent.chapter2.howtocondition;

public class ApplicationCondition {

	public static void main(String[] args) throws InterruptedException {
		MyService service = new MyService();
		Runnable runnable1 = new MyServiceThread1(service);
		Runnable runnable2 = new MyServiceThread2(service);
		
		new Thread(runnable1, "a").start();
		new Thread(runnable2, "b").start();
		
		// �߳�sleep2����
		Thread.sleep(2000);
		// �������г���conditionA���߳�
		service.signallA();
		
		Thread.sleep(2000);
		// �������г���conditionB���߳�
		service.signallB();
	}

}
