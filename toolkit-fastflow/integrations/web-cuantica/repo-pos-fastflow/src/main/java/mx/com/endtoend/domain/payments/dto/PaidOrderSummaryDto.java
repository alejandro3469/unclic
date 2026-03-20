package mx.com.endtoend.domain.payments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class PaidOrderSummaryDto {

	@NotNull(message = "El número de orden es obligatorio")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderNumber;

	private String orderCode;

	private String branchCode;

	private String currency;

	@NotNull(message = "El total de IVA es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de IVA debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal ivaTotal;

	@NotNull(message = "El subtotal es obligatorio")
	@DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal subTotal;

	@NotNull(message = "El total de la orden es obligatorio")
	@DecimalMin(value = "0.0", message = "El total de la orden debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderTotal;

	@NotNull(message = "El pago pendiente es obligatorio")
	@DecimalMin(value = "0.0", message = "El pago pendiente debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal pendingPayment;

	private Date paymentDate;

	private String state;

	private String stateDescription;

	private Long userNumber;

	public PaidOrderSummaryDto() {
		super();
	}

	public PaidOrderSummaryDto(BigDecimal orderNumber, String orderCode, String branchCode, String currency,
			BigDecimal ivaTotal, BigDecimal subTotal, BigDecimal orderTotal, BigDecimal pendingPayment, Date paymentDate, String state,
			String stateDescription, Long userNumber) {
		super();
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.branchCode = branchCode;
		this.currency = currency;
		this.ivaTotal = ivaTotal;
		this.subTotal = subTotal;
		this.orderTotal = orderTotal;
		this.pendingPayment = pendingPayment;
		this.paymentDate = paymentDate;
		this.state = state;
		this.stateDescription = stateDescription;
		this.userNumber = userNumber;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getIvaTotal() {
		return ivaTotal;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public BigDecimal getPendingPayment() {
		return pendingPayment;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getState() {
		return state;
	}

	public String getStateDescription() {
		return stateDescription;
	}

	public Long getUserNumber() {
		return userNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = DecimalPrecisionUtils.roundToTwoDecimals(orderNumber);
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setIvaTotal(BigDecimal ivaTotal) {
		this.ivaTotal = DecimalPrecisionUtils.roundToTwoDecimals(ivaTotal);
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = DecimalPrecisionUtils.roundToTwoDecimals(subTotal);
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderTotal);
	}

	public void setPendingPayment(BigDecimal pendingPayment) {
		this.pendingPayment = DecimalPrecisionUtils.roundToTwoDecimals(pendingPayment);
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
	}

	@Override
	public String toString() {
		return "PaidOrderSummaryDto [orderNumber=" + orderNumber + ", orderCode=" + orderCode + ", branchCode="
				+ branchCode + ", currency=" + currency + ", ivaTotal=" + ivaTotal + ", subTotal=" + subTotal
				+ ", orderTotal=" + orderTotal + ", pendingPayment=" + pendingPayment + ", paymentDate=" + paymentDate
				+ ", state=" + state + ", stateDescription=" + stateDescription + ", userNumber=" + userNumber + "]";
	}

}