import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class TimeChangeColorRenderer implements TableCellRenderer{
	public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	
	Color defaultColor = null;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(column == 1) {	
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			java.util.Date parsedDate = null;
			try {
				parsedDate = dateFormat.parse((String) table.getValueAt(row, column));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Timestamp time = new java.sql.Timestamp(parsedDate.getTime());
			java.util.Date now = new java.util.Date();			
			
			if(time.before(now)) {
				c.setBackground(Constants.TIME_EXPIRED_COLOR);				
			} else {
				c.setBackground(null);
			}			
		}		
		return c;
	}
	
}	
