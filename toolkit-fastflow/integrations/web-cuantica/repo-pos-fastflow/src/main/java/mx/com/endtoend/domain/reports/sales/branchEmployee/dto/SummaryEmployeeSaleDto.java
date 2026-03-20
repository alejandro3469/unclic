package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class SummaryEmployeeSaleDto {

	private String employeeName;

	private Long ticketAmount;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private List<SummarySaleBranchEmployeeDto> summaryDetail;

	public SummaryEmployeeSaleDto(String employeeName, List<SummarySaleBranchEmployeeDto> summaryDetail) {

		this.employeeName = employeeName;
		this.summaryDetail = summaryDetail;
		this.ticketAmount = (long) summaryDetail.size();
		this.totalAmount = obtainTotalAmount(summaryDetail);
	}

	private BigDecimal obtainTotalAmount(List<SummarySaleBranchEmployeeDto> summaryDetail) {
		BigDecimal sum = BigDecimal.ZERO;
		for (SummarySaleBranchEmployeeDto summarySaleBranchEmployee : summaryDetail) {
			sum = sum.add(DecimalPrecisionUtils.roundToTwoDecimals(summarySaleBranchEmployee.getTotalAmountReceived()));
		}
		return DecimalPrecisionUtils.roundToTwoDecimals(sum);
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public Long getTicketAmount() {
		return ticketAmount;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public List<SummarySaleBranchEmployeeDto> getSummaryDetail() {
		return summaryDetail;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setTicketAmount(Long ticketAmount) {
		this.ticketAmount = ticketAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setSummaryDetail(List<SummarySaleBranchEmployeeDto> summaryDetail) {
		this.summaryDetail = summaryDetail;
	}

	@Override
	public String toString() {
		return "SummaryEmployeeSaleDto [employeeName=" + employeeName + ", ticketAmount=" + ticketAmount
				+ ", totalAmount=" + totalAmount + ", summaryDetail=" + summaryDetail + "]";
	}

}
