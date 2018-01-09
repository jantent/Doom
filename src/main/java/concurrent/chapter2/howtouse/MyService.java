package concurrent.chapter2.howtouse;

import java.util.concurrent.locks.ReentrantLock;

/**
 * �߳�ִ��ҵ��
 * 
 * @author tangj
 *
 */
public class MyService {

	ReentrantLock lock = new ReentrantLock();

	public void servicMethod() {
		// ����
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "����service");
			// �����߳�˯��
			Thread.sleep(3000);
			for (int i = 0; i < 3; i++) {
				System.out.println("��ӡ��: " + i);
			}
			System.out.println(Thread.currentThread().getName() + "�˳�service");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// �����˹�������Ȼ�������߳̾ͽ�������
		lock.unlock();
	}

}
