package mx.com.endtoend.domain.creditNote.dto;

import java.math.BigDecimal;

public class AuthorizationParmasDto {

	private String orderCode;

	private BigDecimal orderNumber;

	private String employeeEmail;

	private String authorizationCode;

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public String toString() {
		return "AuthorizationParmasDto [orderCode=" + orderCode + ", orderNumber=" + orderNumber + ", employeeEmail="
				+ employeeEmail + ", authorizationCode=" + authorizationCode + "]";
	}

}
