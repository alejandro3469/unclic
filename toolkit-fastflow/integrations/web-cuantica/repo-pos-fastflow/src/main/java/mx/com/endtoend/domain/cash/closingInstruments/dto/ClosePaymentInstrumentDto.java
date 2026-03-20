package mx.com.endtoend.domain.cash.closingInstruments.dto;

public class ClosePaymentInstrumentDto {

	private Long id;

	private String code;

	private String name;

	private String incomeType;

	private boolean isEnabled;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public boolean getIsEnabled() {
		return isEnabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public ClosePaymentInstrumentDto() {
		super();
	}

	public ClosePaymentInstrumentDto(Long id, String code, String name, String incomeType, boolean isEnabled) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.incomeType = incomeType;
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "ClosePaymentInstrumentDto [id=" + id + ", code=" + code + ", name=" + name + ", incomeType="
				+ incomeType + ", isEnabled=" + isEnabled + "]";
	}

}
