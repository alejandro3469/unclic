package mx.com.endtoend.domain.catalogue.dto.client;

public class ClientTypeDto {

	private Long id;

	private boolean isEnable;

	private String code;

	private String value;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "ClientTypeDto [id=" + id + ", enable=" + isEnable + ", code=" + code + ", value=" + value + "]";
	}

}
