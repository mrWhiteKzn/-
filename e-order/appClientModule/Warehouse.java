import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class Warehouse {
	static boolean checkExisting(String nameWarehouse) {
		
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet resultset;
		String query= "select * from dbassembly.warehouses where wh_name = '"
				+ nameWarehouse
				+"'";
		resultset=myConnector.getData(query);
		JOptionPane.showMessageDialog(null, "dud" );
		try {
			if(resultset.isBeforeFirst()) return true;
		} catch (SQLException e) { e.printStackTrace();	}
		JOptionPane.showMessageDialog(null, "Склад с именем "+ nameWarehouse +" уже существует!" );
		return false;
	}
	
	static void addNewWarehouse(String nameWarehouse) {
		MysqlConnector myConnector = new MysqlConnector();
		String query = "insert into dbassembly.warehouses (wh_name) values ('"
				+nameWarehouse
				+"');";
		myConnector.sendData( query );
				
	}
	
	static ArrayList<String>  showWarehousesInWorks(String billNumber)  {
		
		//Get all Warehouses
		String query = "SELECT wh_name FROM dbassembly.wh_works, dbassembly.warehouses "
				+ "WHERE dbassembly.wh_works.id_assembly = (Select id_assembly from dbassembly.assembly where number = '"+billNumber+"') "
				+ "and dbassembly.wh_works.id_warehouse = dbassembly.warehouses.id_warehouse";
		
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet myResult;
		myResult = myConnector.getData(query);
		ArrayList<String> wareHousesList = new ArrayList<String>();
		
		try {
			while(myResult.next()) wareHousesList.add(myResult.getString(1));
		}catch(Exception e) {
			
		}
		myConnector.closeConnection();
		return wareHousesList;
	}
	
	static void removeWarehousesInWorks(JPanel checkBoxesPanel, Bill currentBill, JFrame frame) {
		MysqlConnector myConnector = new MysqlConnector();
		
		for (int i=0; i<checkBoxesPanel.getComponentCount(); i++) {
			if( checkBoxesPanel.getComponent(i) instanceof JCheckBox) {
				JCheckBox ch = (JCheckBox) checkBoxesPanel.getComponent( i );
				if (ch.isSelected()) {
					String warehouseName = ch.getText();
					String billNumber = currentBill.getBillNumber();
					
					String query = "DELETE from wh_works where id_assembly = ("
							+ "Select id_assembly from dbassembly.assembly where number = '"+billNumber+"')"
							+ "and id_warehouse = ("
							+ "Select id_warehouse from dbassembly.warehouses where wh_name = '"+warehouseName+"')";
					
					myConnector.sendData(query);
					JOptionPane.showMessageDialog(null, "Сборка отмечена.");
					
					SwingUtilities.updateComponentTreeUI(frame);
					frame.invalidate();
					frame.validate();
					frame.repaint();
					
					checkBoxesPanel.remove(i);
				}
				
			}
		}
		myConnector.closeConnection();
	}
	
	public Warehouse() {
		
	}
	
}
