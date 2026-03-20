package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReportBranchEmployeeDto {

	private String dateFrom;

	private String dateTo;

	private String generateDate;

	private Long totalTicket;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	private List<SaleBranchEmployeeDto> saleBranchDetail;

	public ReportBranchEmployeeDto(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			List<SaleBranchEmployeeDto> saleBranchDetail) {

		this.dateFrom = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
				.format(reportBranchEmployeeParamsDto.getStartDate());
		this.dateTo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(reportBranchEmployeeParamsDto.getEndDate());
		this.generateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.saleBranchDetail = saleBranchDetail;
		this.totalTicket = getTotalTicket(saleBranchDetail);
		this.totalAmount = getTotalAmountByList(saleBranchDetail);
	}

	private Long getTotalTicket(List<SaleBranchEmployeeDto> saleBranchDetail) {
		Long totalTicket = 0L;
		for (SaleBranchEmployeeDto saleBranchEmployeeDto : saleBranchDetail) {
			if (saleBranchEmployeeDto.getEmployeeDetail() != null) {
				for (SummaryEmployeeSaleDto summaryEmployeeSaleDto : saleBranchEmployeeDto.getEmployeeDetail()) {
					totalTicket += summaryEmployeeSaleDto.getTicketAmount();
				}
			}

		}
		return totalTicket;
	}

	private BigDecimal getTotalAmountByList(List<SaleBranchEmployeeDto> saleBranchDetail) {
		BigDecimal amount = BigDecimal.ZERO;
		for (SaleBranchEmployeeDto saleBranchEmployeeDto : saleBranchDetail) {
			if (saleBranchEmployeeDto.getEmployeeDetail() != null) {
				for (SummaryEmployeeSaleDto summaryEmployeeSale : saleBranchEmployeeDto.getEmployeeDetail()) {
					amount = amount.add(DecimalPrecisionUtils.roundToTwoDecimals(summaryEmployeeSale.getTotalAmount()));
				}
			}
		}

		return DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}

	public Long getTotalTicket() {
		return totalTicket;
	}

	public void setTotalTicket(Long totalTicket) {
		this.totalTicket = totalTicket;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public List<SaleBranchEmployeeDto> getSaleBranchDetail() {
		return saleBranchDetail;
	}

	public void setSaleBranchDetail(List<SaleBranchEmployeeDto> saleBranchDetail) {
		this.saleBranchDetail = saleBranchDetail;
	}

	@Override
	public String toString() {
		return "ReportBranchEmployeeDto [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", generateDate=" + generateDate
				+ ", totalTicket=" + totalTicket + ", totalAmount=" + totalAmount + ", saleBranchDetail="
				+ saleBranchDetail + "]";
	}

}
