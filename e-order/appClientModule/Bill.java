
class Bill {
	
	String billNumber;
	String orgName;		

	Bill(String number) { 
		billNumber=number; 
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
}
