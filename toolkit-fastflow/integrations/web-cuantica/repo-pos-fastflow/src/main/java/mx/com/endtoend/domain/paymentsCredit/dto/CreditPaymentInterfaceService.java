package mx.com.endtoend.domain.paymentsCredit.dto;

import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditPersistencePort;

/**
 * Clase contenedora de interfaces de uso común para la gestión de cobros con
 * crédito y variables de uso general del sistema
 * 
 * @author ddcasas
 */
public class CreditPaymentInterfaceService {

	private PaymentCreditPersistencePort paymentCreditPersistencePort;

	private PaymentPersistencePort paymentPersistencePort;

	private String companyCode;

	private String idOperation;

	public CreditPaymentInterfaceService(PaymentPersistencePort paymentPersistencePort, String companyCode,
			String idOperation) {
		super();
		this.paymentPersistencePort = paymentPersistencePort;
		this.companyCode = companyCode;
		this.idOperation = idOperation;
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

	public PaymentCreditPersistencePort getPaymentCreditPersistencePort() {
		return paymentCreditPersistencePort;
	}

	public void setPaymentCreditPersistencePort(PaymentCreditPersistencePort paymentCreditPersistencePort) {
		this.paymentCreditPersistencePort = paymentCreditPersistencePort;
	}

	public PaymentPersistencePort getPaymentPersistencePort() {
		return paymentPersistencePort;
	}

	public void setPaymentPersistencePort(PaymentPersistencePort paymentPersistencePort) {
		this.paymentPersistencePort = paymentPersistencePort;
	}

}
