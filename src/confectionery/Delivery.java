/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Delivery {
	private Order order;
	private Lock orderLock; 
	private Condition setCond;
	private Boolean closed;
	
	public Delivery()
	{
		orderLock = new ReentrantLock();
		setCond = orderLock.newCondition();
		closed = false;
	}
	
	public void setDelivery(Order order)
	{
		orderLock.lock();
		try {
			this.order = order;
			setCond.signal();
		} finally {
			orderLock.unlock();
		}
	}

	public synchronized Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}

	public void close()
	{
		orderLock.lock();
		try {
			//Order dummy = new Order("dummy",1L,2L,3L,4L);
			//order = dummy;
			closed = true;
			setCond.signal();
		} finally {
			orderLock.unlock();
		}
	}
	public void waitOrder()
	{
		orderLock.lock();
		try {
			while ((order == null) && !closed) 
				  setCond.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			orderLock.unlock();
		}
	}

}
