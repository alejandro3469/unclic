package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreditSaleRequestDto {

	@NotNull(message = "El número de orden es obligatorio")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderNumber;

	private String orderCode;

	private String token;

	@NotNull(message = "El total de venta es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de venta debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal venta_total;

	private List<CreditSaleDetailDto> detail;

	private Date creationDate;

	public CreditSaleRequestDto() {
		super();
	}

	public CreditSaleRequestDto(BigDecimal orderNumber, String orderCode, String token, BigDecimal venta_total,
			List<CreditSaleDetailDto> detail) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.token = token;
		this.venta_total = DecimalPrecisionUtils.roundToTwoDecimals(venta_total);
		this.detail = detail;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BigDecimal getVenta_total() {
		return venta_total;
	}

	public void setVenta_total(BigDecimal venta_total) {
		this.venta_total = DecimalPrecisionUtils.roundToTwoDecimals(venta_total);
	}

	public List<CreditSaleDetailDto> getDetail() {
		return detail;
	}

	public void setDetail(List<CreditSaleDetailDto> detail) {
		this.detail = detail;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "CreditSaleRequestDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", token=" + token
				+ ", venta_total=" + venta_total + ", detail=" + detail + ", creationDate=" + creationDate + "]";
	}

}
