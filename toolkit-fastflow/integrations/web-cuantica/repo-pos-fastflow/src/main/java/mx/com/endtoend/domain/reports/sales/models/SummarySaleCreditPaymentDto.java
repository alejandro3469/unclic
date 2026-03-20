package mx.com.endtoend.domain.reports.sales.models;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class SummarySaleCreditPaymentDto {

	private Long paymentId;

	private Date paymentDate;

	private BigDecimal orderNumber;

	private String orderCode;

	private Long clientNumber;

	private String businessName;

	private String clientName;

	private String clientFirstSurname;

	private String clientSecondSurname;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal amountApplied;

	private String currency;

	private String referenceId;

	private BigDecimal invoiceReferenceNumber;

	private String invoiceReferenceCode;

	public SummarySaleCreditPaymentDto() {
		super();
	}

	public SummarySaleCreditPaymentDto(Long paymentId, Date paymentDate, BigDecimal orderNumber, String orderCode,
			Long clientNumber, String businessName, String clientName, String clientFirstSurname,
			String clientSecondSurname, BigDecimal amountApplied, String currency, String referenceId,
			BigDecimal invoiceReferenceNumber, String invoiceReferenceCode) {
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
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
		this.currency = currency;
		this.referenceId = referenceId;
		this.invoiceReferenceNumber = invoiceReferenceNumber;
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientFirstSurname() {
		return clientFirstSurname;
	}

	public void setClientFirstSurname(String clientFirstSurname) {
		this.clientFirstSurname = clientFirstSurname;
	}

	public String getClientSecondSurname() {
		return clientSecondSurname;
	}

	public void setClientSecondSurname(String clientSecondSurname) {
		this.clientSecondSurname = clientSecondSurname;
	}

	public BigDecimal getAmountApplied() {
		return amountApplied;
	}

	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = DecimalPrecisionUtils.roundToTwoDecimals(amountApplied);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public BigDecimal getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public void setInvoiceReferenceNumber(BigDecimal invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public String getInvoiceReferenceCode() {
		return invoiceReferenceCode;
	}

	public void setInvoiceReferenceCode(String invoiceReferenceCode) {
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	@Override
	public String toString() {
		return "SummarySaleCreditPaymentDto [paymentId=" + paymentId + ", paymentDate=" + paymentDate + ", orderNumber="
				+ orderNumber + ", orderCode=" + orderCode + ", clientNumber=" + clientNumber + ", businessName="
				+ businessName + ", clientName=" + clientName + ", clientFirstSurname=" + clientFirstSurname
				+ ", clientSecondSurname=" + clientSecondSurname + ", amountApplied=" + amountApplied + ", currency="
				+ currency + ", referenceId=" + referenceId + ", invoiceReferenceNumber=" + invoiceReferenceNumber
				+ ", invoiceReferenceCode=" + invoiceReferenceCode + "]";
	}

}
