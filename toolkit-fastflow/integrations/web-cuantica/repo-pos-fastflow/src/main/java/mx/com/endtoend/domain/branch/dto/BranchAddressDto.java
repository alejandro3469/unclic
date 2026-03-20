package mx.com.endtoend.domain.branch.dto;

public class BranchAddressDto {

	private String street;

	private String insideNumber;

	private String outsideNumber;

	private String cp;

	private String colony;

	private String phoneNumber;

	public String getStreet() {
		return street;
	}

	public String getInsideNumber() {
		return insideNumber;
	}

	public String getOutsideNumber() {
		return outsideNumber;
	}

	public String getCp() {
		return cp;
	}

	public String getColony() {
		return colony;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setInsideNumber(String insideNumber) {
		this.insideNumber = insideNumber;
	}

	public void setOutsideNumber(String outsideNumber) {
		this.outsideNumber = outsideNumber;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public void setColony(String colony) {
		this.colony = colony;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
