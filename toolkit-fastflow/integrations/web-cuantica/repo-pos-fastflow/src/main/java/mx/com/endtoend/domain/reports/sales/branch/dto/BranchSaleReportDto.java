package mx.com.endtoend.domain.reports.sales.branch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BranchSaleReportDto {

	private String dateFrom;

	private String dateTo;

	private String generateReportDate;

	private List<BranchSaleSummaryDto> branchSaleSummaryList;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountTotal;

	public BranchSaleReportDto(GenericSearchSaleReportParamsDto saleReportParamsDto,
			List<BranchSaleSummaryDto> branchSaleSummaryList) {

		this.dateFrom = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(saleReportParamsDto.getStartDate());
		this.dateTo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(saleReportParamsDto.getEndDate());
		this.generateReportDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.branchSaleSummaryList = branchSaleSummaryList;
		
		BigDecimal total = BigDecimal.ZERO;
		
		for (BranchSaleSummaryDto branchSaleSummaryDto : branchSaleSummaryList) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(branchSaleSummaryDto.getBranchTotal()));
		}
		
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(total);
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

	public String getGenerateReportDate() {
		return generateReportDate;
	}

	public void setGenerateReportDate(String generateReportDate) {
		this.generateReportDate = generateReportDate;
	}

	public List<BranchSaleSummaryDto> getBranchSaleSummaryList() {
		return branchSaleSummaryList;
	}

	public void setBranchSaleSummaryList(List<BranchSaleSummaryDto> branchSaleSummaryList) {
		this.branchSaleSummaryList = branchSaleSummaryList;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	@Override
	public String toString() {
		return "BranchSaleReportDto [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", generateReportDate="
				+ generateReportDate + ", branchSaleSummaryList=" + branchSaleSummaryList + ", amountTotal="
				+ amountTotal + "]";
	}

}
