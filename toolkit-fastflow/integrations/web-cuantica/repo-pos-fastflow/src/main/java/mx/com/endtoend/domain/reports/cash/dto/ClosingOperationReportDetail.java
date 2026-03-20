package mx.com.endtoend.domain.reports.cash.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import mx.com.endtoend.domain.commons.constants.ClosingIncomeEnum;

public class ClosingOperationReportDetail {

	private String incomeType;

	private String currency;

	@NotNull(message = "El monto teórico es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto teórico debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal theoreticalAmount;

	@NotNull(message = "El monto físico es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto físico debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal physicalAmount;

	@NotNull(message = "Las diferencias son obligatorias")
	@DecimalMin(value = "0.0", message = "Las diferencias deben ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal differences;

	public ClosingOperationReportDetail() {
		super();
	}

	public ClosingOperationReportDetail(String incomeType, String currency, BigDecimal theoreticalAmount,
			BigDecimal physicalAmount, BigDecimal differences) {
		super();
		this.incomeType = getIncomeTypeTranslate(incomeType);
		this.currency = currency;
		this.theoreticalAmount = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalAmount);
		this.physicalAmount = DecimalPrecisionUtils.roundToTwoDecimals(physicalAmount);
		this.differences = DecimalPrecisionUtils.roundToTwoDecimals(differences);
	}

	private String getIncomeTypeTranslate(String incomeType) {

		ClosingIncomeEnum value = ClosingIncomeEnum.valueOf(incomeType);
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

		case CREDIT_CARD:
			translate = "TARJETAS BANCARIAS";
			break;

		case TRANSFER:
			translate = "TARNSFERENCIAS";
			break;

		case CREDIT:
			translate = "CREDITOS";
			break;

		default:
			translate = "---";
			break;
		}

		return translate;
	}

	public String getIncomeType() {
		return incomeType;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getTheoreticalAmount() {
		return theoreticalAmount;
	}

	public BigDecimal getPhysicalAmount() {
		return physicalAmount;
	}

	public BigDecimal getDifferences() {
		return differences;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setTheoreticalAmount(BigDecimal theoreticalAmount) {
		this.theoreticalAmount = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalAmount);
	}

	public void setPhysicalAmount(BigDecimal physicalAmount) {
		this.physicalAmount = DecimalPrecisionUtils.roundToTwoDecimals(physicalAmount);
	}

	public void setDifferences(BigDecimal differences) {
		this.differences = DecimalPrecisionUtils.roundToTwoDecimals(differences);
	}

	@Override
	public String toString() {
		return "ClosingOperationReportDetail [incomeType=" + incomeType + ", currency=" + currency
				+ ", theoreticalAmount=" + theoreticalAmount + ", physicalAmount=" + physicalAmount + ", differences="
				+ differences + "]";
	}

}
