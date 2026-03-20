package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;

public class GeneralClosingOperationReport {

	private String dateFrom;

	private String dateTo;

	private String generateDate;

	private List<GenralClosingOperationSummary> closingOperationSummary;

	@NotNull(message = "El monto general es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto general debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal generalAmount;

	@NotNull(message = "La diferencia general es obligatoria")
	@DecimalMin(value = "0.0", message = "La diferencia general debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal generalDifference;

	public GeneralClosingOperationReport(ClosingOperationReportParamsDto closingReportParamsDto,
			List<GenralClosingOperationSummary> closingOperationSummary) {
		this.dateFrom = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(closingReportParamsDto.getStartDate());
		this.dateTo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(closingReportParamsDto.getEndDate());
		this.generateDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		this.closingOperationSummary = closingOperationSummary;

		BigDecimal generalAmount = BigDecimal.ZERO;
		BigDecimal generalDifference = BigDecimal.ZERO;
		BigDecimal generalTotalCreditNote = BigDecimal.ZERO;

		for (GenralClosingOperationSummary genralClosingOperationSummary : closingOperationSummary) {
			generalAmount = generalAmount.add(DecimalPrecisionUtils.roundToTwoDecimals(genralClosingOperationSummary.getBranchTotal()));
			generalTotalCreditNote = generalTotalCreditNote.add(DecimalPrecisionUtils.roundToTwoDecimals(genralClosingOperationSummary.getCreditNoteTotal()));
		}

		generalDifference = generalAmount.subtract(generalTotalCreditNote);

		this.generalAmount = DecimalPrecisionUtils.roundToTwoDecimals(generalAmount);
		this.generalDifference = DecimalPrecisionUtils.roundToTwoDecimals(generalDifference);
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

	public List<GenralClosingOperationSummary> getClosingOperationSummary() {
		return closingOperationSummary;
	}

	public void setClosingOperationSummary(List<GenralClosingOperationSummary> closingOperationSummary) {
		this.closingOperationSummary = closingOperationSummary;
	}

	public BigDecimal getGeneralAmount() {
		return generalAmount;
	}

	public void setGeneralAmount(BigDecimal generalAmount) {
		this.generalAmount = DecimalPrecisionUtils.roundToTwoDecimals(generalAmount);
	}

	public BigDecimal getGeneralDifference() {
		return generalDifference;
	}

	public void setGeneralDifference(BigDecimal generalDifference) {
		this.generalDifference = DecimalPrecisionUtils.roundToTwoDecimals(generalDifference);
	}

	@Override
	public String toString() {
		return "GeneralClosingOperationReport [dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", generateDate="
				+ generateDate + ", closingOperationSummary=" + closingOperationSummary + ", generalAmount="
				+ generalAmount + ", generalDifference=" + generalDifference + "]";
	}

}
