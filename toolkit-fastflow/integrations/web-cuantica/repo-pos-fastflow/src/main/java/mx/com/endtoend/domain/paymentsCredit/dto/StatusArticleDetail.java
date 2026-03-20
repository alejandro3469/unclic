package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class StatusArticleDetail {

	private Long detail_id;

	private String product_sku;

	@NotNull(message = "La cantidad es obligatoria")
	@DecimalMin(value = "0.0", message = "La cantidad debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal cantidad;

	@NotNull(message = "El total de operación es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de operación debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal total_operacion;

	private String tr_disposicion_id;

	public StatusArticleDetail() {
		super();
	}

	public StatusArticleDetail(Long detail_id, String product_sku, BigDecimal cantidad, BigDecimal total_operacion,
			String tr_disposicion_id) {
		super();
		this.detail_id = detail_id;
		this.product_sku = product_sku;
		this.cantidad = DecimalPrecisionUtils.roundToTwoDecimals(cantidad);
		this.total_operacion = DecimalPrecisionUtils.roundToTwoDecimals(total_operacion);
		this.tr_disposicion_id = tr_disposicion_id;
	}

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public String getProduct_sku() {
		return product_sku;
	}

	public void setProduct_sku(String product_sku) {
		this.product_sku = product_sku;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = DecimalPrecisionUtils.roundToTwoDecimals(cantidad);
	}

	public BigDecimal getTotal_operacion() {
		return total_operacion;
	}

	public void setTotal_operacion(BigDecimal total_operacion) {
		this.total_operacion = DecimalPrecisionUtils.roundToTwoDecimals(total_operacion);
	}

	public String getTr_disposicion_id() {
		return tr_disposicion_id;
	}

	public void setTr_disposicion_id(String tr_disposicion_id) {
		this.tr_disposicion_id = tr_disposicion_id;
	}

	@Override
	public String toString() {
		return "StatusArticleDetail [detail_id=" + detail_id + ", product_sku=" + product_sku + ", cantidad=" + cantidad
				+ ", total_operacion=" + total_operacion + ", tr_disposicion_id=" + tr_disposicion_id + "]";
	}

}
