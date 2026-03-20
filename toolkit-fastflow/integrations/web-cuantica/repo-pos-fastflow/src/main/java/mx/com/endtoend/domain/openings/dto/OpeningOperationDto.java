package mx.com.endtoend.domain.openings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OpeningOperationDto {

	private Long openingId;

	private String branchCode;

	private String employeeEmail;

	private List<OpeningOperationDetailDto> openingOperationDetail;

	private Long closingId;

	private boolean isActive;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private Date creationDate;

	private int closeAttempts;

	public OpeningOperationDto() {
		super();
	}

	public OpeningOperationDto(OpeningOperationDto openingOperationDto) {
		super();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (OpeningOperationDetailDto openingOperationDetailDto : openingOperationDto.getOpeningOperationDetail()) {
			totalAmount = totalAmount.add(openingOperationDetailDto.getAmount());
		}
		this.branchCode = openingOperationDto.getBranchCode();
		this.employeeEmail = openingOperationDto.getEmployeeEmail();
		this.openingOperationDetail = openingOperationDto.getOpeningOperationDetail();
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.isActive = true;
		this.creationDate = new Date();
		this.closeAttempts = 0;
	}

	public OpeningOperationDto(Long openingId, String branchCode, String employeeEmail,
			List<OpeningOperationDetailDto> openingOperationDetail, Long closingId, boolean isActive,
			BigDecimal totalAmount) {
		super();
		this.openingId = openingId;
		this.branchCode = branchCode;
		this.employeeEmail = employeeEmail;
		this.openingOperationDetail = openingOperationDetail;
		this.closingId = closingId;
		this.isActive = isActive;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.closeAttempts = 0;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getOpeningId() {
		return openingId;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public List<OpeningOperationDetailDto> getOpeningOperationDetail() {
		return openingOperationDetail;
	}

	public Long getClosingId() {
		return closingId;
	}

	public void setOpeningId(Long openingId) {
		this.openingId = openingId;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public void setOpeningOperationDetail(List<OpeningOperationDetailDto> openingOperationDetail) {
		this.openingOperationDetail = openingOperationDetail;
	}

	public void setClosingId(Long closingId) {
		this.closingId = closingId;
	}

	public int getCloseAttempts() {
		return closeAttempts;
	}

	public void setCloseAttempts(int closeAttempts) {
		this.closeAttempts = closeAttempts;
	}

	@Override
	public String toString() {
		return "OpeningOperationDto [openingId=" + openingId + ", branchCode=" + branchCode + ", employeeEmail="
				+ employeeEmail + ", openingOperationDetail=" + openingOperationDetail + ", closingId=" + closingId
				+ ", isActive=" + isActive + ", totalAmount=" + totalAmount + ", creationDate=" + creationDate
				+ ", closeAttempts=" + closeAttempts + "]";
	}

}
