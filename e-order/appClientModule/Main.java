import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) {
		System.out.println("Hello");
		RunController runController = new RunController();
		runController.readConfigFile();
	
		boolean goodConnection = runController.isAvailable();				
		
		/*
		 * 
		 * Check database connection
		 */
		if(goodConnection) {
			UIForm window = new UIForm();
			Thread myThread = new Thread(new Runnable() {

				@Override
				public void run() {					
					try {
						while(true) {
							Thread.sleep(1000);
							window.refreshClientMonitorTab2();
						}									
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			});			
			myThread.start();			
		}
		else {
			JOptionPane.showMessageDialog(null, "Нет связи с базой данных!");
		}
	}
}