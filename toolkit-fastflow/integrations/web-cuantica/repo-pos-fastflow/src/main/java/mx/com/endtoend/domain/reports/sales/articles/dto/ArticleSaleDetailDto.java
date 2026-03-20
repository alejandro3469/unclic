package mx.com.endtoend.domain.reports.sales.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.endtoend.domain.users.dto.UserDto;

public class ArticleSaleDetailDto {

	private Date creationDate;

	private String priceType;

	private String orderCode;

	private String orderNumber;

	private Long clientNumber;

	private String articleDescription;

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

	private String empployeeName;

	public ArticleSaleDetailDto(SummaryArticleSaleDto summaryArticle, UserDto userDto) {

		this.creationDate = summaryArticle.getCreationDate();
		this.priceType = summaryArticle.getPriceType();
		this.orderCode = summaryArticle.getOrderCode();
		this.orderNumber = String.valueOf(summaryArticle.getOrderNumber().longValue());
		this.clientNumber = summaryArticle.getClientNumber();
		String description = summaryArticle.getDescriptionOne() == null ? "" : summaryArticle.getDescriptionOne();
		description += summaryArticle.getDescriptionTwo() == null ? "" : summaryArticle.getDescriptionTwo();
		this.articleDescription = description;
		this.alternativeDescription = summaryArticle.getAlternativeDescription();
		this.articleCode = summaryArticle.getArticleCode();
		this.requestAmount = summaryArticle.getRequestAmount();
		this.finalUnitPrice = summaryArticle.getFinalUnitPrice();
		this.subTotal = summaryArticle.getSubTotal();
		this.subTotalTax = summaryArticle.getSubTotalTax();
		BigDecimal iepsTax = summaryArticle.getIeps().compareTo(BigDecimal.valueOf(-1)) == 0 ? BigDecimal.ZERO : summaryArticle.getIeps().divide(BigDecimal.valueOf(100));
		BigDecimal iepsValue = summaryArticle.getFinalUnitPrice().multiply(iepsTax);
		this.ieps = DecimalPrecisionUtils.roundToTwoDecimals(iepsValue);
		this.unitMeasurement = summaryArticle.getUnitMeasurement();
		this.statusOrder = summaryArticle.getStatusOrder();
		this.empployeeName = userDto == null ? "---"
				: userDto.getName() + " " + userDto.getFirstSurname() + " " + userDto.getSecondSurname();

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getPriceType() {
		return priceType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public String getArticleDescription() {
		return articleDescription;
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

	public String getEmpployeeName() {
		return empployeeName;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
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

	public void setEmpployeeName(String empployeeName) {
		this.empployeeName = empployeeName;
	}

	@Override
	public String toString() {
		return "ArticleSaleDetailDto [creationDate=" + creationDate + ", priceType=" + priceType + ", orderCode="
				+ orderCode + ", orderNumber=" + orderNumber + ", clientNumber=" + clientNumber
				+ ", articleDescription=" + articleDescription + ", alternativeDescription=" + alternativeDescription
				+ ", articleCode=" + articleCode + ", requestAmount=" + requestAmount + ", finalUnitPrice="
				+ finalUnitPrice + ", subTotal=" + subTotal + ", subTotalTax=" + subTotalTax + ", ieps=" + ieps
				+ ", unitMeasurement=" + unitMeasurement + ", statusOrder=" + statusOrder + ", empployeeName="
				+ empployeeName + "]";
	}

}
