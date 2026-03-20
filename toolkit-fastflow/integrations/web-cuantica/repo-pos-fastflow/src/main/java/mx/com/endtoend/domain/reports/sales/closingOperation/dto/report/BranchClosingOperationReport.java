package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;

public class BranchClosingOperationReport {

	private String branchName;

	private String branchCode;

	private String dateFrom;

	private String dateTo;

	private String generateDate;

	private List<BranchClosingOperationSummary> closingReportDetail;

	@NotNull(message = "El total de la sucursal es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la sucursal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal branchTotal;

	public BranchClosingOperationReport(BranchDto branchDto, ClosingOperationReportParamsDto closingReportParamsDto,
			List<BranchClosingOperationSummary> closingReportDetail) {

		this.branchName = branchDto != null ? branchDto.getName() : "";
		this.branchCode = branchDto != null ? branchDto.getCode() : closingReportParamsDto.getBranchCode();
		this.dateFrom = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(closingReportParamsDto.getStartDate());
		this.dateTo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(closingReportParamsDto.getEndDate());
		this.generateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.closingReportDetail = closingReportDetail;

		BigDecimal total = BigDecimal.ZERO;

		for (BranchClosingOperationSummary branchClosingOperationSummary : closingReportDetail) {
			total = total.add(DecimalPrecisionUtils.roundToTwoDecimals(branchClosingOperationSummary.getAmountTotal()));
		}

		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(total);
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public List<BranchClosingOperationSummary> getClosingReportDetail() {
		return closingReportDetail;
	}

	public void setClosingReportDetail(List<BranchClosingOperationSummary> closingReportDetail) {
		this.closingReportDetail = closingReportDetail;
	}

	public BigDecimal getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(BigDecimal branchTotal) {
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
	}

	@Override
	public String toString() {
		return "BranchClosingOperationReport [branchName=" + branchName + ", branchCode=" + branchCode + ", dateFrom="
				+ dateFrom + ", dateTo=" + dateTo + ", generateDate=" + generateDate + ", closingReportDetail="
				+ closingReportDetail + ", branchTotal=" + branchTotal + "]";
	}

}
