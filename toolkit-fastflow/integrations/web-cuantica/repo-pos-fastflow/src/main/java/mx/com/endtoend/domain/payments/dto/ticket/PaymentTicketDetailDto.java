package mx.com.endtoend.domain.payments.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PaymentTicketDetailDto {

	private static final String SKU_COMISSION = "COM0009-COMISIÓN BANCARIA";

	private String article;

	private String um;

	@NotNull(message = "La cantidad es obligatoria")
	@DecimalMin(value = "0.0", message = "La cantidad debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal quantity;

	@NotNull(message = "El subtotal es obligatorio")
	@DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal subtotal;

	@NotNull(message = "El precio unitario con impuesto es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio unitario con impuesto debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal unitPriceTax;

	public PaymentTicketDetailDto() {
		super();
	}

	public PaymentTicketDetailDto(OrderDetailDto orderDetailDto) {
		super();
		this.article = orderDetailDto.getArticleCode().trim() + " " + orderDetailDto.getDescriptionOne().trim()
				+ " " + orderDetailDto.getDescriptionTwo().trim() + " " + orderDetailDto.getAlternateDescription().trim();
		this.um = orderDetailDto.getUnitMeasurement();
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getRequestAmount());
		this.subtotal = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getSubTotalTax());
		this.unitPriceTax = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getUnitPriceTax());
	}

	public PaymentTicketDetailDto(BigDecimal commision) {
		super();
		this.article = SKU_COMISSION;
		this.um = "";
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.ONE);
		this.subtotal = DecimalPrecisionUtils.roundToTwoDecimals(commision);
		this.unitPriceTax = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.ZERO);
	}

	public PaymentTicketDetailDto(String article, String um, BigDecimal quantity, BigDecimal subtotal, BigDecimal unitPriceTax) {
		super();
		this.article = article;
		this.um = um;
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(quantity);
		this.subtotal = DecimalPrecisionUtils.roundToTwoDecimals(subtotal);
		this.unitPriceTax = DecimalPrecisionUtils.roundToTwoDecimals(unitPriceTax);
	}

	public String getArticle() {
		return article;
	}

	public String getUm() {
		return um;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public BigDecimal getUnitPriceTax() {
		return unitPriceTax;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(quantity);
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = DecimalPrecisionUtils.roundToTwoDecimals(subtotal);
	}

	public void setUnitPriceTax(BigDecimal unitPriceTax) {
		this.unitPriceTax = DecimalPrecisionUtils.roundToTwoDecimals(unitPriceTax);
	}

	@Override
	public String toString() {
		return "PaymentTicketDetailDto [article=" + article + ", um=" + um + ", quantity=" + quantity + ", subtotal="
				+ subtotal + ", unitPriceTax=" + unitPriceTax + "]";
	}

}