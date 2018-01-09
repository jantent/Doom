package concurrent.chapter2.howtocondition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyService {

	// ʵ����һ��ReentrantLock����
	private ReentrantLock lock = new ReentrantLock();
	// Ϊ�߳�Aע��һ��Condition
	public Condition conditionA = lock.newCondition();
	// Ϊ�߳�Bע��һ��Condition
	public Condition conditionB = lock.newCondition();

	public void awaitA() {
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "������awaitA����");
			long timeBefore = System.currentTimeMillis();
			// ִ��conditionA�ȴ�
			conditionA.await();
			long timeAfter = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName()+"������");
			System.out.println(Thread.currentThread().getName() + "�ȴ���: " + (timeAfter - timeBefore)/1000+"s");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void awaitB() {
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + "������awaitB����");
			long timeBefore = System.currentTimeMillis();
			// ִ��conditionB�ȴ�
			conditionB.await();
			long timeAfter = System.currentTimeMillis();
			System.out.println(Thread.currentThread().getName()+"������");
			System.out.println(Thread.currentThread().getName() + "�ȴ���: " + (timeAfter - timeBefore)/1000+"s");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void signallA() {
		try {
			lock.lock();
			System.out.println("�������ѳ���");
			// ��������ע��conditionA���߳�
			conditionA.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public void signallB() {
		try {
			lock.lock();
			System.out.println("�������ѳ���");
			// ��������ע��conditionA���߳�
			conditionB.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
