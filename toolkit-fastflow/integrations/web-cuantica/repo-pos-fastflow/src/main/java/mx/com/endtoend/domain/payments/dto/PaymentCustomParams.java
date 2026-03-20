package mx.com.endtoend.domain.payments.dto;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.creditNote.ports.CreditNotePersistencePort;
import mx.com.endtoend.infrastructure.services.bds.BDSServicePort;
import mx.com.endtoend.infrastructure.services.jde.payments.common.serviceport.PaymentJDEServicePort;

/**
 * Clase para el paso de parámetros entre las capas del sistema
 * 
 * @author ddcasas
 *
 */
public class PaymentCustomParams {

	private PaymentJDEServicePort paymentJDEServicePort;

	private AccountingRecordPersistencePort accountingRecordPersistencePort;

	private CreditNotePersistencePort creditNotePersistencePort;

	private CompanyPersistencePort companyPersistencePort;

	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	private BDSServicePort bdsServicePort;

	private String companyCode;

	private String idOperation;

	public PaymentCustomParams(PaymentJDEServicePort paymentJDEServicePort,
			AccountingRecordPersistencePort accountingRecordPersistencePort,
			CreditNotePersistencePort creditNotePersistencePort, CompanyPersistencePort companyPersistencePort,
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort,
			BDSServicePort bdsServicePort, String companyCode, String idOperation) {
		super();
		this.paymentJDEServicePort = paymentJDEServicePort;
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
		this.creditNotePersistencePort = creditNotePersistencePort;
		this.companyPersistencePort = companyPersistencePort;
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
		this.bdsServicePort = bdsServicePort;
		this.companyCode = companyCode;
		this.idOperation = idOperation;
	}

	public PaymentJDEServicePort getPaymentJDEServicePort() {
		return paymentJDEServicePort;
	}

	public AccountingRecordPersistencePort getAccountingRecordPersistencePort() {
		return accountingRecordPersistencePort;
	}

	public CreditNotePersistencePort getCreditNotePersistencePort() {
		return creditNotePersistencePort;
	}

	public void setPaymentJDEServicePort(PaymentJDEServicePort paymentJDEServicePort) {
		this.paymentJDEServicePort = paymentJDEServicePort;
	}

	public void setAccountingRecordPersistencePort(AccountingRecordPersistencePort accountingRecordPersistencePort) {
		this.accountingRecordPersistencePort = accountingRecordPersistencePort;
	}

	public void setCreditNotePersistencePort(CreditNotePersistencePort creditNotePersistencePort) {
		this.creditNotePersistencePort = creditNotePersistencePort;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public String getIdOperation() {
		return idOperation;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public void setIdOperation(String idOperation) {
		this.idOperation = idOperation;
	}

	public CompanyPersistencePort getCompanyPersistencePort() {
		return companyPersistencePort;
	}

	public void setCompanyPersistencePort(CompanyPersistencePort companyPersistencePort) {
		this.companyPersistencePort = companyPersistencePort;
	}

	public CreditCardConfigurationPersistencePort getCreditCardConfigurationPersistencePort() {
		return creditCardConfigurationPersistencePort;
	}

	public void setCreditCardConfigurationPersistencePort(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort) {
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
	}

	public BDSServicePort getBdsServicePort() {
		return bdsServicePort;
	}

	public void setBdsServicePort(BDSServicePort bdsServicePort) {
		this.bdsServicePort = bdsServicePort;
	}

}
