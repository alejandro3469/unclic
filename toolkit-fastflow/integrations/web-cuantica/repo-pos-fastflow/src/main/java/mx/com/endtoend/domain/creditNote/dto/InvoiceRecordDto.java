package mx.com.endtoend.domain.creditNote.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Clase para obtener la aporbación del sistema JDE para aplicar N.C. y su
 * disponibilidad por artículo
 * 
 * @author ddcasas
 *
 */
public class InvoiceRecordDto {

	private String articleCode;

	private boolean isValid;

	@NotNull(message = "La cantidad es obligatoria")
	@DecimalMin(value = "0.0", message = "La cantidad debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal quantity;
	
	public InvoiceRecordDto generateEmptyRecords(String articleCode) {
		InvoiceRecordDto invoiceRecordDto = new InvoiceRecordDto();
		invoiceRecordDto.setArticleCode(articleCode);
		invoiceRecordDto.setQuantity(BigDecimal.ZERO);
		invoiceRecordDto.setIsValid(false);
		return invoiceRecordDto;
	}
	
	public InvoiceRecordDto generateExistsRecords(String articleCode, BigDecimal quantity) {
		InvoiceRecordDto invoiceRecordDto = new InvoiceRecordDto();
		invoiceRecordDto.setArticleCode(articleCode);
		invoiceRecordDto.setQuantity(quantity);
		invoiceRecordDto.setIsValid(true);
		return invoiceRecordDto;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public boolean getIsValid() {
		return isValid;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = DecimalPrecisionUtils.roundToTwoDecimals(quantity);
	}

	@Override
	public String toString() {
		return "InvoiceRecordDto [articleCode=" + articleCode + ", isValid=" + isValid + ", quantity=" + quantity + "]";
	}

}
