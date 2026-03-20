package mx.com.endtoend.domain.catalogue.dto.address;

public class ColonyDto {

	private Long id;

	private boolean isEnable;

	private String cp;

	private String name;

	private String stateCode;

	private String city;

	private String municipality;

	public ColonyDto() {
	}

	public ColonyDto(String cp, String name, String stateCode, String city) {
		this.id = null;
		this.isEnable = true;
		this.cp = cp;
		this.name = name;
		this.stateCode = stateCode;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean enable) {
		isEnable = enable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getMunicipality() {
		return municipality;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	@Override
	public String toString() {
		return "ColonyDto [id=" + id + ", isEnable=" + isEnable + ", cp=" + cp + ", name=" + name + ", stateCode="
				+ stateCode + ", city=" + city + ", municipality=" + municipality + "]";
	}

}
