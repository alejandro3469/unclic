package mx.com.endtoend.domain.openings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class OpeningOperationDetailDto {

	private Long id;

	private Long openingId;

	private OpenPaymentInstrumentDto openPaymentInstrument;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	public OpeningOperationDetailDto() {
		super();
	}

	public OpeningOperationDetailDto(Long id, Long openingId, OpenPaymentInstrumentDto openPaymentInstrument,
			BigDecimal amount) {
		super();
		this.id = id;
		this.openingId = openingId;
		this.openPaymentInstrument = openPaymentInstrument;
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public Long getId() {
		return id;
	}

	public Long getOpeningId() {
		return openingId;
	}

	public OpenPaymentInstrumentDto getOpenPaymentInstrument() {
		return openPaymentInstrument;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOpeningId(Long openingId) {
		this.openingId = openingId;
	}

	public void setOpenPaymentInstrument(OpenPaymentInstrumentDto openPaymentInstrument) {
		this.openPaymentInstrument = openPaymentInstrument;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	@Override
	public String toString() {
		return "OpeningOperationDetailDto [id=" + id + ", openingId=" + openingId + ", openPaymentInstrument="
				+ openPaymentInstrument + ", amount=" + amount + "]";
	}

}
