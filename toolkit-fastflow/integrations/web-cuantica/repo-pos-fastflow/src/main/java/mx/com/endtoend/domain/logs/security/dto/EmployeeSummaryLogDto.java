package mx.com.endtoend.domain.logs.security.dto;

import java.util.Date;

import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;

public class EmployeeSummaryLogDto {

	private String user;

	private Date updatedDate;

	private Long userId;

	private String username;

	private EmployeeDto employeeSaved;

	private EmployeeDto employeeUpdated;

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public EmployeeDto getEmployeeSaved() {
		return employeeSaved;
	}

	public void setEmployeeSaved(EmployeeDto employeeSaved) {
		this.employeeSaved = employeeSaved;
	}

	public EmployeeDto getEmployeeUpdated() {
		return employeeUpdated;
	}

	public void setEmployeeUpdated(EmployeeDto employeeUpdated) {
		this.employeeUpdated = employeeUpdated;
	}

	@Override
	public String toString() {
		return "EmployeeSummaryLogDto [user=" + user + ", updatedDate=" + updatedDate + ", userId=" + userId
				+ ", username=" + username + ", employeeSaved=" + employeeSaved + ", employeeUpdated=" + employeeUpdated
				+ "]";
	}

}
