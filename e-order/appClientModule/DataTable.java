import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class DataTable {
	
	String query;	
	Vector <String> columnNames	= new Vector<String>();
	Vector <Object> table 		= new Vector<Object>();	
	
	public DataTable(String query) {
		this.query = query;		
		
	}
	
	DefaultTableModel getDataTable()  {
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(query);
		ResultSetMetaData resultSetMetaData= null;
		try {
			resultSetMetaData = (ResultSetMetaData) result.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("1");
			e.printStackTrace();
		}
		
		
		/*
		 * 
		 * Set Names of columns
		 */		
		try {
			for (int columnIndex =1; columnIndex<=resultSetMetaData.getColumnCount();columnIndex++) {
				columnNames.add(resultSetMetaData.getColumnName(columnIndex));		
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("2");
		}
		
		/*
		 * 
		 * Set data for table 
		 */		
		try {
			while (result.next()) {
				Vector <Object> data = new Vector<Object>();			
				for(int columnIndex=1; columnIndex<=resultSetMetaData.getColumnCount(); columnIndex++) {				
					data.add(result.getObject(columnIndex));					
				}		
				table.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("3");
			e.printStackTrace();
		}
		try {
			result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("4");
			e.printStackTrace();
		}
		myConnector.closeConnection();
		return new DefaultTableModel(table, columnNames);
	}
	
}
