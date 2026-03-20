package mx.com.endtoend.domain.reports.sales.branch.dto;

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
import mx.com.endtoend.smart.bussiness.constants.CreditCardTypeEnum;

/**
 * Clase modelo para el resumen operativo de las ventas del sistema
 * 
 * @author ddcasas
 *
 */
public class SummarySaleBranchDto {

	private Date paymentDate;

	private String orderNumber;

	private String orderCode;

	private String clientName;

	private Long clientNumber;

	@NotNull(message = "El monto total recibido es obligatorio")
	@DecimalMin(value = "0.0", message = "El monto total recibido debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal totalAmountReceived;

	private String description;

	private String invoiceReferenceNumber;

	private String invoiceReferenceCode;

	private String referenceBankCode;

	/**
	 * Constructor para la generación del resumen operativo de cobros con crédito
	 * 
	 * @param summarySaleCreditPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleCreditPaymentDto summarySaleCreditPaymentDto) {

		this.paymentDate = summarySaleCreditPaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCreditPaymentDto.getOrderNumber().longValue());
		this.orderCode = summarySaleCreditPaymentDto.getOrderCode();

		this.clientName = summarySaleCreditPaymentDto.getBusinessName().isEmpty()
				? summarySaleCreditPaymentDto.getClientName() + " "
						+ summarySaleCreditPaymentDto.getClientFirstSurname() + " "
						+ summarySaleCreditPaymentDto.getClientSecondSurname()
				: summarySaleCreditPaymentDto.getBusinessName();

		this.clientNumber = summarySaleCreditPaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCreditPaymentDto.getAmountApplied());

		this.description = "REF. " + summarySaleCreditPaymentDto.getReferenceId();

		this.invoiceReferenceNumber = summarySaleCreditPaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCreditPaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCreditPaymentDto.getInvoiceReferenceCode() == null ? " "
				: summarySaleCreditPaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = " ";
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros en efectivo
	 * 
	 * @param summarySaleCashPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleCashPaymentDto summarySaleCashPaymentDto) {

		this.paymentDate = summarySaleCashPaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCashPaymentDto.getOrderNumber().longValue());
		this.orderCode = summarySaleCashPaymentDto.getOrderCode();

		this.clientName = summarySaleCashPaymentDto.getBusinessName().isEmpty()
				? summarySaleCashPaymentDto.getClientName() + " " + summarySaleCashPaymentDto.getClientFirstSurname()
						+ " " + summarySaleCashPaymentDto.getClientSecondSurname()
				: summarySaleCashPaymentDto.getBusinessName();

		this.clientNumber = summarySaleCashPaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(BigDecimal.valueOf(summarySaleCashPaymentDto.getAmountApplied()));

		this.description = summarySaleCashPaymentDto.getCurrency();

		this.invoiceReferenceNumber = summarySaleCashPaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCashPaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCashPaymentDto.getInvoiceReferenceCode() == null ? " "
				: summarySaleCashPaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = " ";
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros con tarjetas
	 * de crédito
	 * 
	 * @param summarySaleCashPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleCreditCardPaymentDto saleCreditCardPaymentDto) {

		this.paymentDate = saleCreditCardPaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(saleCreditCardPaymentDto.getOrderNumber().longValue());
		this.orderCode = saleCreditCardPaymentDto.getOrderCode();

		this.clientName = saleCreditCardPaymentDto.getBusinessName().isEmpty()
				? saleCreditCardPaymentDto.getClientName() + " " + saleCreditCardPaymentDto.getClientFirstSurname()
						+ " " + saleCreditCardPaymentDto.getClientSecondSurname()
				: saleCreditCardPaymentDto.getBusinessName();

		this.clientNumber = saleCreditCardPaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(saleCreditCardPaymentDto.getAmountApplied());

		String description = saleCreditCardPaymentDto.getBankingInstitution() + " - ";

		description += saleCreditCardPaymentDto.getCreditCardType() != null
				? saleCreditCardPaymentDto.getCreditCardType().equals(CreditCardTypeEnum.CREDIT.toString()) ? " CREDITO"
						: saleCreditCardPaymentDto.getCreditCardType().equals(CreditCardTypeEnum.DEBIT.toString())
								? "DEBITO"
								: " -- "
				: " -- ";

		description += saleCreditCardPaymentDto.getCreditCardCode() + " / " + "REF. ";
		description += saleCreditCardPaymentDto.getCardReference() == null ? " "
				: saleCreditCardPaymentDto.getCardReference();
		this.description = description;

		this.invoiceReferenceNumber = saleCreditCardPaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(saleCreditCardPaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = saleCreditCardPaymentDto.getInvoiceReferenceCode() == null ? " "
				: saleCreditCardPaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = " ";
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros con notas de
	 * crédito
	 * 
	 * @param summarySaleCashPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleCreditNotePaymentDto summarySaleCreditNotePaymentDto) {

		this.paymentDate = summarySaleCreditNotePaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCreditNotePaymentDto.getOrderNumber().longValue());
		this.orderCode = summarySaleCreditNotePaymentDto.getOrderCode();

		this.clientName = summarySaleCreditNotePaymentDto.getBusinessName().isEmpty()
				? summarySaleCreditNotePaymentDto.getClientName() + " "
						+ summarySaleCreditNotePaymentDto.getClientFirstSurname() + " "
						+ summarySaleCreditNotePaymentDto.getClientSecondSurname()
				: summarySaleCreditNotePaymentDto.getBusinessName();

		this.clientNumber = summarySaleCreditNotePaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCreditNotePaymentDto.getAmountApplied());

		this.description = summarySaleCreditNotePaymentDto.getFolio().longValue() + " - "
				+ summarySaleCreditNotePaymentDto.getCreditNoteCode();

		this.invoiceReferenceNumber = summarySaleCreditNotePaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCreditNotePaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCreditNotePaymentDto.getInvoiceReferenceCode() == null ? " "
				: summarySaleCreditNotePaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = " ";
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros con cheques
	 * 
	 * @param summarySaleCashPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleCheckPaymentDto summarySaleCheckPaymentDto) {

		this.paymentDate = summarySaleCheckPaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleCheckPaymentDto.getOrderNumber().longValue());
		this.orderCode = summarySaleCheckPaymentDto.getOrderCode();

		this.clientName = summarySaleCheckPaymentDto.getBusinessName().isEmpty()
				? summarySaleCheckPaymentDto.getClientName() + " " + summarySaleCheckPaymentDto.getClientFirstSurname()
						+ " " + summarySaleCheckPaymentDto.getClientSecondSurname()
				: summarySaleCheckPaymentDto.getBusinessName();

		this.clientNumber = summarySaleCheckPaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleCheckPaymentDto.getAmountApplied());

		this.description = "NO." + summarySaleCheckPaymentDto.getCheckNumber();

		this.invoiceReferenceNumber = summarySaleCheckPaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleCheckPaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleCheckPaymentDto.getInvoiceReferenceCode() == null ? " "
				: summarySaleCheckPaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = summarySaleCheckPaymentDto.getBankingInstitution();
	}

	/**
	 * Constructor para la generación del resumen operativo de cobros con
	 * transferencias
	 * 
	 * @param summarySaleCashPaymentDto
	 */
	public SummarySaleBranchDto(SummarySaleTransferPaymentDto summarySaleTransferPaymentDto) {

		this.paymentDate = summarySaleTransferPaymentDto.getPaymentDate();
		this.orderNumber = String.valueOf(summarySaleTransferPaymentDto.getOrderNumber().longValue());
		this.orderCode = summarySaleTransferPaymentDto.getOrderCode();

		this.clientName = summarySaleTransferPaymentDto.getBusinessName().isEmpty()
				? summarySaleTransferPaymentDto.getClientName() + " "
						+ summarySaleTransferPaymentDto.getClientFirstSurname() + " "
						+ summarySaleTransferPaymentDto.getClientSecondSurname()
				: summarySaleTransferPaymentDto.getBusinessName();

		this.clientNumber = summarySaleTransferPaymentDto.getClientNumber();

		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(summarySaleTransferPaymentDto.getAmountApplied());

		this.description = "NO. REF. " + summarySaleTransferPaymentDto.getReferenceNumber() + " / " + "NO. SEG. "
				+ summarySaleTransferPaymentDto.getTrackingNumber();

		this.invoiceReferenceNumber = summarySaleTransferPaymentDto.getInvoiceReferenceNumber() == null ? " "
				: String.valueOf(summarySaleTransferPaymentDto.getInvoiceReferenceNumber().longValue());
		this.invoiceReferenceCode = summarySaleTransferPaymentDto.getInvoiceReferenceCode() == null ? " "
				: summarySaleTransferPaymentDto.getInvoiceReferenceCode();
		this.referenceBankCode = summarySaleTransferPaymentDto.getBankingInstitution();
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

	public String getClientName() {
		return clientName;
	}

	public Long getClientNumber() {
		return clientNumber;
	}

	public BigDecimal getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public String getDescription() {
		return description;
	}

	public String getInvoiceReferenceNumber() {
		return invoiceReferenceNumber;
	}

	public String getInvoiceReferenceCode() {
		return invoiceReferenceCode;
	}

	public String getReferenceBankCode() {
		return referenceBankCode;
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

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setClientNumber(Long clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
		this.totalAmountReceived = DecimalPrecisionUtils.roundToTwoDecimals(totalAmountReceived);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setInvoiceReferenceNumber(String invoiceReferenceNumber) {
		this.invoiceReferenceNumber = invoiceReferenceNumber;
	}

	public void setInvoiceReferenceCode(String invoiceReferenceCode) {
		this.invoiceReferenceCode = invoiceReferenceCode;
	}

	public void setReferenceBankCode(String referenceBankCode) {
		this.referenceBankCode = referenceBankCode;
	}

	@Override
	public String toString() {
		return "SummarySaleBranchDto [paymentDate=" + paymentDate + ", orderNumber=" + orderNumber + ", orderCode="
				+ orderCode + ", clientName=" + clientName + ", clientNumber=" + clientNumber + ", totalAmountReceived="
				+ totalAmountReceived + ", description=" + description + ", invoiceReferenceNumber="
				+ invoiceReferenceNumber + ", invoiceReferenceCode=" + invoiceReferenceCode + ", referenceBankCode="
				+ referenceBankCode + "]";
	}

}
