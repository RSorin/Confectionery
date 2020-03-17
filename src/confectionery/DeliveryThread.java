/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;


public class DeliveryThread extends Thread{
	private Delivery delivery;
	private DeliveryQueue<DeliveryThread> deliveries;
	
	public DeliveryThread(DeliveryQueue<DeliveryThread> deliveries) {
		this.deliveries = deliveries;
		delivery = new Delivery();
	}

	@Override
	public void run() {
		
		while (deliveries.isOpen())
		{
				delivery.waitOrder();
				if (!deliveries.isOpen() && delivery.getOrder() == null)
					break;
				Order order = delivery.getOrder();
				if ( order != null)
				{
					System.out.printf("%s started to deliver the order\n", Thread.currentThread().getName());
					try {
						Thread.sleep(order.getDeliveryTime());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.printf("%s finished to deliver the order\n", Thread.currentThread().getName());
					delivery.setOrder(null);
					try {
						deliveries.put(this);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
	}

	public Delivery getDelivery() {
		return delivery;
	}
	
	

}
