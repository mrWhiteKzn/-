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
	
	DefaultTableModel getDataTable() throws SQLException {
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(query);
		ResultSetMetaData resultSetMetaData = (ResultSetMetaData) result.getMetaData();
		
		
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
		myConnector.closeConnection();
		return new DefaultTableModel(table, columnNames);
	}
	
}
