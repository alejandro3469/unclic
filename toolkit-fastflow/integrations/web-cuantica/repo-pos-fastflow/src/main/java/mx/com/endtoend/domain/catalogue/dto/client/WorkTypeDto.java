package mx.com.endtoend.domain.catalogue.dto.client;

public class WorkTypeDto {

	private Long id;

	private boolean isEnable;

	private String code;

	private String value;

	public Long getId() {
		return id;
	}

	public boolean getIsEnable() {
		return isEnable;
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

	public void setIsEnable(boolean enable) {
		this.isEnable = enable;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "WorkTypeDto [id=" + id + ", enable=" + isEnable + ", code=" + code + ", value=" + value + "]";
	}

}
