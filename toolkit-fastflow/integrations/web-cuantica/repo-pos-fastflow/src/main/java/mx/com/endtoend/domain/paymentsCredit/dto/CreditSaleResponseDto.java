package mx.com.endtoend.domain.paymentsCredit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class CreditSaleResponseDto {

	@NotNull(message = "El número de orden es obligatorio")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderNumber;

	private String orderCode;

	private String code;

	private String name;

	private String transaction_id;

	@NotNull(message = "El total de puntos es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de puntos debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal puntos_total;

	private String type;

	private Date creationDate;

	public CreditSaleResponseDto() {
		super();
	}

	public CreditSaleResponseDto(BigDecimal orderNumber, String orderCode, String code, String name,
			String transaction_id, BigDecimal puntos_total, String type) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.code = code;
		this.name = name;
		this.transaction_id = transaction_id;
		this.puntos_total = DecimalPrecisionUtils.roundToTwoDecimals(puntos_total);
		this.type = type;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public BigDecimal getPuntos_total() {
		return puntos_total;
	}

	public void setPuntos_total(BigDecimal puntos_total) {
		this.puntos_total = DecimalPrecisionUtils.roundToTwoDecimals(puntos_total);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "CreditSaleResponseDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", code=" + code
				+ ", name=" + name + ", transaction_id=" + transaction_id + ", puntos_total=" + puntos_total + ", type="
				+ type + ", creationDate=" + creationDate + "]";
	}

}
