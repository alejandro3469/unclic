package mx.com.endtoend.domain.recharges.dto;

import java.math.BigDecimal;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.recharges.ports.RechargeSalePersistencePort;
import mx.com.endtoend.infrastructure.services.jde.orders.common.serviceport.OrderJdeServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;

public class CustomRechargeSaleParams {

	private String companyCode;

	private String idOperation;

	private String method;

	private String orderCode;

	private BigDecimal orderNumber;

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	private PaymentJDEServicePort paymentJDEServicePort;

	private RechargeSalePersistencePort rechargeSalePersistencePort;

	private OrderJdeServicePort orderJdeServicePort;

	private PaymentPersistencePort paymentPersistencePort;

	private OrderPersistencePort orderPersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private ClientPersistencePort clientPersistencePort;

	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	public CustomRechargeSaleParams(String companyCode, String idOperation, String method, String orderCode,
			BigDecimal orderNumber, AccountingRecordPersistencePort accountingRecordPersistencePort,
			PaymentJDEServicePort paymentJDEServicePort, OrderJdeServicePort orderJdeServicePort,
			PaymentPersistencePort paymentPersistencePort, OrderPersistencePort orderPersistencePort,
			CompanyPersistencePort companyPersistencePort, ClientPersistencePort clientPersistencePort, 
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort) {
		super();
		this.companyCode = companyCode;
		this.idOperation = idOperation;
		this.method = method;
		this.orderCode = orderCode;
		this.orderNumber = orderNumber;
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderJdeServicePort = orderJdeServicePort;
		this.paymentPersistencePort = paymentPersistencePort;
		this.orderPersistencePort = orderPersistencePort;
		this.companyPersistencePort = companyPersistencePort;
		this.clientPersistencePort = clientPersistencePort;
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
	}

	public CustomRechargeSaleParams(String companyCode, String idOperation, String method,
			AccountingRecordPersistencePort accountingRecordPersistencePort,
			PaymentJDEServicePort paymentJDEServicePort, OrderJdeServicePort orderJdeServicePort,
			PaymentPersistencePort paymentPersistencePort, OrderPersistencePort orderPersistencePort,
			CompanyPersistencePort companyPersistencePort, ClientPersistencePort clientPersistencePort, 
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort) {
		super();
		this.companyCode = companyCode;
		this.idOperation = idOperation;
		this.method = method;
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderJdeServicePort = orderJdeServicePort;
		this.paymentPersistencePort = paymentPersistencePort;
		this.orderPersistencePort = orderPersistencePort;
		this.companyPersistencePort = companyPersistencePort;
		this.clientPersistencePort = clientPersistencePort;
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getIdOperation() {
		return idOperation;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public AccountingRecordPersistencePort getAccountingRecordPersistencePort() {
		return accountingRecordPersistencePort;
	}

	public void setAccountingRecordPersistencePort(AccountingRecordPersistencePort accountingRecordPersistencePort) {
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
	}

	public PaymentJDEServicePort getPaymentJDEServicePort() {
		return paymentJDEServicePort;
	}

	public void setPaymentJDEServicePort(PaymentJDEServicePort paymentJDEServicePort) {
		this.paymentJDEServicePort = paymentJDEServicePort;
	}

	public RechargeSalePersistencePort getRechargeSalePersistencePort() {
		return rechargeSalePersistencePort;
	}

	public void setRechargeSalePersistencePort(RechargeSalePersistencePort rechargeSalePersistencePort) {
		this.rechargeSalePersistencePort = rechargeSalePersistencePort;
	}

	public OrderJdeServicePort getOrderJdeServicePort() {
		return orderJdeServicePort;
	}

	public void setOrderJdeServicePort(OrderJdeServicePort orderJdeServicePort) {
		this.orderJdeServicePort = orderJdeServicePort;
	}

	public PaymentPersistencePort getPaymentPersistencePort() {
		return paymentPersistencePort;
	}

	public void setPaymentPersistencePort(PaymentPersistencePort paymentPersistencePort) {
		this.paymentPersistencePort = paymentPersistencePort;
	}

	public OrderPersistencePort getOrderPersistencePort() {
		return orderPersistencePort;
	}

	public void setOrderPersistencePort(OrderPersistencePort orderPersistencePort) {
		this.orderPersistencePort = orderPersistencePort;
	}

	public CompanyPersistencePort getCompanyPersistencePort() {
		return companyPersistencePort;
	}

	public void setCompanyPersistencePort(CompanyPersistencePort companyPersistencePort) {
		this.companyPersistencePort = companyPersistencePort;
	}

	public ClientPersistencePort getClientPersistencePort() {
		return clientPersistencePort;
	}

	public void setClientPersistencePort(ClientPersistencePort clientPersistencePort) {
		this.clientPersistencePort = clientPersistencePort;
	}

	public CreditCardConfigurationPersistencePort getCreditCardConfigurationPersistencePort() {
		return creditCardConfigurationPersistencePort;
	}

	public void setCreditCardConfigurationPersistencePort(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort) {
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
	}

}
