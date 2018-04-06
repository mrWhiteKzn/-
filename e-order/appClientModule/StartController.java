import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class StartController {
	
	String url 		= null;
	String ipAddr 	= null;
	String dataBase = null;
	String port		= null;
	String user		= null;
	String password	= null;		
	
	public StartController() {	
		
	}
	
	boolean isAvailable() {		
		boolean reachable = false;	
		url = "jdbc:mysql://"+this.ipAddr+":"+this.port +"/" + this.dataBase;
		try {
			
			Connection conn = DriverManager.getConnection(url, user, password);	
			if (reachable = conn.isValid(1)) {
				reachable = true;
				conn.close();
			}					
			
		} catch (Exception e) {	}
		
		
		return reachable;
	}
	
	void readConfigFile() {
		final Path fFilePath;
		fFilePath = Paths.get("config");
		String[] params = null;
		String currentString;
		
		 try (Scanner scanner =  new Scanner(fFilePath)){
			 
		     while (scanner.hasNextLine()){
		    	 
		    	 currentString = scanner.nextLine();		    	 
		    	 params  = currentString.split("=");
		    	 
		    	 if (params[0].replaceAll("\\s", "").equals("ipAddr"))		this.ipAddr	=params[1].replaceAll("\\s", "");		    	 
		    	 if (params[0].replaceAll("\\s", "").equals("port")) 		this.port	=params[1].replaceAll("\\s", "");
		    	 if (params[0].replaceAll("\\s", "").equals("dataBase")) 	this.dataBase=params[1].replaceAll("\\s", "");
		    	 if (params[0].replaceAll("\\s", "").equals("user")) 		this.user	=params[1].replaceAll("\\s", "");
		    	 if (params[0].replaceAll("\\s", "").equals("password"))	this.password=params[1].replaceAll("\\s", "");		    	 
		    	 
		      }      
		 } catch (IOException e) {
			e.printStackTrace();
		} 	
		 
	}
	
	
}
