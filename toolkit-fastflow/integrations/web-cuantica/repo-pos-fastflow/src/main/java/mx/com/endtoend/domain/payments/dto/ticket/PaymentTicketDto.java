package mx.com.endtoend.domain.payments.dto.ticket;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

public class PaymentTicketDto {

	private String order;

	private String clientNumber;

	private String employeName;

	private String date;

	private InputStream image;

	private String orderNumber;

	private Integer requestTotal;

	private String branchName;

	private String branchCP;

	private String branchStreet;

	private String branchPhone;

	private String branchExternalNumber;

	private String folio;

	@NotNull(message = "El impuesto 0% es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto 0% debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal tax0;

	@NotNull(message = "El impuesto 16% es obligatorio")
	@DecimalMin(value = "0.0", message = "El impuesto 16% debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal tax16;

	private BigDecimal taxIEPS;

	private BigDecimal ivaTotal;

	private BigDecimal orderTotal;

	private BigDecimal returned;

	private String paymentInstrument;

	private List<PaymentTicketDetailDto> detail;

	private String amountLetter;

	private String companyName;

	private String businessName;

	private String printStatus;

	private String urlAutofac;

	private String key;

	public PaymentTicketDto() {
		super();
	}

	public PaymentTicketDto(PaymentDto paymentDto, OrderDto orderDto, BranchDto branchDto, UserDto userDto,
			UserDto userSale, boolean printSatus) {
		super();
		this.order = orderDto.getOrderCode();
		this.clientNumber = paymentDto.getClientNumber().toString();
		this.employeName = userDto.getName() + " " + userDto.getFirstSurname() + " " + userDto.getSecondSurname();
		String fomatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(paymentDto.getPaymentDate());
		this.date = fomatDate;
		this.orderNumber = String.valueOf(orderDto.getOrderNumber().longValue());
		this.requestTotal = orderDto.getOrderDetail().size();
		this.branchName = branchDto.getName();
		this.branchCP = branchDto.getCp();
		this.branchStreet = branchDto.getStreet();
		this.branchPhone = branchDto.getPhoneNumber();
		this.branchExternalNumber = branchDto.getOutsideNumber();
		this.folio = userSale.getUserNumber().toString() + "-" + branchDto.getCode().trim() + "-" + String.valueOf(orderDto.getOrderNumber().longValue());
		this.ivaTotal = orderDto.getIvaTotal();

		for (TaxDto tax : orderDto.getTaxes()) {
			if (tax.getTaxValue().equals("0")) {
				this.tax0 = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
			if (tax.getTaxValue().equals("16")) {
				this.tax16 = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
			if (tax.getTaxValue().equals("IEPS")) {
				this.taxIEPS = DecimalPrecisionUtils.roundToTwoDecimals(tax.getValue());
			}
		}

		this.orderTotal = orderDto.getOrderTotal();
		this.returned = paymentDto.getPaymentCashList() != null
				? paymentDto.getPaymentCashList().size() > 0 ? getAmountReturned(paymentDto.getPaymentCashList()) : BigDecimal.ZERO
				: BigDecimal.ZERO;

		String paymentInstriments = generatePaymentMethodList(paymentDto);

		this.paymentInstrument = paymentInstriments;

		this.printStatus = printSatus ? "REIMPRESION TICKET" : "";

	}

	private String generatePaymentMethodList(PaymentDto paymentDto) {
		String paymentInstriments = "";
		paymentInstriments = paymentDto.getPaymentCashList() != null
				? paymentDto.getPaymentCashList().size() > 0 ? paymentInstriments + " COBRO EFECTIVO - "
						: paymentInstriments
				: paymentInstriments;

		paymentInstriments = paymentDto.getCreditCardPaymentList() != null
				? paymentDto.getCreditCardPaymentList().size() > 0 ? paymentInstriments + " COBRO CON TARJETA - "
						: paymentInstriments
				: paymentInstriments;

		paymentInstriments = paymentDto.getTransferPaymentList() != null
				? paymentDto.getTransferPaymentList().size() > 0 ? paymentInstriments + " COBRO CON TRANSFERENCIA - "
						: paymentInstriments
				: paymentInstriments;

		paymentInstriments = paymentDto.getCreditNotePaymentList() != null
				? paymentDto.getCreditNotePaymentList().size() > 0 ? paymentInstriments + " COBRO CON NOTA DE CRÉDITO -"
						: paymentInstriments
				: paymentInstriments;

		paymentInstriments = paymentDto.getCheckPaymentList() != null
				? paymentDto.getCheckPaymentList().size() > 0 ? paymentInstriments + " COBRO CON CHEQUE -"
						: paymentInstriments
				: paymentInstriments;
				
		paymentInstriments = paymentDto.getCreditPaymentList() != null
				? paymentDto.getCreditPaymentList().size() > 0 ? paymentInstriments + " COBRO CRÉDITO -"
						: paymentInstriments
				: paymentInstriments;
				
		return paymentInstriments;
	}

	private BigDecimal getAmountReturned(List<PaymentCashDto> paymentCashList) {
		BigDecimal amountApplied = BigDecimal.ZERO;
		BigDecimal amountReceived = BigDecimal.ZERO;
		for (PaymentCashDto paymentCashDto : paymentCashList) {
			amountApplied = amountApplied.add(paymentCashDto.getAmountApplied());
			amountReceived = amountReceived.add(paymentCashDto.getAmountReceived());
		}
		BigDecimal result = amountReceived.subtract(amountApplied);
		result = DecimalPrecisionUtils.roundToTwoDecimals(result);
		return result;
	}

	public String getOrder() {
		return order;
	}

	public String getClientNumber() {
		return clientNumber;
	}

	public String getEmployeName() {
		return employeName;
	}

	public String getDate() {
		return date;
	}

	public InputStream getImage() {
		return image;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public Integer getRequestTotal() {
		return requestTotal;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchCP() {
		return branchCP;
	}

	public String getBranchStreet() {
		return branchStreet;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public String getBranchExternalNumber() {
		return branchExternalNumber;
	}

	public String getFolio() {
		return folio;
	}

	public BigDecimal getTax0() {
		return tax0;
	}

	public BigDecimal getTax16() {
		return tax16;
	}

	public BigDecimal getTaxIEPS() {
		return taxIEPS;
	}

	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public BigDecimal getReturned() {
		return returned;
	}

	public String getPaymentInstrument() {
		return paymentInstrument;
	}

	public List<PaymentTicketDetailDto> getDetail() {
		return detail;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public void setEmployeName(String employeName) {
		this.employeName = employeName;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setRequestTotal(Integer requestTotal) {
		this.requestTotal = requestTotal;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchCP(String branchCP) {
		this.branchCP = branchCP;
	}

	public void setBranchStreet(String branchStreet) {
		this.branchStreet = branchStreet;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public void setBranchExternalNumber(String branchExternalNumber) {
		this.branchExternalNumber = branchExternalNumber;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public void setTax0(BigDecimal tax0) {
		this.tax0 = DecimalPrecisionUtils.roundToTwoDecimals(tax0);
	}

	public void setTax16(BigDecimal tax16) {
		this.tax16 = DecimalPrecisionUtils.roundToTwoDecimals(tax16);
	}

	public void setTaxIEPS(BigDecimal taxIEPS) {
		this.taxIEPS = DecimalPrecisionUtils.roundToTwoDecimals(taxIEPS);
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = DecimalPrecisionUtils.roundToTwoDecimals(orderTotal);
	}

	public void setReturned(BigDecimal returned) {
		this.returned = DecimalPrecisionUtils.roundToTwoDecimals(returned);
	}

	public void setPaymentInstrument(String paymentInstrument) {
		this.paymentInstrument = paymentInstrument;
	}

	public void setDetail(List<PaymentTicketDetailDto> detail) {
		this.detail = detail;
	}

	public BigDecimal getIvaTotal() {
		return ivaTotal;
	}

	public String getAmountLetter() {
		return amountLetter;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setIvaTotal(BigDecimal ivaTotal) {
		this.ivaTotal = DecimalPrecisionUtils.roundToTwoDecimals(ivaTotal);
	}

	public void setAmountLetter(String amountLetter) {
		this.amountLetter = amountLetter;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getUrlAutofac() {
		return urlAutofac;
	}

	public void setUrlAutofac(String urlAutofac) {
		this.urlAutofac = urlAutofac;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "PaymentTicketDto [order=" + order + ", clientNumber=" + clientNumber + ", employeName=" + employeName
				+ ", date=" + date + ", image=" + image + ", orderNumber=" + orderNumber + ", requestTotal="
				+ requestTotal + ", branchName=" + branchName + ", branchCP=" + branchCP + ", branchStreet="
				+ branchStreet + ", branchPhone=" + branchPhone + ", branchExternalNumber=" + branchExternalNumber
				+ ", folio=" + folio + ", tax0=" + tax0 + ", tax16=" + tax16 + ", taxIEPS=" + taxIEPS + ", ivaTotal="
				+ ivaTotal + ", orderTotal=" + orderTotal + ", returned=" + returned + ", paymentInstrument="
				+ paymentInstrument + ", detail=" + detail + ", amountLetter=" + amountLetter + ", companyName="
				+ companyName + ", businessName=" + businessName + ", printStatus=" + printStatus + "]";
	}

}
