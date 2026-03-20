package mx.com.endtoend.domain.recharges.dto;

public class CompanyRechargeConfigurationDto {

	private String companyCode;

	private String originatorCode;

	private String user;

	private String password;

	public CompanyRechargeConfigurationDto(String companyCode, String originatorCode, String user, String password) {
		super();
		this.companyCode = companyCode;
		this.originatorCode = originatorCode;
		this.user = user;
		this.password = password;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getOriginatorCode() {
		return originatorCode;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "CompanyRechargeConfigurationDto [companyCode=" + companyCode + ", originatorCode=" + originatorCode
				+ ", user=" + user + ", password=" + password + "]";
	}

}
