import java.awt.Color;
import java.awt.Component;
import java.sql.Timestamp;
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
			
			if(defaultColor == null) {
				defaultColor = c.getBackground();
			}			
			
			Timestamp time = (Timestamp) table.getValueAt(row, column);
			java.util.Date now = new java.util.Date();
			
			if(time.before(now)) {
				c.setBackground(Constants.TIME_EXPIRED_COLOR);				
			} else {
				c.setBackground(defaultColor);
			}
		}		
		return c;
	}
	
}	
