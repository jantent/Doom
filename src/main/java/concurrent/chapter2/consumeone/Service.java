package concurrent.chapter2.consumeone;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Service {

	private Lock lock = new ReentrantLock();
	private boolean flag = false;
	private Condition condition = lock.newCondition();
	// �Դ�Ϊ������־
	private int number = 1;

	/**
	 * ����������
	 */
	public void produce() {
		try {
			lock.lock();
			while (flag == true) {
				condition.await();
			}
			System.out.println(Thread.currentThread().getName() + "-----����-----");
			number++;
			System.out.println("number: " + number);
			System.out.println();
			flag = true;
			// ��������������
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * ������������������Ʒ
	 */
	public void consume() {
		try {
			lock.lock();
			while (flag == false) {
				condition.await();
			}
			System.out.println(Thread.currentThread().getName() + "-----����-----");
			number--;
			System.out.println("number: " + number);
			System.out.println();
			flag = false;
			// ��������������
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
