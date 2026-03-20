package mx.com.endtoend.domain.userConfigurations.dto;

/**
 * 
 * @author ddcasas
 *
 */

public class RoleJobTypeDto {

	private Long id;

	private String code;

	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "RoleJobTypeDto [id=" + id + ", code=" + code + ", name=" + name + "]";
	}

}
