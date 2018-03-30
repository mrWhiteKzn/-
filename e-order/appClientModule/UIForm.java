import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import java.sql.PreparedStatement;

public class UIForm {	
	
	JFrame frame = new JFrame( "Mr White Program" );
	JTabbedPane tabbedPane;	
	JPanel tab1, tab2, tab3, tab4;
	JScrollPane clientMonitorScrollPane;
	Bill currentBill = new Bill();	
	
	JTable clientMonitorTable = new JTable ();	
	JTable rulerTable = new JTable();	
	JScrollPane scrollPane = new JScrollPane();
	ClientMonitor monitorTable;
	RulerMonitor rulerMonitor;
	
	void refreshClientMonitor(){
		monitorTable.refreshData();
	}
	
	void refreshRulerTabelData() {
		rulerMonitor.refreshData();
	}	
	
	void createCheckBoxes(JPanel tabPanel) {
		MysqlConnector myConnector = new MysqlConnector();
		ResultSet warehouses;	
		warehouses = myConnector.getData( "Select * from dbassembly.warehouses" );
		ArrayList<String> checkBoxArray = new ArrayList<String>();
		GridBagConstraints c = new GridBagConstraints();
		
		try {
	
			c.gridx = 0;
			c.gridy = 3;
			int[] distanceCheckBox = {0,10,0,0};					
			while(warehouses.next()){
			
				JCheckBox chbox = new JCheckBox( warehouses.getString("wh_name") );		
				Element.createElement(chbox, tabPanel, c.gridx, c.gridy++, distanceCheckBox, true);
				checkBoxArray.add( chbox.getName() );		
			
				if( c.gridy > 6 ) {
					c.gridy = 3;
					c.gridx++;
				}		
			}
		}
		catch(Exception ex) {
			System.out.println( ex );
		}
		myConnector.closeConnection();
	}
	
	static void removeCheckboxes(JPanel currentPanel, JFrame currentFrame) {
		
		for( int i=0; i<currentPanel.getComponentCount(); i++ ) {			
			if( currentPanel.getComponent( i ) instanceof JCheckBox ) {				
				currentPanel.remove(i);
				removeCheckboxes(currentPanel, currentFrame);
			}			
		}
		currentPanel.updateUI();
	}
	
	private void cleanTab1(JTextField billNumber,JTextField nameOrganization, JTextField timeBilling) {
		billNumber.setText( null );
		nameOrganization.setText( null );
		timeBilling.setText( null );
		
		for( int i=0; i<tab1.getComponentCount(); i++ ) {
			if( tab1.getComponent(i) instanceof JCheckBox) {
				JCheckBox ch = (JCheckBox) tab1.getComponent(i);
				ch.setSelected( false );
			}
		} 
	}
	
	public UIForm() {				

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(500, 400));		
		frame.setResizable(false);
		
		tab1 = new JPanel( new GridBagLayout() );	
		tab2 = new JPanel( new GridLayout());
		tab3 = new JPanel( new GridBagLayout() );		
		tab4 = new JPanel( new GridBagLayout() );		
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab( "Сборки", tab1 );		
		tabbedPane.addTab( "Монитор", tab2 );
		tabbedPane.addTab( "Управление сборками", tab3 ); 
		tabbedPane.addTab( "Настройки", tab4 );		
		
		/** 
		 * 
		 * 
		 * SECTION PANEL#1 
		 * 
		 * */	
		JLabel lbOrganization 	= new JLabel();
		JLabel lbBillNumber 	= new JLabel();
		JLabel lbTimeBilling 	= new JLabel();
		
		lbOrganization.setText	( "Организация:" );		
		lbBillNumber.setText	( "Номер Сборки:" );		
		lbTimeBilling.setText	( "Время сборки (мин.):" );
		
		int[] distance = { 25,15,0,0 };
		Element.createElement( lbOrganization, tab1, 0, 0, distance, false );
		Element.createElement( lbBillNumber,   tab1, 0, 1, distance, false );
		Element.createElement( lbTimeBilling,  tab1, 0, 2, distance, false );		
	
		JTextField nameOrganization = new JTextField();
		JTextField billNumber 		= new JTextField();
		JTextField timeBilling 		= new JTextField();
		
		Element.createElement( nameOrganization, tab1, 1, 0, 3, true );
		Element.createElement( billNumber, tab1, 1, 1, 3, true );
		Element.createElement( timeBilling, tab1, 1, 2, 3, true);
		
		nameOrganization.addMouseListener( new MouseListener() {	
			public void mousePressed(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {nameOrganization.setText(null);}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}	
		});
		billNumber.addMouseListener( new MouseListener() {
			public void mouseClicked(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {billNumber.setText(null);}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}			
		});
		timeBilling.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) { timeBilling.setText(null);}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}			
		});
				
		createCheckBoxes(tab1);
				
		// Buttons 
		JButton addButton	= new JButton( "Добавить" );	
		JButton cleanButton	= new JButton( "Очистить" );
		Element.createElement( cleanButton, tab1, 0, 7, 1, false);
		Element.createElement( addButton, tab1, 1, 7, 2, false );	
				
		addButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!nameOrganization.getText().isEmpty() && !billNumber.getText().isEmpty()) {					
					
					int billingTime = 0;
					try {
						billingTime = Integer.parseInt( timeBilling.getText() );
					} catch ( Exception ex ) {
						JOptionPane.showMessageDialog(null, "Проверьте правильность ввода времени сборки.");
						return;
					}				
					
					Bill newBill = new Bill(nameOrganization.getText(), billNumber.getText(), billingTime);
					
					
					//Checking existing the bill in database					
					MysqlConnector connector = new MysqlConnector();
					ResultSet result;
					result = connector.getData( "Select * from dbassembly.assembly WHERE number = '"+newBill.getBillNumber()+"'" );
					try {
						if ( result.first() ) {
							JOptionPane.showMessageDialog( null, "Эта сборка уже в базе!" );
							return;
						}
					} catch (HeadlessException | SQLException e1) {
						JOptionPane.showMessageDialog( null, "Произошло что-то непонятное, пожалуйста замрите." );
						e1.printStackTrace();
					}
					
					
					// is client exist in database, if isn't then add him
					PreparedStatement preparedStatementAddClient;
					PreparedStatement preparedStatementAddBill;
					PreparedStatement createWarehousesStatement;
					String clientString;
					String billString;
					String workString;					
					
					clientString = "INSERT INTO dbassembly.clients ( cl_name )"
									+ "SELECT * FROM(SELECT ? ) "
									+ "AS tmp WHERE NOT EXISTS( "
													+ "SELECT cl_name FROM dbassembly.clients WHERE cl_name= ?"
													+ ") LIMIT 1;";
					
					billString = "INSERT INTO dbassembly.assembly ( number, id_client, startTime, planTime ) "
								+ "values (?,"
									+ " (SELECT id_client FROM dbassembly.clients WHERE cl_name= ?),"
									+ " now(),"
									+ " date_add(now(), INTERVAL ? minute));";
					
					
					workString = "INSERT INTO dbassembly.wh_works (id_assembly, id_warehouse) "
							+ "VALUES ( "
							+ "(SELECT id_assembly FROM dbassembly.assembly WHERE number = ?), "
							+ "(SELECT id_warehouse FROM dbassembly.warehouses WHERE wh_name = ?))";
					
					try {
						preparedStatementAddClient = connector.getMyConnection().prepareStatement(clientString);
						preparedStatementAddClient.setString(1, newBill.getOrgName());
						preparedStatementAddClient.setString(2, newBill.getOrgName());
						
						preparedStatementAddClient.executeUpdate();
					
						preparedStatementAddBill = connector.getMyConnection().prepareStatement(billString);
						preparedStatementAddBill.setString(1, newBill.getBillNumber());
						preparedStatementAddBill.setString(2, newBill.getOrgName());
						preparedStatementAddBill.setInt(3, newBill.getTime());
						
						preparedStatementAddBill.executeUpdate();
						System.out.println(preparedStatementAddBill.toString());
						for( int i=0; i<tab1.getComponentCount(); i++ ) {
							if( tab1.getComponent( i ) instanceof JCheckBox ) {					
								JCheckBox ch = (JCheckBox) tab1.getComponent( i );	
								createWarehousesStatement = connector.getMyConnection().prepareStatement(workString);
								
							if( ch.isSelected() ) {	
								createWarehousesStatement.setString(1, newBill.getBillNumber());	
								createWarehousesStatement.setString(2, ch.getText());							
		
								createWarehousesStatement.executeUpdate();
							}
							
						}
							
					}					
					}catch(SQLException e1) { }	
					
					JOptionPane.showMessageDialog(null, "Сборка добавлена!");
					cleanTab1(billNumber, nameOrganization, timeBilling);
					connector.closeConnection();
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Не все поля заполнены!");
				}
				
				
			}
			
		});	
		cleanButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cleanTab1(billNumber, nameOrganization, timeBilling);
			}
			
		});
	
		
		/** SECTION PANEL#2 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * */		
		monitorTable = new ClientMonitor(tab2);		
		//tab2.add(monitorTable.refreshClientMonitorTab2());	
		
		/**
		 * 
		 *  
		 *  SECTION PANEL#3 
		 *  
		 *  */			
		JLabel warehousesLabel;
		JScrollPane scrollPane 	= new JScrollPane();		
		JPanel scrollPanel 		= new JPanel();
		JButton acceptWorkButton= new JButton();		
		JPanel checkBoxesPanel 	= new JPanel();
		
		rulerMonitor = new RulerMonitor(scrollPanel, scrollPane, checkBoxesPanel, frame, rulerTable, currentBill );
		
		checkBoxesPanel.setLayout( new GridBagLayout() );			
		warehousesLabel = new JLabel( "Склады:" );
		acceptWorkButton.setText( "OK" );				
	
		Element.createElement( warehousesLabel, checkBoxesPanel, 0, 0, 0, 0, false, "ABOVE_BASELINE" );	
		Element.createElement( scrollPanel, tab3, 0, 0, 0, 2, false, "BOTH" );	
		Element.createElement( checkBoxesPanel, tab3, 1, 0, 0, 0, false, "BOTH" );		
		Element.createElement( acceptWorkButton, tab3, 1, 1, 0, 0, false, "SOUTHEAST");
		
		acceptWorkButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				Warehouse.removeWarehousesInWorks(checkBoxesPanel, currentBill, frame);					
			}
			
		});
				
				
		/** 
		 * 
		 * SECTION PANEL4
		 * 
		 */		
		JLabel lbNewWarehouse 		= new JLabel();
		JTextField newWarehouseField= new JTextField();
		JButton addWarehouseButton 	= new JButton();
		
		Element.createElement( lbNewWarehouse, tab4, 0, 0, 0, true );
		Element.createElement( newWarehouseField, tab4, 1, 0, 0, true );
		Element.createElement( addWarehouseButton, tab4, 0, 1, 0, true);	
		
		lbNewWarehouse.setText( "Имя нового склада:" );	
		addWarehouseButton.setText("Добавить склад");
		addWarehouseButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String newWarehouse = newWarehouseField.getText();
				if (newWarehouse.length()>0) {
					
					boolean alreadyExisting=Warehouse.checkExisting(newWarehouse);
					if(!alreadyExisting) {
						Warehouse.addNewWarehouse(newWarehouse);						
						JOptionPane.showMessageDialog(null, newWarehouseField.getText() +" добавлен. Перезайдите в программу.");
						frame.repaint();
					}
					
				}				
			}			
		});
		
		/** END OF SECTION PANEL4 */
		
		frame.add( tabbedPane);
		frame.pack();
		frame.setVisible( true );	
	}
}
