package mx.com.endtoend.domain.userConfigurations.dto;

public class CreditNoteTypeDto {

	private Long id;

	private String code;

	private String type;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "CreditNoteTypeDto [id=" + id + ", code=" + code + ", type=" + type + "]";
	}
}