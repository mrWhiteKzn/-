import javax.swing.JPanel;
import javax.swing.JTable;

class ClientMonitor {
	JTable clientMonitorTable = new JTable();
	DataTable dataTable; 
	
	ClientMonitor(JPanel tab2){
		tab2.add(clientMonitorTable);
		
	}
	
	void refreshClientMonitorTab2() {
		String query ="SELECT  cl_name, number, planTime "  
			 	+"FROM dbassembly.assembly, dbassembly.clients "  
			 	+"WHERE dbassembly.assembly.id_client = dbassembly.clients.id_client " 
			 	+"AND state=0";
		
		
		dataTable = new DataTable();			
		clientMonitorTable.setModel(dataTable.queryToTableModel(query));
		
	}	
}
