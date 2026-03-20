package mx.com.endtoend.domain.reports.sales.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase modelo para la recuperación de los datos del resumen de ventas por
 * concepto de cobro contarjeta de credito
 * 
 * @author ddcasas
 *
 */
public class SummarySaleCreditCardPaymentDto {

	private Long paymentId;

	private Date paymentDate;

	private BigDecimal orderNumber;

	private String orderCode;

	private Long clientNumber;

	private String businessName;

	private String clientName;

	private String clientFirstSurname;

	private String clientSecondSurname;

	private BigDecimal invoiceReferenceNumber;

	private String invoiceReferenceCode;

	@NotNull(message = "El monto aplicado es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto aplicado debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountApplied;

	private String bankingInstitution;

	private String cardReference;

	private String creditCardCode;

	private String creditCardType;

	public SummarySaleCreditCardPaymentDto(Long paymentId, Date paymentDate, BigDecimal orderNumber, String orderCode,
			Long clientNumber, String businessName, String clientName, String clientFirstSurname,
			String clientSecondSurname, BigDecimal invoiceReferenceNumber, String invoiceReferenceCode,
			BigDecimal amountApplied, String bankingInstitution, String cardReference, String creditCardCode,
			String creditCardType) {
		super();
		this.paymentId = paymentId;
		this.paymentDate = paymentDate;
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.clientNumber = clientNumber;
		this.businessName = businessName;
		this.clientName = clientName;
		this.clientFirstSurname = clientFirstSurname;
		this.clientSecondSurname = clientSecondSurname;
		this.invoiceReferenceNumber = invoiceReferenceNumber;
		this.invoiceReferenceCode = invoiceReferenceCode;
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
		this.bankingInstitution = bankingInstitution;
		this.cardReference = cardReference;
		this.creditCardCode = creditCardCode;
		this.creditCardType = creditCardType;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getClientName() {
		return clientName;
	}

	public String getClientFirstSurname() {
		return clientFirstSurname;
	}

	public String getClientSecondSurname() {
		return clientSecondSurname;
	}

	public BigDecimal getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public String getInvoiceReferenceCode() {
		return invoiceReferenceCode;
	}

	public BigDecimal getAmountApplied() {
		return amountApplied;
	}

	public String getBankingInstitution() {
		return bankingInstitution;
	}

	public String getCardReference() {
		return cardReference;
	}

	public String getCreditCardCode() {
		return creditCardCode;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setClientFirstSurname(String clientFirstSurname) {
		this.clientFirstSurname = clientFirstSurname;
	}

	public void setClientSecondSurname(String clientSecondSurname) {
		this.clientSecondSurname = clientSecondSurname;
	}

	public void setInvoiceReferenceNumber(BigDecimal invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public void setInvoiceReferenceCode(String invoiceReferenceCode) {
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
	}

	public void setBankingInstitution(String bankingInstitution) {
		this.bankingInstitution = bankingInstitution;
	}

	public void setCardReference(String cardReference) {
		this.cardReference = cardReference;
	}

	public void setCreditCardCode(String creditCardCode) {
		this.creditCardCode = creditCardCode;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

}
