import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
				if(metaData.getColumnName(column+1).equals("number")) {
					columnNames.addElement("Номер сборки");
				}
				else if(metaData.getColumnName(column+1).equals("planTime")){
					columnNames.addElement("Время готовности");
				}
				else {
					columnNames.addElement(metaData.getColumnName(column+1));
				}
				
			}
			
			//Get all rows
			Vector rows = new Vector();
			
			while (resultset.next()) {
				Vector newRow = new Vector();
				
				for(int i =1; i<= countOfColumns; i++) {
					if(resultset.getObject(i) instanceof Timestamp) {
						Timestamp time = (Timestamp) resultset.getObject(i);						
						newRow.addElement(convertTimeFormat(time));
					}
					else {
						newRow.addElement(resultset.getObject(i));
					}					
					
				}
				rows.addElement(newRow);
			}
			
		return new DefaultTableModel(rows, columnNames);	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	static String convertTimeFormat(Timestamp time){		
		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");		
		String formattedTime = outputFormat.format(time);	
		return formattedTime;
	}
}
