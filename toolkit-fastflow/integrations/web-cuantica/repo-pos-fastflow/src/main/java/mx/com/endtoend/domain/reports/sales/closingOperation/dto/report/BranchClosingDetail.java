package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.com.endtoend.domain.commons.constants.MovementPaymentTypeEnum;

public class BranchClosingDetail {

	private String currency;

	private String closingDate;

	private String closingTime;

	private String movementType;

	@NotNull(message = "El monto teórico es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto teórico debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal theoreticalAmount;

	@NotNull(message = "El monto de cierre es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto de cierre debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal closingAmount;

	@NotNull(message = "El monto de diferencia es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto de diferencia debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal differenceAmount;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountTotal;

	public BranchClosingDetail() {
		super();
	}

	public BranchClosingDetail(String currency, Date closingFullDate, String movementType, BigDecimal theoreticalAmount,
			BigDecimal closingAmount) {

		this.currency = currency;
		this.closingDate = new SimpleDateFormat("dd/MM/yyyy").format(closingFullDate);
		this.closingTime = new SimpleDateFormat("HH:mm:ss").format(closingFullDate);
		this.movementType = getMoventTypeDescription(movementType);
		this.theoreticalAmount = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalAmount);
		this.closingAmount = DecimalPrecisionUtils.roundToTwoDecimals(closingAmount);

		BigDecimal finalDifferenceAmount = theoreticalAmount.subtract(closingAmount);
		this.differenceAmount = DecimalPrecisionUtils.roundToTwoDecimals(finalDifferenceAmount);

		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(closingAmount);
	}

	private String getMoventTypeDescription(String movementTye) {
		MovementPaymentTypeEnum key = MovementPaymentTypeEnum.valueOf(movementTye);
		switch (key) {
		case CASH:
			return "EFECTIVO";
		case CHECK:
			return "CHEQUE";
		case CREDIT:
			return "CRÉDITO";
		case CREDIT_CARD:
			return "TARJETA DE CRÉDITO";
		case CREDIT_NOTE:
			return "NOTA DE CRÉDITO";
		case TRANSFER:
			return "TRANSFERENCIA";
		default:
			return "--";
		}
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public BigDecimal getTheoreticalAmount() {
		return theoreticalAmount;
	}

	public void setTheoreticalAmount(BigDecimal theoreticalAmount) {
		this.theoreticalAmount = DecimalPrecisionUtils.roundToTwoDecimals(theoreticalAmount);
	}

	public BigDecimal getClosingAmount() {
		return closingAmount;
	}

	public void setClosingAmount(BigDecimal closingAmount) {
		this.closingAmount = DecimalPrecisionUtils.roundToTwoDecimals(closingAmount);
	}

	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = DecimalPrecisionUtils.roundToTwoDecimals(differenceAmount);
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	@Override
	public String toString() {
		return "BranchClosingDetail [currency=" + currency + ", closingDate=" + closingDate + ", closingTime="
				+ closingTime + ", movementType=" + movementType + ", theoreticalAmount=" + theoreticalAmount
				+ ", closingAmount=" + closingAmount + ", differenceAmount=" + differenceAmount + ", amountTotal="
				+ amountTotal + "]";
	}

}
