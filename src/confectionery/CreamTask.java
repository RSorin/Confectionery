/*
 * Radu Sorin-Gabriel
 * Grupa 410 - M1
 */

package confectionery;

public class CreamTask implements Runnable{
	
	private Long time;
	
	public CreamTask(Long time) {
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
		System.out.println(ConsoleColors.YELLOW + "Confectioner" + Thread.currentThread().getId() + " started to prepare cream." +
				ConsoleColors.RESET);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(ConsoleColors.YELLOW + "Confectioner" + Thread.currentThread().getId() + " finished to prepare cream." +
				ConsoleColors.RESET);
		
	}
	

}
