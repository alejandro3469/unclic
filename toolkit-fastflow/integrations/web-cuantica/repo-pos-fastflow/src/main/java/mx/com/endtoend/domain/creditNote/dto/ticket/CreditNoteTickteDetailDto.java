package mx.com.endtoend.domain.creditNote.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDetailDto;

public class CreditNoteTickteDetailDto {

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

	@NotNull(message = "El precio unitario con impuestos es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio unitario con impuestos debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal unitPriceTax;

	public CreditNoteTickteDetailDto() {
		super();
	}

	public CreditNoteTickteDetailDto(CreditNoteDetailDto creditNoteDetail) {

		this.article = creditNoteDetail.getArticleCode().trim() + "-" + creditNoteDetail.getDescriptionOne()
				+ creditNoteDetail.getDescriptionTwo();
		this.um = creditNoteDetail.getUnitMeasure();
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(creditNoteDetail.getRequestAmount());

		BigDecimal tax1 = creditNoteDetail.getTaxOne().compareTo(BigDecimal.valueOf(-1)) != 0 ? 
			creditNoteDetail.getTaxOne().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;
		BigDecimal tax2 = creditNoteDetail.getTaxTwo().compareTo(BigDecimal.valueOf(-1)) != 0 ? 
			creditNoteDetail.getTaxTwo().divide(BigDecimal.valueOf(100)) : BigDecimal.ZERO;

		BigDecimal taxByLine = tax1.add(tax2);
		BigDecimal unitPriceTax = creditNoteDetail.getFinalUnitPrice().multiply(BigDecimal.ONE.add(taxByLine));
		unitPriceTax = DecimalPrecisionUtils.roundToTwoDecimals(unitPriceTax);

		this.unitPriceTax = unitPriceTax;
		BigDecimal subtotal = creditNoteDetail.getRequestAmount().multiply(unitPriceTax);
		subtotal = DecimalPrecisionUtils.roundToTwoDecimals(subtotal);

		this.subtotal = subtotal;

	}

	public CreditNoteTickteDetailDto(String article, String um, BigDecimal quantity, BigDecimal subtotal, BigDecimal unitPriceTax) {
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
		return "CreditNoteTickteDetailDto [article=" + article + ", um=" + um + ", quantity=" + quantity + ", subtotal="
				+ subtotal + ", unitPriceTax=" + unitPriceTax + "]";
	}

}
