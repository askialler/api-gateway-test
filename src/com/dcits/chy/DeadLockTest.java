package com.dcits.chy;

public class DeadLockTest {
	
	public static void main(String[] args) {
		new Thread(new DeadLockRunnable(0)).start();
		new Thread(new DeadLockRunnable(1)).start();
		new Thread(new DeadLockRunnable(2)).start();
//		new Thread(new DeadLockRunnable(3)).start();
	}
}

class DeadLockRunnable implements Runnable{
	
	private int flag=0;
	private static Object lock1=new Object();
	private static Object lock2=new Object();
	private static Object lock3=new Object();
	private static Object lock4=new Object();
	public DeadLockRunnable(int flag) {
		this.flag=flag;
	}
	
	public void deadlock() throws InterruptedException{
		
		switch(flag){
			case 0:
				synchronized (DeadLockRunnable.lock1) {
					System.out.println(Thread.currentThread().getName()+ " begin to sleep");
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+ " end to sleep");
					synchronized (DeadLockRunnable.lock2) {
						System.out.println(Thread.currentThread().getName()+ " do something");
					}
				}
				break;
			case 1:
				synchronized (DeadLockRunnable.lock2) {
					System.out.println(Thread.currentThread().getName()+ " begin to sleep");
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+ " end to sleep");
					synchronized (DeadLockRunnable.lock3) {
						System.out.println(Thread.currentThread().getName()+ " do something");
					}
				}
				break;
			case 2:
				synchronized (DeadLockRunnable.lock3) {
					System.out.println(Thread.currentThread().getName()+ " begin to sleep");
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+ " end to sleep");
					synchronized (DeadLockRunnable.lock4) {
						System.out.println(Thread.currentThread().getName()+ " do something");
					}
				}
				break;
			case 3:
				synchronized (DeadLockRunnable.lock4) {
					System.out.println(Thread.currentThread().getName()+ " begin to sleep");
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName()+ " end to sleep");
					synchronized (DeadLockRunnable.lock1) {
						System.out.println(Thread.currentThread().getName()+ " do something");
					}
				}
				break;
		
		}
		
	}
	
	@Override
	public void run() {
		try {
			deadlock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

