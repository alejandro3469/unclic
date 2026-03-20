package mx.com.endtoend.domain.reports.sales.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class SummaryArticleSaleDto {

	private Long userId;

	private String priceType;

	private Date creationDate;

	private String orderCode;

	private BigDecimal orderNumber;

	private Long clientNumber;

	private String descriptionOne;

	private String descriptionTwo;

	private String alternativeDescription;

	private String articleCode;

	@NotNull(message = "La cantidad solicitada es obligatoria")
	@DecimalMin(value = "0.0", message = "La cantidad solicitada debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal requestAmount;

	@NotNull(message = "El precio unitario final es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio unitario final debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal finalUnitPrice;

	@NotNull(message = "El subtotal es obligatorio")
	@DecimalMin(value = "0.0", message = "El subtotal debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal subTotal;

	@NotNull(message = "El subtotal con impuestos es obligatorio")
	@DecimalMin(value = "0.0", message = "El subtotal con impuestos debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal subTotalTax;

	@NotNull(message = "El IEPS es obligatorio")
	@DecimalMin(value = "0.0", message = "El IEPS debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal ieps;

	private String unitMeasurement;

	private String statusOrder;

	public SummaryArticleSaleDto(Long userId, String priceType, Date creationDate, String orderCode,
			BigDecimal orderNumber, Long clientNumber, String descriptionOne, String descriptionTwo,
			String alternativeDescription, String articleCode, BigDecimal requestAmount, BigDecimal finalUnitPrice,
			BigDecimal subTotal, BigDecimal subTotalTax, BigDecimal ieps, String unitMeasurement, String statusOrder) {
		super();
		this.userId = userId;
		this.priceType = priceType;
		this.creationDate = creationDate;
		this.orderCode = orderCode;
		this.orderNumber = orderNumber;
		this.clientNumber = clientNumber;
		this.descriptionOne = descriptionOne;
		this.descriptionTwo = descriptionTwo;
		this.alternativeDescription = alternativeDescription;
		this.articleCode = articleCode;
		this.requestAmount = DecimalPrecisionUtils.roundToTwoDecimals(requestAmount);
		this.finalUnitPrice = DecimalPrecisionUtils.roundToTwoDecimals(finalUnitPrice);
		this.subTotal = DecimalPrecisionUtils.roundToTwoDecimals(subTotal);
		this.subTotalTax = DecimalPrecisionUtils.roundToTwoDecimals(subTotalTax);
		this.ieps = DecimalPrecisionUtils.roundToTwoDecimals(ieps);
		this.unitMeasurement = unitMeasurement;
		this.statusOrder = statusOrder;
	}

	public Long getUserId() {
		return userId;
	}

	public String getPriceType() {
		return priceType;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public String getDescriptionOne() {
		return descriptionOne;
	}

	public String getDescriptionTwo() {
		return descriptionTwo;
	}

	public String getAlternativeDescription() {
		return alternativeDescription;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public BigDecimal getRequestAmount() {
		return requestAmount;
	}

	public BigDecimal getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public BigDecimal getSubTotalTax() {
		return subTotalTax;
	}

	public BigDecimal getIeps() {
		return ieps;
	}

	public String getUnitMeasurement() {
		return unitMeasurement;
	}

	public String getStatusOrder() {
		return statusOrder;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setDescriptionOne(String descriptionOne) {
		this.descriptionOne = descriptionOne;
	}

	public void setDescriptionTwo(String descriptionTwo) {
		this.descriptionTwo = descriptionTwo;
	}

	public void setAlternativeDescription(String alternativeDescription) {
		this.alternativeDescription = alternativeDescription;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setRequestAmount(BigDecimal requestAmount) {
		this.requestAmount = DecimalPrecisionUtils.roundToTwoDecimals(requestAmount);
	}

	public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
		this.finalUnitPrice = DecimalPrecisionUtils.roundToTwoDecimals(finalUnitPrice);
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = DecimalPrecisionUtils.roundToTwoDecimals(subTotal);
	}

	public void setSubTotalTax(BigDecimal subTotalTax) {
		this.subTotalTax = DecimalPrecisionUtils.roundToTwoDecimals(subTotalTax);
	}

	public void setIeps(BigDecimal ieps) {
		this.ieps = DecimalPrecisionUtils.roundToTwoDecimals(ieps);
	}

	public void setUnitMeasurement(String unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public void setStatusOrder(String statusOrder) {
		this.statusOrder = statusOrder;
	}

	@Override
	public String toString() {
		return "SummaryArticleSaleDto [userId=" + userId + ", priceType=" + priceType + ", creationDate=" + creationDate
				+ ", orderCode=" + orderCode + ", orderNumber=" + orderNumber + ", clientNumber=" + clientNumber
				+ ", descriptionOne=" + descriptionOne + ", descriptionTwo=" + descriptionTwo
				+ ", alternativeDescription=" + alternativeDescription + ", articleCode=" + articleCode
				+ ", requestAmount=" + requestAmount + ", finalUnitPrice=" + finalUnitPrice + ", subTotal=" + subTotal
				+ ", subTotalTax=" + subTotalTax + ", ieps=" + ieps + ", unitMeasurement=" + unitMeasurement
				+ ", statusOrder=" + statusOrder + "]";
	}

}
