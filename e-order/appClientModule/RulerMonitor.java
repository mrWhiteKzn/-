import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RulerMonitor implements Monitor{
	
	JTable rulerTable = new JTable();

	RulerMonitor(JPanel scrollPanel, JScrollPane scrollPane, JPanel checkBoxesPanel, JFrame frame, JTable rulerTable, Bill currentBill){
		
		this.rulerTable = rulerTable;	
		scrollPane = new JScrollPane( rulerTable );		
		scrollPane.setPreferredSize(new Dimension (230,415));
		scrollPanel.add( scrollPane );					
		
		rulerTable.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked( MouseEvent event ) {
				
				UIForm.removeCheckboxes(checkBoxesPanel, frame);				
				
				int row = rulerTable.rowAtPoint( event.getPoint() );
				int col = rulerTable.columnAtPoint( event.getPoint() );
				String bill = (String) rulerTable.getValueAt( row, col );					
				currentBill.setBillNumber(bill);
				
				if (row >= 0 && col >= 0 && bill != null) {
									
					ArrayList<String> wareHousesList = Warehouse.showWarehousesInWorks( bill );												
					int i=1;
					
					for( String warehouseName : wareHousesList ) {
						JCheckBox ch = new JCheckBox( warehouseName );	
						Element.createElement(ch, checkBoxesPanel, 0, i, 0, false);
						ch.setName( "chwhwork"+i++ );
						}
											
					frame.repaint();					
				 }
			}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}				
		});
	}
	@Override
	public void refreshData() {		
		String query = "Select number, planTime FROM dbassembly.assembly WHERE state=0";
		
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(query);	
		
		

		rulerTable.setModel(DBUtils.resultSetToTableBodel(result));
		
		TimeChangeColorRenderer colorRenderer = new TimeChangeColorRenderer();
		rulerTable.setDefaultRenderer(Object.class, colorRenderer);
	}

}
