import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class DBUtils {
	public static TableModel resultSetToTableBodel(ResultSet resultset) {
		try {
			ResultSetMetaData metaData = resultset.getMetaData();
			int countOfColumns = metaData.getColumnCount();
			Vector columnNames = new Vector();
			
			//Get column names
			for(int column = 0; column < countOfColumns; column++) {
				columnNames.addElement(metaData.getColumnName(column+1));
			}
			
			//Get all rows
			Vector rows = new Vector();
			
			while (resultset.next()) {
				Vector newRow = new Vector();
				
				for(int i =1; i<= countOfColumns; i++) {
					newRow.addElement(resultset.getObject(i));
				}
				rows.addElement(newRow);
			}
			
		return new DefaultTableModel(rows, columnNames);	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
}
