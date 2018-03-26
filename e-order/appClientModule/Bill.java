
class Bill {
	
	private String billNumber;
	private String orgName;
	private int time;

	Bill(String billNumber, int time) { 
		this.billNumber=billNumber;
		this.time = time;
	}
	
	Bill() {
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

	protected void setOrgName(String orgName) {	
		this.orgName = orgName;	
		}	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	
}
