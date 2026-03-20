package mx.com.endtoend.domain.orders.dto;

public class AddressListDto {
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getColony() {
		return colony;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public String getDelegation() {
		return delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getInteriorNumber() {
		return interiorNumber;
	}

	public void setInteriorNumber(String interiorNumber) {
		this.interiorNumber = interiorNumber;
	}

	public String getOutdoorNumber() {
		return outdoorNumber;
	}

	public void setOutdoorNumber(String outdoorNumber) {
		this.outdoorNumber = outdoorNumber;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	private String street;
	
	private String outdoorNumber;
	
	private String interiorNumber;
	
	private String cp;	
	
	private String colony;
	
	private String delegation;
	
	private String state;


}
