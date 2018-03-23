import java.sql.*;

import javax.swing.JOptionPane;
 

class MysqlConnector {
	
	private Connection myConnection = null;
	private Statement myStatement;
	private ResultSet myResultSet;
	
	
	public MysqlConnector(){
		
		try {			
			Class.forName("com.mysql.jdbc.Driver");
			myConnection=DriverManager.getConnection("jdbc:mysql://192.168.5.248:3306/dbassembly","root","1234567");
			myStatement=myConnection.createStatement();
		}
		catch(Exception ex) { System.out.println("Error: "+ex);	}		
	}

	public ResultSet getData(String queryString) {
		
		try {			
			String query = queryString;
			myResultSet=myStatement.executeQuery(query);					
		}
		catch(Exception ex){ System.out.println(ex); }
		
		return myResultSet;
		
	}
	
	public void sendData(String queryString) {
		try {
			myStatement.executeUpdate(queryString);
		}
		catch(Exception ex) { JOptionPane.showMessageDialog(null, "ERROR: "+ex); }
	}

	public void closeConnection() {
		try {
			this.myConnection.close();
		} catch (SQLException e) { 	e.printStackTrace(); }
	}
	
	public Connection getMyConnection() {
		return myConnection;
	}

	public void setMyConnection(Connection myConnection) {
		this.myConnection = myConnection;
	}
}
