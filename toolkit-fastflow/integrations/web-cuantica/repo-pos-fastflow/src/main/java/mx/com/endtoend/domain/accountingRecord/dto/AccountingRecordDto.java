package mx.com.endtoend.domain.accountingRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class AccountingRecordDto {

	private Long id;

	private Date date;

	private Long employeeId;

	private String branchCode;

	private Long openingId;

	private Long transactionId;

	private String accountingConcept;

	private String movementType;

	@NotNull(message = "El monto aplicado es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto aplicado debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountApplied;

	private String movementConcept;

	public AccountingRecordDto() {
		super();
	}

	public AccountingRecordDto(Long employeeId, String branchCode, Long openingId, Long transactionId,
			String accountingConcept, String movementType, BigDecimal amountApplied, String movementConcept) {
		super();
		this.date = new Date();
		this.employeeId = employeeId;
		this.branchCode = branchCode;
		this.openingId = openingId;
		this.transactionId = transactionId;
		this.accountingConcept = accountingConcept;
		this.movementType = movementType;
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
		this.movementConcept = movementConcept;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public Long getOpeningId() {
		return openingId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public String getAccountingConcept() {
		return accountingConcept;
	}

	public String getMovementType() {
		return movementType;
	}

	public BigDecimal getAmountApplied() {
		return amountApplied;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setOpeningId(Long openingId) {
		this.openingId = openingId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public void setAccountingConcept(String accountingConcept) {
		this.accountingConcept = accountingConcept;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
	}

	public String getMovementConcept() {
		return movementConcept;
	}

	public void setMovementConcept(String movementConcept) {
		this.movementConcept = movementConcept;
	}

	@Override
	public String toString() {
		return "AccountingRecordDto [id=" + id + ", date=" + date + ", employeeId=" + employeeId + ", branchCode="
				+ branchCode + ", openingId=" + openingId + ", transactionId=" + transactionId + ", accountingConcept="
				+ accountingConcept + ", movementType=" + movementType + ", amountApplied=" + amountApplied
				+ ", movementConcept=" + movementConcept + "]";
	}

}
