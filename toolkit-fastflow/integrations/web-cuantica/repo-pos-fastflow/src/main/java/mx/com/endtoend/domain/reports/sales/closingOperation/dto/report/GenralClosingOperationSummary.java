package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.commons.constants.MovementPaymentTypeEnum;

public class GenralClosingOperationSummary {

	private String branchName;

	private String branchCode;

	private List<GeneralClosingDetail> closingReportDetail;

	@NotNull(message = "El total de la sucursal es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la sucursal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal branchTotal;

	@NotNull(message = "El total de notas de crédito es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de notas de crédito debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal creditNoteTotal;

	@NotNull(message = "El total de diferencia es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de diferencia debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal differenceTotal;

	public GenralClosingOperationSummary(BranchDto branch, List<GeneralClosingDetail> closingReportDetail) {

		this.branchName = branch != null ? branch.getName() : "";
		this.branchCode = branch != null ? branch.getCode() : "---";
		this.closingReportDetail = closingReportDetail;
		
		BigDecimal branchTotal = BigDecimal.ZERO;
		BigDecimal creditNoteTotal = BigDecimal.ZERO;
		BigDecimal differenceTotal = BigDecimal.ZERO;
		
		for (GeneralClosingDetail generalClosingDetail : closingReportDetail) {
			branchTotal = branchTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(generalClosingDetail.getAmountTotal()));
			if(generalClosingDetail.getMovementType().equalsIgnoreCase(MovementPaymentTypeEnum.CREDIT_NOTE.toString())) {
				creditNoteTotal = creditNoteTotal.add(DecimalPrecisionUtils.roundToTwoDecimals(generalClosingDetail.getAmountTotal()));
			}
		}
		
		differenceTotal = branchTotal.subtract(creditNoteTotal);
		
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
		this.creditNoteTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteTotal);
		this.differenceTotal = DecimalPrecisionUtils.roundToTwoDecimals(differenceTotal);
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

	public List<GeneralClosingDetail> getClosingReportDetail() {
		return closingReportDetail;
	}

	public void setClosingReportDetail(List<GeneralClosingDetail> closingReportDetail) {
		this.closingReportDetail = closingReportDetail;
	}

	public BigDecimal getBranchTotal() {
		return branchTotal;
	}

	public void setBranchTotal(BigDecimal branchTotal) {
		this.branchTotal = DecimalPrecisionUtils.roundToTwoDecimals(branchTotal);
	}

	public BigDecimal getCreditNoteTotal() {
		return creditNoteTotal;
	}

	public void setCreditNoteTotal(BigDecimal creditNoteTotal) {
		this.creditNoteTotal = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteTotal);
	}

	public BigDecimal getDifferenceTotal() {
		return differenceTotal;
	}

	public void setDifferenceTotal(BigDecimal differenceTotal) {
		this.differenceTotal = DecimalPrecisionUtils.roundToTwoDecimals(differenceTotal);
	}

	@Override
	public String toString() {
		return "GenralClosingOperationSummary [branchName=" + branchName + ", branchCode=" + branchCode
				+ ", closingReportDetail=" + closingReportDetail + ", branchTotal=" + branchTotal + ", creditNoteTotal="
				+ creditNoteTotal + ", differenceTotal=" + differenceTotal + "]";
	}

}
