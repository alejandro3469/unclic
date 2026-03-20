package mx.com.endtoend.domain.orders.dto;

import java.math.BigDecimal;

public class GenericActionControllOrderDto {

	private String orderType;

	private BigDecimal orderNumber;

	private String employeeEmail;

	private String authorizationCode;

	public String getOrderType() {
		return orderType;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	@Override
	public String toString() {
		return "GenericActionControllOrderDto [orderType=" + orderType + ", orderNumber=" + orderNumber
				+ ", employeeEmail=" + employeeEmail + ", authorizationCode=" + authorizationCode + "]";
	}

	public String personalToString() {
		return "Input data [order type= " + orderType + ", order number =" + orderNumber + ", employeeEmail="
				+ employeeEmail + ", authorizationCode=" + " ********** " + "]";
	}

}
