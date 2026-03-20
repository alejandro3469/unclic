package mx.com.endtoend.domain.cash.bankReference.dto;

public class BankDto {

	private Long id;

	private String code;

	private String bankingInstitution;

	private String useType;

	private boolean isEnable;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getBankingInstitution() {
		return bankingInstitution;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setBankingInstitution(String bankingInstitution) {
		this.bankingInstitution = bankingInstitution;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	@Override
	public String toString() {
		return "BankDto [id=" + id + ", code=" + code + ", bankingInstitution=" + bankingInstitution + ", useType="
				+ useType + ", isEnable=" + isEnable + "]";
	}

}