package concurrent.chapter2;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tangj
 * 
 *         ���ʹ��ReentrantLock
 */
public class FirstReentrantLock {

	public static void main(String[] args) {
		Runnable runnable = new ReentrantLockThread();
		new Thread(runnable, "a").start();
		new Thread(runnable, "b").start();
	}

}

class ReentrantLockThread implements Runnable {
	// ����һ��ReentrantLock����
	ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		try {
			// ʹ��lock()��������
			lock.lock();
			for (int i = 0; i < 3; i++) {
				System.out.println(Thread.currentThread().getName() + "����ˣ�  " + i);
			}
		} finally {
			// ������ִ��unlock()�����ͷ���
			lock.unlock();
		}

	}

}