package mx.com.endtoend.domain.creditNote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

public class CreditNoteSummary {

	@NotNull(message = "El folio es obligatorio")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal folio;

	private String creditNoteCode;

	@NotNull(message = "El monto total es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmount;

	@NotNull(message = "El monto usado es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto usado debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal usedAmountM;

	@NotNull(message = "El monto pendiente es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto pendiente debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal pendingAmount;

	private Date creationDate;

	private Long clientNumber;

	private String currency;

	private BigDecimal exchangeRate;

	@NotNull(message = "El número de orden es obligatorio")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal orderNumber;

	private String orderCode;

	public CreditNoteSummary(BigDecimal folio, String creditNoteCode, BigDecimal totalAmount, BigDecimal usedAmountM,
			BigDecimal pendingAmount, Date creationDate, Long clientNumber, String currency, BigDecimal exchangeRate) {
		super();
		this.folio = folio;
		this.creditNoteCode = creditNoteCode;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.usedAmountM = DecimalPrecisionUtils.roundToTwoDecimals(usedAmountM);
		this.pendingAmount = DecimalPrecisionUtils.roundToTwoDecimals(pendingAmount);
		this.creationDate = creationDate;
		this.clientNumber = clientNumber;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
	}

	public CreditNoteSummary(BigDecimal folio, String creditNoteCode, BigDecimal totalAmount, BigDecimal usedAmountM,
			BigDecimal pendingAmount, Date creationDate, Long clientNumber, String currency, BigDecimal exchangeRate,
			BigDecimal orderNumber, String orderCode) {
		super();
		this.folio = folio;
		this.creditNoteCode = creditNoteCode;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.usedAmountM = DecimalPrecisionUtils.roundToTwoDecimals(usedAmountM);
		this.pendingAmount = DecimalPrecisionUtils.roundToTwoDecimals(pendingAmount);
		this.creationDate = creationDate;
		this.clientNumber = clientNumber;
		this.currency = currency;
		this.exchangeRate = exchangeRate;
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
	}

	public BigDecimal getFolio() {
		return folio;
	}

	public String getCreditNoteCode() {
		return creditNoteCode;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public BigDecimal getUsedAmountM() {
		return usedAmountM;
	}

	public BigDecimal getPendingAmount() {
		return pendingAmount;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public void setCreditNoteCode(String creditNoteCode) {
		this.creditNoteCode = creditNoteCode;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
	}

	public void setUsedAmountM(BigDecimal usedAmountM) {
		this.usedAmountM = DecimalPrecisionUtils.roundToTwoDecimals(usedAmountM);
	}

	public void setPendingAmount(BigDecimal pendingAmount) {
		this.pendingAmount = DecimalPrecisionUtils.roundToTwoDecimals(pendingAmount);
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Override
	public String toString() {
		return "CreditNoteSummary [folio=" + folio + ", creditNoteCode=" + creditNoteCode + ", totalAmount="
				+ totalAmount + ", usedAmountM=" + usedAmountM + ", pendingAmount=" + pendingAmount + ", creationDate="
				+ creationDate + ", clientNumber=" + clientNumber + ", currency=" + currency + ", exchangeRate="
				+ exchangeRate + "]";
	}
}