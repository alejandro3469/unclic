package mx.com.endtoend.domain.reports.sales.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase modelo para la recuperación de datos del resumen de ventas por concepto
 * de efectivo
 * 
 * @author ddcasas
 *
 */
public class SummarySaleCashPaymentDto {

	private Long paymentId;

	private Date paymentDate;

	private BigDecimal orderNumber;

	private String orderCode;

	private Long clientNumber;

	private String businessName;

	private String clientName;

	private String clientFirstSurname;

	private String clientSecondSurname;

	private BigDecimal amountApplied;

	private String currency;

	private BigDecimal invoiceReferenceNumber;

	private String invoiceReferenceCode;

	public SummarySaleCashPaymentDto(Long paymentId, Date paymentDate, BigDecimal orderNumber, String orderCode,
			Long clientNumber, String businessName, String clientName, String clientFirstSurname,
			String clientSecondSurname, BigDecimal amountApplied, String currency, BigDecimal invoiceReferenceNumber,
			String invoiceReferenceCode) {
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
		this.amountApplied = amountApplied;
		this.currency = currency;
		this.invoiceReferenceNumber = invoiceReferenceNumber;
		this.invoiceReferenceCode = invoiceReferenceCode;
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

	public Double getAmountApplied() {
		return amountApplied != null ? amountApplied.doubleValue() : null;
	}

	public BigDecimal getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public String getInvoiceReferenceCode() {
		return invoiceReferenceCode;
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

	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = amountApplied;
	}

	public void setInvoiceReferenceNumber(BigDecimal invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public void setInvoiceReferenceCode(String invoiceReferenceCode) {
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
