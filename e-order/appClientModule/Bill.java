
class Bill {
	
	private String billNumber;
	private String orgName;
	private int time;

	Bill() {
	}
	
	Bill(String billNumber, int time) { 
		this.billNumber=billNumber;
		this.time = time;
	}
	
	Bill(String orgName, String billNumber, int time) {
		this.orgName = orgName;
		this.billNumber = billNumber;
		this.time = time;
	}

	protected String getBillNumber() {	
		return billNumber; 
	}

	protected void setBillNumber(String billNumber) {	
		this.billNumber = billNumber;	
	}
	
	
	protected String getOrgName() {	
		return orgName;
	}

	public int getTime() {
		return time;
	}
	

}
