package mx.com.endtoend.domain.roles.dto;

import java.util.List;

public class RoleDto {

	private Long id;
	private String name;
	private String description;
	private String companyCode;
	boolean enabled;
	List<PermissionDto> permissions;
	
	public RoleDto roleDtoEmpty() {
		RoleDto roleDtoEmpty = new RoleDto();
		roleDtoEmpty.setId(null);
		roleDtoEmpty.setName("");
		roleDtoEmpty.setDescription("");
		roleDtoEmpty.setCompanyCode("");
		roleDtoEmpty.setEnabled(false);
		roleDtoEmpty.setPermissions(null);
		return roleDtoEmpty;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PermissionDto> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDto> permissions) {
		this.permissions = permissions;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + ", description=" + description + ", companyCode=" + companyCode
				+ ", enabled=" + enabled + ", permissions=" + permissions + "]";
	}

}