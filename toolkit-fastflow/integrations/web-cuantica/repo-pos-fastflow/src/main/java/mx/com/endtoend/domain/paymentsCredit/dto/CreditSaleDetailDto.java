package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreditSaleDetailDto {

	private String product_sku;

	@NotNull(message = "El monto es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amount;

	@NotNull(message = "El total de operación es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de operación debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal total_operacion;

	public CreditSaleDetailDto() {
		super();
	}

	public CreditSaleDetailDto(String product_sku, BigDecimal amount, BigDecimal total_operacion) {
		super();
		this.product_sku = product_sku;
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
		this.total_operacion = DecimalPrecisionUtils.roundToTwoDecimals(total_operacion);
	}

	public String getProduct_sku() {
		return product_sku;
	}

	public void setProduct_sku(String product_sku) {
		this.product_sku = product_sku;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
	}

	public BigDecimal getTotal_operacion() {
		return total_operacion;
	}

	public void setTotal_operacion(BigDecimal total_operacion) {
		this.total_operacion = DecimalPrecisionUtils.roundToTwoDecimals(total_operacion);
	}

	@Override
	public String toString() {
		return "CreditSaleDetailDto [product_sku=" + product_sku + ", amount=" + amount + ", total_operacion="
				+ total_operacion + "]";
	}

}
