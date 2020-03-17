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

public class DeliveryQueue<T> implements SyncQueue<T>{
	private LinkedList<T> threadsPool; 
	private boolean open = true; 
	private Lock queueLock; 
	private Condition putCond; 
	private Condition getCond;
	
	public DeliveryQueue() 
	{ 
		queueLock = new ReentrantLock(); 
		putCond = queueLock.newCondition(); 
		getCond = queueLock.newCondition();
		threadsPool = new LinkedList<T>();
	}
	
	@Override 
	public boolean put(T thread) throws InterruptedException 
	{ 
		queueLock.lock(); 
		try { 
			while (open && threadsPool.size() > Constants.DELIVERY_NUMBER) 
				putCond.await(); 
			if (!open) 
				return false; 
			threadsPool.add(thread); 
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
			while (open && threadsPool.isEmpty()) 
				getCond.await(); 
			if (open) 
				putCond.signal(); 
			T thread = threadsPool.poll(); 
			return Optional.ofNullable(thread); 
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

	public LinkedList<T> getThreadsPool() {
		return threadsPool;
	}

	public boolean isOpen() {
		return open;
	}
}
