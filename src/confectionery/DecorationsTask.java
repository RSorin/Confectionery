package confectionery;

public class DecorationsTask implements Runnable{
	
	private Long time;
	
	public DecorationsTask(Long time) {
		this.time = time;
	}
	

	public Long getTime() {
		return time;
	}




	public void setTime(Long time) {
		this.time = time;
	}


	@Override
	public void run() {
		System.out.println(ConsoleColors.BLUE + "Confectioner" + Thread.currentThread().getId() + " started to prepare decorations." +
				ConsoleColors.RESET);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(ConsoleColors.BLUE + "Confectioner" + Thread.currentThread().getId() + " finished to prepare decorations." +
				ConsoleColors.RESET);
		
	}
	

}
