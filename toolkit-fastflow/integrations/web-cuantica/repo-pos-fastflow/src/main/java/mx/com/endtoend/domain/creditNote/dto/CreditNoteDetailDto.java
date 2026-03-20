package mx.com.endtoend.domain.creditNote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CreditNoteDetailDto {

	private Long id;

	private BigDecimal articleNumber;

	private String articleCode;

	private String descriptionOne;

	private String descriptionTwo;

	private String unitMeasure;

	@NotNull(message = "El precio unitario final es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio unitario final debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal finalUnitPrice;

	@NotNull(message = "El impuesto uno es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto uno debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxOne;

	@NotNull(message = "El impuesto dos es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto dos debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal taxTwo;

	@NotNull(message = "La cantidad solicitada es obligatoria")
	@DecimalMin(value = "0.0", message = "La cantidad solicitada debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal requestAmount;

	private boolean isCustomArticle;

	public CreditNoteDetailDto() {
		super();
	}

	public CreditNoteDetailDto(OrderDetailDto orderDetailDto) {
		super();
		this.id = null;
		this.articleNumber = orderDetailDto.getArticleNumber();
		this.articleCode = orderDetailDto.getArticleCode();
		this.descriptionOne = orderDetailDto.getDescriptionOne();
		this.descriptionTwo = orderDetailDto.getDescriptionTwo();
		this.unitMeasure = orderDetailDto.getUnitMeasurement();
		this.finalUnitPrice = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getFinalUnitPrice());
		this.taxOne = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getTaxValueOne());
		this.taxTwo = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getTaxValueTwo());
		this.requestAmount = DecimalPrecisionUtils.roundToTwoDecimals(orderDetailDto.getRequestAmount());
		this.isCustomArticle = orderDetailDto.getIsCustumArticle();
	}

	public Long getId() {
		return id;
	}

	public BigDecimal getArticleNumber() {
		return articleNumber;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public String getDescriptionOne() {
		return descriptionOne;
	}

	public String getDescriptionTwo() {
		return descriptionTwo;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public BigDecimal getFinalUnitPrice() {
		return finalUnitPrice;
	}

	public BigDecimal getTaxOne() {
		return taxOne;
	}

	public BigDecimal getTaxTwo() {
		return taxTwo;
	}

	public boolean getIsCustomArticle() {
		return isCustomArticle;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setArticleNumber(BigDecimal articleNumber) {
		this.articleNumber = articleNumber;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setDescriptionOne(String descriptionOne) {
		this.descriptionOne = descriptionOne;
	}

	public void setDescriptionTwo(String descriptionTwo) {
		this.descriptionTwo = descriptionTwo;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public void setFinalUnitPrice(BigDecimal finalUnitPrice) {
		this.finalUnitPrice = DecimalPrecisionUtils.roundToTwoDecimals(finalUnitPrice);
	}

	public void setTaxOne(BigDecimal taxOne) {
		this.taxOne = DecimalPrecisionUtils.roundToTwoDecimals(taxOne);
	}

	public void setTaxTwo(BigDecimal taxTwo) {
		this.taxTwo = DecimalPrecisionUtils.roundToTwoDecimals(taxTwo);
	}

	public void setIsCustomArticle(boolean isCustomArticle) {
		this.isCustomArticle = isCustomArticle;
	}

	public BigDecimal getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(BigDecimal requestAmount) {
		this.requestAmount = DecimalPrecisionUtils.roundToTwoDecimals(requestAmount);
	}

	@Override
	public String toString() {
		return "CreditNoteDetailDto [id=" + id + ", articleNumber=" + articleNumber + ", articleCode=" + articleCode
				+ ", descriptionOne=" + descriptionOne + ", descriptionTwo=" + descriptionTwo + ", unitMeasure="
				+ unitMeasure + ", finalUnitPrice=" + finalUnitPrice + ", taxOne=" + taxOne + ", taxTwo=" + taxTwo
				+ ", requestAmount=" + requestAmount + ", isCustomArticle=" + isCustomArticle + "]";
	}

}
