package mx.com.endtoend.domain.reports.sales.branchEmployee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.endtoend.domain.reports.sales.models.SummarySaleCashPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCheckPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditCardPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditNotePaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleTransferPaymentDto;

/**
 * Clase modelo para la generación del resumen operativos de los cobros del
 * sistema por sucursal y empleado
 * 
 * @author ddcasas
 *
 */
public class SummarySaleBranchEmployeeDto {

	private static final String COBRO_CHEQUE = "COBRO CHEQUE";

	private static final String COBRO_TRANSFERENCIA = "COBRO TRANSFERENCIA";

	private static final String COBRO_NOTA_DE_CREDITO = "COBRO NOTA DE CRÉDITO";

	private static final String COBRO_TARJETA = "COBRO TARJETA";

	private static final String COBRO_EFECTIVO = "COBRO EFECTIVO";
	
	private static final String COBRO_CREDITO = "COBRO CRÉDITO";

	private Date paymentDate;

	private String orderNumber;

	private String orderCode;

	private Long clientNumber;

	private String clientName;

	@NotNull(message = "El monto total recibido es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total recibido debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmountReceived;

	private String paymenmtIntrument;

	private String paymentDescription;

	private String invoiceReferenceNumber;

	private String invoiceReferenceCode;
	
	
	/**
	 * Constructor para la generación del resumen operativo de cobros con crédito
	 * 
	 * @param summarySaleCreditPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleCreditPaymentDto summarySaleCreditPayment) {

		this.paymentDate = summarySaleCreditPayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCreditPayment.getOrderNumber().longValue());
		this.orderCode = summarySaleCreditPayment.getOrderCode();
		this.clientNumber = summarySaleCreditPayment.getClientNumber();

		this.clientName = summarySaleCreditPayment.getBusinessName().isEmpty()
				? summarySaleCreditPayment.getClientName() + " " + summarySaleCreditPayment.getClientFirstSurname() + " "
						+ summarySaleCreditPayment.getClientSecondSurname()
				: summarySaleCreditPayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCreditPayment.getAmountApplied());
		this.paymenmtIntrument = COBRO_CREDITO;
		this.paymentDescription = summarySaleCreditPayment.getReferenceId();

		this.invoiceReferenceNumber = summarySaleCreditPayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCreditPayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCreditPayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleCreditPayment.getInvoiceReferenceCode();
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros en efectivo
	 * 
	 * @param summarySaleCashPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleCashPaymentDto summarySaleCashPayment) {

		this.paymentDate = summarySaleCashPayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCashPayment.getOrderNumber().longValue());
		this.orderCode = summarySaleCashPayment.getOrderCode();
		this.clientNumber = summarySaleCashPayment.getClientNumber();

		this.clientName = summarySaleCashPayment.getBusinessName().isEmpty()
				? summarySaleCashPayment.getClientName() + " " + summarySaleCashPayment.getClientFirstSurname() + " "
						+ summarySaleCashPayment.getClientSecondSurname()
				: summarySaleCashPayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(summarySaleCashPayment.getAmountApplied()));
		this.paymenmtIntrument = COBRO_EFECTIVO;
		this.paymentDescription = summarySaleCashPayment.getCurrency();

		this.invoiceReferenceNumber = summarySaleCashPayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCashPayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCashPayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleCashPayment.getInvoiceReferenceCode();
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros tarjetas de
	 * crédito
	 * 
	 * @param summarySaleCashPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleCreditCardPaymentDto summarySaleCreditCardPayment) {

		this.paymentDate = summarySaleCreditCardPayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCreditCardPayment.getOrderNumber().longValue());
		this.orderCode = summarySaleCreditCardPayment.getOrderCode();
		this.clientNumber = summarySaleCreditCardPayment.getClientNumber();

		this.clientName = summarySaleCreditCardPayment.getBusinessName().isEmpty()
				? summarySaleCreditCardPayment.getClientName() + " "
						+ summarySaleCreditCardPayment.getClientFirstSurname() + " "
						+ summarySaleCreditCardPayment.getClientSecondSurname()
				: summarySaleCreditCardPayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCreditCardPayment.getAmountApplied());
		this.paymenmtIntrument = COBRO_TARJETA + " " + summarySaleCreditCardPayment.getBankingInstitution();
		this.paymentDescription = summarySaleCreditCardPayment.getBankingInstitution() + " - "
				+ summarySaleCreditCardPayment.getCreditCardCode() + " / " + "REF. "
				+ summarySaleCreditCardPayment.getCardReference() == null ? " "
						: summarySaleCreditCardPayment.getCardReference();

		this.invoiceReferenceNumber = summarySaleCreditCardPayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCreditCardPayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCreditCardPayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleCreditCardPayment.getInvoiceReferenceCode();
	}

	/**
	 * Constructor para la generación del resumen operativo con cobro notas de
	 * crédito
	 * 
	 * @param summarySaleCashPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleCreditNotePaymentDto summarySaleCreditNotePayment) {

		this.paymentDate = summarySaleCreditNotePayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCreditNotePayment.getOrderNumber().longValue());
		this.orderCode = summarySaleCreditNotePayment.getOrderCode();
		this.clientNumber = summarySaleCreditNotePayment.getClientNumber();

		this.clientName = summarySaleCreditNotePayment.getBusinessName().isEmpty()
				? summarySaleCreditNotePayment.getClientName() + " "
						+ summarySaleCreditNotePayment.getClientFirstSurname() + " "
						+ summarySaleCreditNotePayment.getClientSecondSurname()
				: summarySaleCreditNotePayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCreditNotePayment.getAmountApplied());
		this.paymenmtIntrument = COBRO_NOTA_DE_CREDITO;
		this.paymentDescription = summarySaleCreditNotePayment.getFolio().longValue() + " - "
				+ summarySaleCreditNotePayment.getCreditNoteCode();

		this.invoiceReferenceNumber = summarySaleCreditNotePayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCreditNotePayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCreditNotePayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleCreditNotePayment.getInvoiceReferenceCode();
	}

	/**
	 * Constructor para la generación del resumen operativo con cobro transferencia
	 * 
	 * @param summarySaleCashPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleTransferPaymentDto summarySaleTransferPayment) {

		this.paymentDate = summarySaleTransferPayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleTransferPayment.getOrderNumber().longValue());
		this.orderCode = summarySaleTransferPayment.getOrderCode();
		this.clientNumber = summarySaleTransferPayment.getClientNumber();

		this.clientName = summarySaleTransferPayment.getBusinessName().isEmpty()
				? summarySaleTransferPayment.getClientName() + " " + summarySaleTransferPayment.getClientFirstSurname()
						+ " " + summarySaleTransferPayment.getClientSecondSurname()
				: summarySaleTransferPayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleTransferPayment.getAmountApplied());
		this.paymenmtIntrument = COBRO_TRANSFERENCIA;
		this.paymentDescription = "NO. REF. " + summarySaleTransferPayment.getReferenceNumber() + " / " + "NO. SEG. "
				+ summarySaleTransferPayment.getTrackingNumber();

		this.invoiceReferenceNumber = summarySaleTransferPayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleTransferPayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleTransferPayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleTransferPayment.getInvoiceReferenceCode();
	}

	/**
	 * Constructor para la generación del resumen operativo con cobro cheque
	 * 
	 * @param summarySaleCashPayment
	 */
	public SummarySaleBranchEmployeeDto(SummarySaleCheckPaymentDto summarySaleCheckPayment) {

		this.paymentDate = summarySaleCheckPayment.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCheckPayment.getOrderNumber().longValue());
		this.orderCode = summarySaleCheckPayment.getOrderCode();
		this.clientNumber = summarySaleCheckPayment.getClientNumber();

		this.clientName = summarySaleCheckPayment.getBusinessName().isEmpty()
				? summarySaleCheckPayment.getClientName() + " " + summarySaleCheckPayment.getClientFirstSurname() + " "
						+ summarySaleCheckPayment.getClientSecondSurname()
				: summarySaleCheckPayment.getBusinessName();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCheckPayment.getAmountApplied());
		this.paymenmtIntrument = COBRO_CHEQUE;
		this.paymentDescription = "NO." + summarySaleCheckPayment.getCheckNumber();

		this.invoiceReferenceNumber = summarySaleCheckPayment.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCheckPayment.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCheckPayment.getInvoiceReferenceCode() == null ? " "
				: summarySaleCheckPayment.getInvoiceReferenceCode();
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public String getClientName() {
		return clientName;
	}

	public BigDecimal getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public String getPaymenmtIntrument() {
		return paymenmtIntrument;
	}

	public String getPaymentDescription() {
		return paymentDescription;
	}

	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public String getInvoiceReferenceCode() {
		return invoiceReferenceCode;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(totalAmountReceived);
	}

	public void setPaymenmtIntrument(String paymenmtIntrument) {
		this.paymenmtIntrument = paymenmtIntrument;
	}

	public void setPaymentDescription(String paymentDescription) {
		this.paymentDescription = paymentDescription;
	}

	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public void setInvoiceReferenceCode(String invoiceReferenceCode) {
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	@Override
	public String toString() {
		return "SummarySaleBranchEmployeeDto [paymentDate=" + paymentDate + ", orderNumber=" + orderNumber
				+ ", orderCode=" + orderCode + ", clientNumber=" + clientNumber + ", clientName=" + clientName
				+ ", totalAmountReceived=" + totalAmountReceived + ", paymenmtIntrument=" + paymenmtIntrument
				+ ", paymentDescription=" + paymentDescription + ", invoiceReferenceNumber=" + invoiceReferenceNumber
				+ ", invoiceReferenceCode=" + invoiceReferenceCode + "]";
	}

}
