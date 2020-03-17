/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

public class Order {
	private String name;
	private Long doughPreparationTime;
	private Long creamPreparationTime;
	private Long decorationsPreparationTime;
	private Long deliveryTime;
	
	public Order(String name, Long doughPreparationTime, Long creamPreparationTime, 
			Long decorationsPreparationTime, Long deliveryTime) {
		this.name = name;
		this.doughPreparationTime = doughPreparationTime;
		this.creamPreparationTime = creamPreparationTime;
		this.decorationsPreparationTime = decorationsPreparationTime;
		this.deliveryTime = deliveryTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDoughPreparationTime() {
		return doughPreparationTime;
	}
	public void setDoughPreparationTime(Long doughPreparationTime) {
		this.doughPreparationTime = doughPreparationTime;
	}
	public Long getCreamPreparationTime() {
		return creamPreparationTime;
	}
	public void setCreamPreparationTime(Long creamPreparationTime) {
		this.creamPreparationTime = creamPreparationTime;
	}
	public Long getDecorationsPreparationTime() {
		return decorationsPreparationTime;
	}
	public void setDecorationsPreparationTime(Long decorationsPreparationTime) {
		this.decorationsPreparationTime = decorationsPreparationTime;
	}
	public Long getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	
}
