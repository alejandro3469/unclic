package mx.com.endtoend.domain.users.dto;

public class GenericSerchParamsUserDto {

	private String branchCode;

	private boolean isEnable;

	private Long userNumber;

	private String name;

	private boolean isAllBranches;

	private String companyCode;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public Long getUserNumber() {
		return userNumber;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsAllBranches() {
		return isAllBranches;
	}

	public void setIsAllBranches(boolean isAllBranches) {
		this.isAllBranches = isAllBranches;
	}

	@Override
	public String toString() {
		return "GenericSerchParamsUserDto [branchCode=" + branchCode + ", isEnable=" + isEnable + ", userNumber="
				+ userNumber + ", name=" + name + ", isAllBranches=" + isAllBranches + ", companyCode=" + companyCode
				+ "]";
	}

}
