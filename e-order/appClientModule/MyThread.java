
public class MyThread implements Runnable{

	@Override
	public void run() {
		System.out.println("Thread");
		/*
		 * 
		 * checkUpdate(){
		 * 
		 * }
		 * refresh tab2()
		 * 
		 * refresh tab3()
		 * 
		 * 
		 * 
		 */
		
		while(true) {
			
			try {
				
				wait(10000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
}
