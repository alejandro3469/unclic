package mx.com.endtoend.domain.creditNote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreditNoteHeaderDto {

	private Long id;

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

	private Long clientId;

	private Long clientNumber;

	private Long saleEmployeeId;

	private Long creditNoteId;

	private String currency;

	private BigDecimal exchangeRate;

	private boolean isPrinted;

	private List<CreditNoteDetailDto> creditNoteDetail;

	public CreditNoteHeaderDto() {
		super();
	}

	public CreditNoteHeaderDto(BigDecimal folio, String creditNoteCode, BigDecimal totalAmount, Long clientId,
			Long clientNumber, Long saleEmployeeId, List<CreditNoteDetailDto> creditNoteDetail, String currency,
			BigDecimal exchangeRate) {
		super();
		this.id = null;
		this.folio = folio;
		this.creditNoteCode = creditNoteCode;
		this.totalAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.usedAmountM = BigDecimal.ZERO;
		this.pendingAmount = DecimalPrecisionUtils.roundToTwoDecimals(totalAmount);
		this.creationDate = new Date();
		this.clientId = clientId;
		this.clientNumber = clientNumber;
		this.saleEmployeeId = saleEmployeeId;
		this.creditNoteDetail = creditNoteDetail;
		this.currency = currency;
		this.exchangeRate = currency.equals("MXN") ? BigDecimal.ONE : DecimalPrecisionUtils.roundToTwoDecimals(exchangeRate);
		this.isPrinted = false;
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

	public Long getClientId() {
		return clientId;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public List<CreditNoteDetailDto> getCreditNoteDetail() {
		return creditNoteDetail;
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

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCreditNoteDetail(List<CreditNoteDetailDto> creditNoteDetail) {
		this.creditNoteDetail = creditNoteDetail;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Long getSaleEmployeeId() {
		return saleEmployeeId;
	}

	public void setSaleEmployeeId(Long saleEmployeeId) {
		this.saleEmployeeId = saleEmployeeId;
	}

	public boolean getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(boolean isPrinted) {
		this.isPrinted = isPrinted;
	}

	public Long getCreditNoteId() {
		return creditNoteId;
	}

	public void setCreditNoteId(Long creditNoteId) {
		this.creditNoteId = creditNoteId;
	}

	@Override
	public String toString() {
		return "CreditNoteHeaderDto [id=" + id + ", folio=" + folio + ", creditNoteCode=" + creditNoteCode
				+ ", totalAmount=" + totalAmount + ", usedAmountM=" + usedAmountM + ", pendingAmount=" + pendingAmount
				+ ", creationDate=" + creationDate + ", clientId=" + clientId + ", clientNumber=" + clientNumber
				+ ", saleEmployeeId=" + saleEmployeeId + ", currency=" + currency + ", exchangeRate=" + exchangeRate
				+ ", creditNoteDetail=" + creditNoteDetail + "]";
	}

}