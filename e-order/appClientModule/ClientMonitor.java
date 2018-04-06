import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTable;

class ClientMonitor implements Monitor {
	JTable clientMonitorTable = new JTable(); 
	
	ClientMonitor(JPanel tab2){
		tab2.add(clientMonitorTable);		
	}

	@Override
	public void refreshData() {
		String query ="SELECT  cl_name, number, planTime "  
			 			+"FROM dbassembly.assembly, dbassembly.clients "  
			 			+"WHERE dbassembly.assembly.id_client = dbassembly.clients.id_client " 
			 			+"AND state=0";
				
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet result;
		
		result = myConnector.getData(query);		
		
		clientMonitorTable.setModel(DBUtils.resultSetToTableBodel(result));
		
		myConnector.closeConnection();
	}	
}
