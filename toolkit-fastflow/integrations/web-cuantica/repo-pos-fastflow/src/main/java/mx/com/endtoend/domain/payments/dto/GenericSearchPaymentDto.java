package mx.com.endtoend.domain.payments.dto;

import java.math.BigDecimal;
import java.util.Date;

public class GenericSearchPaymentDto {

	private String orderCode;

	private BigDecimal orderNumber;

	private String branchCode;

	private Date from;

	private Date to;

	private String employeeEmail;

	public String getOrderCode() {
		return orderCode;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "GenericSearchPaymentDto [orderCode=" + orderCode + ", orderNumber=" + orderNumber + ", branchCode="
				+ branchCode + ", from=" + from + ", to=" + to + ", employeeEmail=" + employeeEmail + "]";
	}

}