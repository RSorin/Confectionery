/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrdersQueue<T> implements SyncQueue<T> {
	
	private LinkedList<T> orders; 
	private boolean open = true; 
	private Lock queueLock; 
	private Condition putCond; 
	private Condition getCond;
	
	public OrdersQueue() 
	{ 
		queueLock = new ReentrantLock(); 
		putCond = queueLock.newCondition(); 
		getCond = queueLock.newCondition();
		orders = new LinkedList<T>();
	}
	
	@Override 
	public boolean put(T order) throws InterruptedException 
	{ 
		queueLock.lock(); 
		try { 
			while (open && orders.size() > Constants.MANAGERS_NUMBER) 
				putCond.await(); 
			if (!open) 
				return false; 
			orders.add(order); 
			getCond.signal(); 
			return true; 
		} finally { 
			queueLock.unlock(); 
		} 
	}
	
	@Override 
	public Optional<T> take() throws InterruptedException 
	{ 
		queueLock.lock(); 
		try { 
			while (open && orders.isEmpty()) 
				getCond.await(); 
			if (open) 
				putCond.signal(); 
			T order = orders.poll(); 
			return Optional.ofNullable(order); 
		} finally { 
			queueLock.unlock(); 
		} 
	}
	
	@Override 
	public void close() 
	{ 
		queueLock.lock(); 
		try { 
			open = false; 
			putCond.signalAll(); 
			getCond.signalAll(); 
		} finally { 
			queueLock.unlock(); 
		} 
	}

	public boolean isOpen() {
		return open;
	}
	



}
