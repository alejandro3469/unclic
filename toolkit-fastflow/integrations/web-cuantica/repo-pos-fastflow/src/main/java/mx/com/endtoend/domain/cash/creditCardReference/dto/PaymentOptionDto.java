package mx.com.endtoend.domain.cash.creditCardReference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentOptionDto {

	private Long id;

	private String period;

	@NotNull(message = "La comisión es obligatoria")
	@DecimalMin(value = "0.0", message = "La comisión debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal commission;

	private boolean isEnable;

	public PaymentOptionDto() {
		super();
	}

	public PaymentOptionDto(Long id, String period, BigDecimal commission, boolean isEnable) {
		super();
		this.id = id;
		this.period = period;
		this.commission = DecimalPrecisionUtils.roundToTwoDecimals(commission);
		this.isEnable = isEnable;
	}

	public Long getId() {
		return id;
	}

	public String getPeriod() {
		return period;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = DecimalPrecisionUtils.roundToTwoDecimals(commission);
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "PaymentOptionDto [id=" + id + ", period=" + period + ", commission=" + commission + ", isEnable="
				+ isEnable + "]";
	}

}
