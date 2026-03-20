package mx.com.endtoend.domain.closings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClosingOperationDto {

	private Long closingId;

	private String branchCode;

	private String employeeEmail;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private Date creationDate;

	private List<ClosingOperationDetailDto> closingOperationDetail;

	public ClosingOperationDto() {
		super();
	}

	public ClosingOperationDto(ClosingOperationDto closingOperationDto) {
		super();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (ClosingOperationDetailDto closingOperationDetailDto : closingOperationDto.getClosingOperationDetail()) {
			totalAmount = totalAmount.add(closingOperationDetailDto.getAmount());
		}
		this.branchCode = closingOperationDto.getBranchCode();
		this.employeeEmail = closingOperationDto.getEmployeeEmail();
		this.closingOperationDetail = closingOperationDto.getClosingOperationDetail();
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.creationDate = new Date();

	}

	public ClosingOperationDto(Long closingId, String branchCode, String employeeEmail, BigDecimal totalAmount,
			Date creationDate, List<ClosingOperationDetailDto> closingOperationDetail) {
		super();
		this.closingId = closingId;
		this.branchCode = branchCode;
		this.employeeEmail = employeeEmail;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.creationDate = creationDate;
		this.closingOperationDetail = closingOperationDetail;
	}

	public Long getClosingId() {
		return closingId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public List<ClosingOperationDetailDto> getClosingOperationDetail() {
		return closingOperationDetail;
	}

	public void setClosingId(Long closingId) {
		this.closingId = closingId;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setClosingOperationDetail(List<ClosingOperationDetailDto> closingOperationDetail) {
		this.closingOperationDetail = closingOperationDetail;
	}

	@Override
	public String toString() {
		return "ClosingOperationDto [closingId=" + closingId + ", branchCode=" + branchCode + ", employeeEmail="
				+ employeeEmail + ", totalAmount=" + totalAmount + ", creationDate=" + creationDate
				+ ", closingOperationDetail=" + closingOperationDetail + "]";
	}

}
