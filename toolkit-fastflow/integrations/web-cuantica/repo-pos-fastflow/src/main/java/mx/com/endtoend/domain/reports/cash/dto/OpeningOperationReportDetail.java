package mx.com.endtoend.domain.reports.cash.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import mx.com.endtoend.domain.commons.constants.OpeningIncomeEnum;

public class OpeningOperationReportDetail {

	private Long lineNumber;

	private String instrumentName;

	private String incomeType;

	@NotNull(message = "El monto aplicado es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto aplicado debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountApplied;

	public OpeningOperationReportDetail() {
		super();
	}

	public OpeningOperationReportDetail(Long lineNumber, String instrumentName, String incomeType,
			BigDecimal amountApplied) {
		super();
		this.lineNumber = lineNumber;
		this.instrumentName = instrumentName;
		this.incomeType = getIncomeTypeTranslate(incomeType);
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
	}

	private String getIncomeTypeTranslate(String incomeType) {

		OpeningIncomeEnum value = OpeningIncomeEnum.valueOf(incomeType);
		String translate = "";
		switch (value) {
		case CASH:
			translate = "EFECTIVO";
			break;
		case CHECK:
			translate = "CHEQUE";
			break;

		case CREDIT_NOTE:
			translate = "NOTA DE CRÉDITO";
			break;

		default:
			translate = "---";
			break;
		}

		return translate;
	}

	public Long getLineNumber() {
		return lineNumber;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public BigDecimal getAmountApplied() {
		return amountApplied;
	}

	public void setLineNumber(Long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
	}

}
