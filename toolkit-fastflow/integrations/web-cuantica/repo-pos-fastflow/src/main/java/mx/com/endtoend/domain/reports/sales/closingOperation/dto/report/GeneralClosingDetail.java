package mx.com.endtoend.domain.reports.sales.closingOperation.dto.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class GeneralClosingDetail {

	private String movementType;

	private Long ticketTotal;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountTotal;

	public GeneralClosingDetail() {
		super();
	}

	public GeneralClosingDetail(String movementType, Long ticketTotal, BigDecimal amountTotal) {
		super();
		this.movementType = movementType;
		this.ticketTotal = ticketTotal;
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}

	public Long getTicketTotal() {
		return ticketTotal;
	}

	public void setTicketTotal(Long ticketTotal) {
		this.ticketTotal = ticketTotal;
	}

	public BigDecimal getAmountTotal() {
		return amountTotal;
	}

	public void setAmountTotal(BigDecimal amountTotal) {
		this.amountTotal = DecimalPrecisionUtils.roundToTwoDecimals(amountTotal);
	}

	@Override
	public String toString() {
		return "GeneralClosingDetail [movementType=" + movementType + ", ticketTotal=" + ticketTotal + ", amountTotal="
				+ amountTotal + "]";
	}

}
