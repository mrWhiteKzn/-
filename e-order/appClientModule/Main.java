import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) {
		
		RunController runController = new RunController();
		runController.readConfigFile();
	
		boolean goodConnection = runController.isAvailable();			
	
		if(goodConnection) {
			UIForm window = new UIForm();
			Thread myThread = new Thread(new Runnable() {

				@Override
				public void run() {					
					try {
						while(true) {							
							window.refreshClientMonitorTab2();
							window.refreshRulerTabelData();
							Thread.sleep(Constants.DATA_REFRESH_RATE);
						}									
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			});			
			myThread.start();			
		}
		else { JOptionPane.showMessageDialog(null, "Нет связи с базой данных!"); }
	}
}