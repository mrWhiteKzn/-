import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.jdbc.ResultSetMetaData;

public class DataTable {
	
	String query;	
	Vector <String> columnNames	= new Vector<String>();
	Vector <Object> table 		= new Vector<Object>();	
	
	public DataTable(String query) {
		this.query = query;				
	}
	
	public DataTable() {
		
	}
	
	TableModel queryToTableModel(String queryString) {
		
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(queryString);
		ResultSetMetaData resultSetMetaData= null;
		try {
			resultSetMetaData = (ResultSetMetaData) result.getMetaData();
		
		/*
		 * 
		 * Set Names of columns
		 */			
		for (int columnIndex =1; columnIndex<=resultSetMetaData.getColumnCount();columnIndex++) {
				columnNames.add(resultSetMetaData.getColumnName(columnIndex));		
		}
		
		/*
		 * 
		 * Set data for table 
		 */		
		while (result.next()) {
			Vector <Object> data = new Vector<Object>();			
			for(int columnIndex=1; columnIndex<=resultSetMetaData.getColumnCount(); columnIndex++) {				
				data.add(result.getObject(columnIndex));					
			}		
			table.add(data);
		}
		
			result.close();
		} catch (SQLException e) {	e.printStackTrace(); }
		
		myConnector.closeConnection();
		return new DefaultTableModel(table, columnNames);
	}
	
 	DefaultTableModel getDataTable()  {
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(query);
		ResultSetMetaData resultSetMetaData= null;
		try {
			resultSetMetaData = (ResultSetMetaData) result.getMetaData();
		} catch (SQLException e) { e.printStackTrace();	}
		
		/*
		 * 
		 * Set Names of columns
		 */		
		try {
			for (int columnIndex =1; columnIndex<=resultSetMetaData.getColumnCount();columnIndex++) {
				columnNames.add(resultSetMetaData.getColumnName(columnIndex));		
			}
		} catch (SQLException e) { e.printStackTrace();	}
		
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
		} catch (SQLException e) {	e.printStackTrace(); }
		
		try {
			result.close();
		} catch (SQLException e) {	e.printStackTrace(); }
		
		myConnector.closeConnection();
		return new DefaultTableModel(table, columnNames);
	}
	
}
