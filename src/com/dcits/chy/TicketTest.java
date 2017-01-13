package com.dcits.chy;

import java.util.ArrayList;

public class TicketTest {

	private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

	public void orderTicket() {
		if (hasTicket()) {
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " "
					+ tickets.remove(0) + " ordered...");
		} else {
			System.out.println("the tickets is empty...");
		}
	}

	public boolean hasTicket() {
		return !tickets.isEmpty();
	}

	public TicketTest() {
		for (int i = 0; i < 100; i++) {
			tickets.add(new Ticket(i));
		}
	}

	public static void main(String[] args) {
		TicketTest ticker = new TicketTest();
		for (int i = 1; i <= 10; i++) {
			new Thread(new Customer(ticker), "Customer" + i).start();
		}
		// Thread t1=new Thread(new Customer(ticker),"Customer1");
		// Thread t2=new Thread(new Customer(ticker),"Customer2");
		// Thread t3=new Thread(new Customer(ticker),"Customer3");
		// Thread t4=new Thread(new Customer(ticker),"Customer3");
		// t4.start();
		// Thread t5=new Thread(new Customer(ticker),"Customer3");
		// t5.start();
		// t1.start();
		// t2.start();
		// t3.start();
	}

}

class Ticket {
	private int ticketNo = 0;

	public Ticket(int ticketNo) {
		this.setTicketNo(ticketNo);
	}

	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}

	public int getTicketNo() {
		return ticketNo;
	}

	@Override
	public String toString() {
		return "ticket" + getTicketNo();
	}

}

class Customer implements Runnable {

	private TicketTest ticker = null;

	public Customer(TicketTest ticker) {
		this.ticker = ticker;
	}

	@Override
	public void run() {
		while (ticker.hasTicket()) {
			ticker.orderTicket();
		}
	}

}