package mx.com.endtoend.domain.creditNote.dto;

import java.math.BigDecimal;

import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;

public class CreditNoteCustomParams {

	private PaymentJDEServicePort paymentJDEServicePort;

	private OrderPersistencePort orderPersistencePort;

	private OrderConfigurationPersistencePort orderConfigurationPersistencePort;

	private CreditNotePersistencePort creditNotePersistencePort;

	private PaymentPersistencePort paymentPersistencePort;

	private OrderDto orderDto;

	private BigDecimal orderNumber;

	private String orderCode;

	private BigDecimal folio;

	private String creditNoteCode;

	private String employeeEmail;

	public CreditNoteCustomParams(PaymentJDEServicePort paymentJDEServicePort,
			OrderPersistencePort orderPersistencePort,
			OrderConfigurationPersistencePort orderConfigurationPersistencePort,
			PaymentPersistencePort paymentPersistencePort) {
		super();
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderPersistencePort = orderPersistencePort;
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
		this.paymentPersistencePort = paymentPersistencePort;
	}

	public CreditNoteCustomParams(PaymentPersistencePort paymentPersistencePort,
			OrderPersistencePort orderPersistencePort, PaymentJDEServicePort paymentJDEServicePort,
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, OrderDto orderDto,
			String employeeEmail) {
		super();
		this.paymentPersistencePort = paymentPersistencePort;
		this.orderPersistencePort = orderPersistencePort;
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
		this.orderDto = orderDto;
		this.employeeEmail = employeeEmail;
	}

	public CreditNoteCustomParams(PaymentPersistencePort paymentPersistencePort,
			OrderPersistencePort orderPersistencePort, PaymentJDEServicePort paymentJDEServicePort,
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, BigDecimal orderNumber,
			String orderCode, String employeeEmail) {
		super();
		this.paymentPersistencePort = paymentPersistencePort;
		this.orderPersistencePort = orderPersistencePort;
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
	}

	public CreditNoteCustomParams(OrderPersistencePort orderPersistencePort,
			PaymentJDEServicePort paymentJDEServicePort,
			OrderConfigurationPersistencePort orderConfigurationPersistencePort, BigDecimal orderNumber,
			String orderCode, BigDecimal folio, String creditNoteCode, String employeeEmail) {
		super();
		this.orderPersistencePort = orderPersistencePort;
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
		this.orderNumber = orderNumber;
		this.orderCode = orderCode;
		this.folio = folio;
		this.creditNoteCode = creditNoteCode;
	}

	public PaymentJDEServicePort getPaymentJDEServicePort() {
		return paymentJDEServicePort;
	}

	public OrderConfigurationPersistencePort getOrderConfigurationPersistencePort() {
		return orderConfigurationPersistencePort;
	}

	public OrderDto getOrderDto() {
		return orderDto;
	}

	public BigDecimal getOrderNumber() {
		return orderNumber;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public BigDecimal getFolio() {
		return folio;
	}

	public String getCreditNoteCode() {
		return creditNoteCode;
	}

	public void setPaymentJDEServicePort(PaymentJDEServicePort paymentJDEServicePort) {
		this.paymentJDEServicePort = paymentJDEServicePort;
	}

	public void setOrderConfigurationPersistencePort(
			OrderConfigurationPersistencePort orderConfigurationPersistencePort) {
		this.orderConfigurationPersistencePort = orderConfigurationPersistencePort;
	}

	public void setOrderDto(OrderDto orderDto) {
		this.orderDto = orderDto;
	}

	public void setOrderNumber(BigDecimal orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setFolio(BigDecimal folio) {
		this.folio = folio;
	}

	public void setCreditNoteCode(String creditNoteCode) {
		this.creditNoteCode = creditNoteCode;
	}

	public CreditNotePersistencePort getCreditNotePersistencePort() {
		return creditNotePersistencePort;
	}

	public void setCreditNotePersistencePort(CreditNotePersistencePort creditNotePersistencePort) {
		this.creditNotePersistencePort = creditNotePersistencePort;
	}

	public OrderPersistencePort getOrderPersistencePort() {
		return orderPersistencePort;
	}

	public void setOrderPersistencePort(OrderPersistencePort orderPersistencePort) {
		this.orderPersistencePort = orderPersistencePort;
	}

	public PaymentPersistencePort getPaymentPersistencePort() {
		return paymentPersistencePort;
	}

	public void setPaymentPersistencePort(PaymentPersistencePort paymentPersistencePort) {
		this.paymentPersistencePort = paymentPersistencePort;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

}
