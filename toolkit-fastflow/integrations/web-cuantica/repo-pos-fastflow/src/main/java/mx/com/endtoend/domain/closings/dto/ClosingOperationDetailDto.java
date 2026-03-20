package mx.com.endtoend.domain.closings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ClosingOperationDetailDto {

	private Long id;

	private Long closingId;

	private ClosePaymentInstrumentDto closePaymentInstrument;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	public ClosingOperationDetailDto() {
		super();
	}

	public ClosingOperationDetailDto(Long id, Long closingId, ClosePaymentInstrumentDto closePaymentInstrument,
			BigDecimal amount) {
		super();
		this.id = id;
		this.closingId = closingId;
		this.closePaymentInstrument = closePaymentInstrument;
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public Long getId() {
		return id;
	}

	public Long getClosingId() {
		return closingId;
	}

	public ClosePaymentInstrumentDto getClosePaymentInstrument() {
		return closePaymentInstrument;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setClosingId(Long closingId) {
		this.closingId = closingId;
	}

	public void setClosePaymentInstrument(ClosePaymentInstrumentDto closePaymentInstrument) {
		this.closePaymentInstrument = closePaymentInstrument;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	@Override
	public String toString() {
		return "ClosingOperationDetailDto [id=" + id + ", closingId=" + closingId + ", closePaymentInstrument="
				+ closePaymentInstrument + ", amount=" + amount + "]";
	}

}
