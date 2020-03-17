/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class OrderTaker {
	
	public static OrdersQueue<Order> orders;
	public static DeliveryQueue<DeliveryThread> deliveries;
	public static ThreadPoolExecutor doughConfectioners;
	public static ThreadPoolExecutor creamConfectioners;
	public static ThreadPoolExecutor decorationsConfectioners;
	public static ArrayList<Thread> managers;
	public static void init()
	{
		orders = new OrdersQueue<Order>();
		deliveries = new DeliveryQueue<DeliveryThread>();
		managers = new ArrayList<>();
		doughConfectioners = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.DOUGH_CONFECTIONERS_NUMBER);
		creamConfectioners = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.CREAM_CONFECTIONERS_NUMBER);
		decorationsConfectioners = (ThreadPoolExecutor) Executors.newFixedThreadPool(Constants.DECORATIONS_CONFECTIONERS_NUMBER);
	}
	
	public static void startWork()
	{
		for (int i=1;i<=Constants.MANAGERS_NUMBER;++i)
		{
			Thread manager = new ManagerThread(orders, deliveries, doughConfectioners, creamConfectioners, decorationsConfectioners);
			manager.setName("Manager" + i);
			manager.start();
			managers.add(manager);
		}
		for (int i=1;i<=Constants.DELIVERY_NUMBER;++i)
		{
			DeliveryThread deliveryMan = new DeliveryThread(deliveries);
			deliveryMan.setName("DeliveryMan" + i);
			deliveryMan.start();
			try {
				deliveries.put(deliveryMan);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	public static void endWork()
	{
		deliveries.close();
		deliveries.getThreadsPool().forEach(x -> x.getDelivery().close());
		doughConfectioners.shutdown();
		creamConfectioners.shutdown();
		decorationsConfectioners.shutdown();
	}
	public static void main(String[] args) {
		
		init();
		startWork();
		Scanner scanner = new Scanner(System.in);
		String command = scanner.nextLine();
		while (!command.contains("end"))
		{
			String orderDetails[] = command.split(" ");
			Order order = new Order(orderDetails[0],
									Long.valueOf(orderDetails[1]),
									Long.valueOf(orderDetails[2]),
									Long.valueOf(orderDetails[3]),
									Long.valueOf(orderDetails[4]));
			
			try {
				orders.put(order);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			command = scanner.nextLine();
			
		}
		scanner.close();
		orders.close();
		for (int i=0;i<Constants.MANAGERS_NUMBER;++i)
		{
			try {
				managers.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		endWork();
	}

}
