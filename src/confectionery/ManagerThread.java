/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


public class ManagerThread extends Thread{
	private OrdersQueue<Order> orders;
	private DeliveryQueue<DeliveryThread> threadPool;
	private ThreadPoolExecutor doughConfectioners;
	private ThreadPoolExecutor creamConfectioners;
	private ThreadPoolExecutor decorationsConfectioners;
	
	public ManagerThread(OrdersQueue<Order> orders, DeliveryQueue<DeliveryThread> threadPool, ThreadPoolExecutor doughConfectioners,
						 ThreadPoolExecutor creamConfectioners, ThreadPoolExecutor decorationsConfectioners) {
		this.orders = orders;
		this.threadPool = threadPool;
		this.doughConfectioners = doughConfectioners;
		this.creamConfectioners = creamConfectioners;
		this.decorationsConfectioners = decorationsConfectioners;
	}

	@Override
	public void run() {
		while (orders.isOpen()) {
			Order order = null;
			try {
				Optional<Order> optionalOrder = orders.take();
				if (optionalOrder.isPresent())
					order = optionalOrder.get();
					
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (order != null)
			{
				System.err.printf("Manager %s retrieved an order.\n",Thread.currentThread().getName());
				System.err.printf("Manager %s sent preparation tasks to confectioners.\n",Thread.currentThread().getName());
				ArrayList<Future<?>> future = new ArrayList<>();
				future.add(doughConfectioners.submit(new DoughTask(order.getDoughPreparationTime())));
				future.add(creamConfectioners.submit(new CreamTask(order.getCreamPreparationTime())));
				future.add(decorationsConfectioners.submit(new DecorationsTask(order.getDecorationsPreparationTime())));
				for (int i=0;i<future.size();++i)
				{
					try {
						future.get(i).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
					} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				}
				System.err.printf("Manager %s received cake from confectioners.\n",Thread.currentThread().getName());
				DeliveryThread deliveryMan = null;
				try {
					Optional<DeliveryThread> optionalDeliveryMan = threadPool.take();
					if (optionalDeliveryMan.isPresent())
						deliveryMan = optionalDeliveryMan.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (deliveryMan != null)
				{
					System.err.printf("Manager %s gave the order to %s\n", Thread.currentThread().getName(),deliveryMan.getName());
					deliveryMan.getDelivery().setDelivery(order);
				}
			}
		}
		
	}
	

}
