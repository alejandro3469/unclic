package mx.com.endtoend.domain.creditNote.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreditNoteSearchParamsDto {

	private BigDecimal folio;

	private String creditNoteCode;

	private BigDecimal orderNumber;

	private String orderCode;

	private String employeeEmail;

	private String branchCode;

	private List<Long> userIdLits;

	private Date from;

	private Date to;

	public BigDecimal getFolio() {
		return folio;
	}

	public String getCreditNoteCode() {
		return creditNoteCode;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public void setCreditNoteCode(String creditNoteCode) {
		this.creditNoteCode = creditNoteCode;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public List<Long> getUserIdLits() {
		return userIdLits;
	}

	public void setUserIdLits(List<Long> userIdLits) {
		this.userIdLits = userIdLits;
	}

	@Override
	public String toString() {
		return "CreditNoteSearchParamsDto [folio=" + folio + ", creditNoteCode=" + creditNoteCode + ", orderNumber="
				+ orderNumber + ", orderCode=" + orderCode + ", employeeEmail=" + employeeEmail + ", branchCode="
				+ branchCode + ", from=" + from + ", to=" + to + "]";
	}
}