package mx.com.endtoend.domain.logs.security.dto;

import java.util.Date;

import mx.com.endtoend.domain.roles.dto.RoleDto;

public class RoleSummaryLogDto {

	private String user;

	private Date updatedDate;

	private String roleName;

	private Long roleId;

	private RoleDto roleSaved;

	private RoleDto roleUpdated;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleDto getRoleSaved() {
		return roleSaved;
	}

	public void setRoleSaved(RoleDto roleSaved) {
		this.roleSaved = roleSaved;
	}

	public RoleDto getRoleUpdated() {
		return roleUpdated;
	}

	public void setRoleUpdated(RoleDto roleUpdated) {
		this.roleUpdated = roleUpdated;
	}

	@Override
	public String toString() {
		return "RoleSummaryLogDto [user=" + user + ", updatedDate=" + updatedDate + ", roleName=" + roleName
				+ ", roleId=" + roleId + ", roleSaved=" + roleSaved + ", roleUpdated=" + roleUpdated + "]";
	}

}
