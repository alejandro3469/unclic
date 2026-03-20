package mx.com.endtoend.domain.catalogue.dto.article;

public class ArticleDivisionDto {

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

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ArticleDivisionDto [id=" + id + ", isEnable=" + isEnable + ", code=" + code + ", value=" + value + "]";
	}

}
