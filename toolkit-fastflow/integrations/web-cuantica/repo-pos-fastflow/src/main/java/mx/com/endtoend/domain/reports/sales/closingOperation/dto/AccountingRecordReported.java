package mx.com.endtoend.domain.reports.sales.closingOperation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountingRecordReported {

	@NotNull(message = "El monto reportado es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto reportado debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountReported;

	private String movementType;

	public AccountingRecordReported(BigDecimal amountReported, String movementType) {
		super();
		this.amountReported = DecimalPrecisionUtils.roundToTwoDecimals(amountReported);
		this.movementType = movementType;
	}

	public BigDecimal getAmountReported() {
		return amountReported;
	}

	public void setAmountReported(BigDecimal amountReported) {
		this.amountReported = DecimalPrecisionUtils.roundToTwoDecimals(amountReported);
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	@Override
	public String toString() {
		return "AccountingRecordReported [amountReported=" + amountReported + ", movementType=" + movementType + "]";
	}

}
