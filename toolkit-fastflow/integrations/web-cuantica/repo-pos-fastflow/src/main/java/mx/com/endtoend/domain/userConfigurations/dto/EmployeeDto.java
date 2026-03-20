package mx.com.endtoend.domain.userConfigurations.dto;

import java.util.List;

public class EmployeeDto {

	private Long id;

	private Long userId;

	private Long userNumber;

	private String employeeEmail;

	private String branchCode;

	private RoleJobTypeDto roleJob;

	private EmployeeDto directBoss;

	private List<EmployeeDto> employees;

	private UserConfigurationDto userConfiguration;

	public Long getId() {
		return id;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getUserNumber() {
		return userNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public RoleJobTypeDto getRoleJob() {
		return roleJob;
	}

	public EmployeeDto getDirectBoss() {
		return directBoss;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public UserConfigurationDto getUserConfiguration() {
		return userConfiguration;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setRoleJob(RoleJobTypeDto roleJob) {
		this.roleJob = roleJob;
	}

	public void setDirectBoss(EmployeeDto directBoss) {
		this.directBoss = directBoss;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	public void setUserConfiguration(UserConfigurationDto userConfiguration) {
		this.userConfiguration = userConfiguration;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", userId=" + userId + ", userNumber=" + userNumber + ", employeeEmail="
				+ employeeEmail + ", branchCode=" + branchCode + ", roleJob=" + roleJob + ", directBoss=" + directBoss
				+ ", employees=" + employees + ", userConfiguration=" + userConfiguration + "]";
	}

}
