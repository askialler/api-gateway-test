package com.dcits.chy;

public class ThreadTest {

	public static void main(String[] args) {
		
		InteruptThread t1=new InteruptThread();
		t1.start();
		Thread[] tarray=new Thread[t1.getThreadGroup().activeCount()];
//		System.out.println("t1 threadgroup:"+t1.getThreadGroup().getName());
//		System.out.println("main threadgroup:"+Thread.currentThread().getThreadGroup().getName());
		System.out.println("main threadgroup:"+Thread.enumerate(tarray));
		for(Thread t:tarray){
			System.out.println(t);
		}
		System.out.println("thread t1 start...");
		try {
			
			System.out.println("main sleep...");			
			Thread.sleep(3000);
			t1.interrupt();
			t1.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		
//		InteruptRunnable ir = new InteruptRunnable();
//		Thread t = new Thread(ir);
//		t.start();
//		try {
//			System.out.println("main() sleep 3s......");
//			Thread.sleep(3000);
//			System.out.println("thread t will exit......");
//			ir.exit();
//
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("main() exit ...");
	}

}

class InteruptRunnable implements Runnable {

	public volatile boolean isExit = false;

	@Override
	public void run() {
		while (!isExit) {

		}
		System.out.println("thread t exited ...");
	}

	public synchronized void exit() {
		isExit = true;
	}
}

class InteruptThread extends Thread {

	@Override
	public void run() {

		while(!isInterrupted()){
			System.out.println("thread t is running ...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
//				e.printStackTrace();
//				System.out.println("t1.interupted():"+Thread.interrupted());
				this.interrupt();
			}
		}
		System.out.println("thread t exit...");
	}
}
