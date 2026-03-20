package mx.com.endtoend.domain.users.dto;

import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.roles.dto.RoleDto;

public class UserDto {

	private Long id;

	private Long userNumber;

	private String name;

	private String firstSurname;

	private String secondSurname;

	private String email;

	private String password;

	private BranchDto branch;

	boolean enabled;

	boolean isSessionActive;

	private List<RoleDto> roles;

	private boolean isConfigurationComplete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public Long getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}

	public BranchDto getBranch() {
		return branch;
	}

	public void setBranch(BranchDto branch) {
		this.branch = branch;
	}

	public boolean getIsSessionActive() {
		return isSessionActive;
	}

	public void setIsSessionActive(boolean sessionActive) {
		this.isSessionActive = sessionActive;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsConfigurationComplete() {
		return isConfigurationComplete;
	}

	public void setIsConfigurationComplete(boolean isConfigurationComplete) {
		this.isConfigurationComplete = isConfigurationComplete;
	}

	public String getName() {
		return name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", userNumber=" + userNumber + ", name=" + name
				+ ", firstSurname=" + firstSurname + ", secondSurname=" + secondSurname + ", email=" + email
				+ ", password=" + password + ", branch=" + branch + ", enabled=" + enabled + ", isSessionActive="
				+ isSessionActive + ", roles=" + roles + ", isConfigurationComplete=" + isConfigurationComplete + "]";
	}

}