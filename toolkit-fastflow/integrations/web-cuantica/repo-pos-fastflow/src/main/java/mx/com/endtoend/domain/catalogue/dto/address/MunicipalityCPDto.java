package mx.com.endtoend.domain.catalogue.dto.address;

public class MunicipalityCPDto {

	private Long id;

	private String name;

	private String cp;

	private String stateCode;

	public MunicipalityCPDto() {
		super();
	}

	public MunicipalityCPDto(String name, String cp) {
		super();
		this.name = name;
		this.cp = cp;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getName() {
		return name;
	}

	public String getCp() {
		return cp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "MunicipalityCPDto [name=" + name + ", cp=" + cp + ", stateCode=" + stateCode + "]";
	}

}
