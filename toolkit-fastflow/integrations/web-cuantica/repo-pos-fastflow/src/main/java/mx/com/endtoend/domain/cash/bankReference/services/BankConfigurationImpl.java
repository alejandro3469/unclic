package mx.com.endtoend.domain.cash.bankReference.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.bankReference.business.BankConfigurationFactory;
import mx.com.endtoend.domain.cash.bankReference.business.BankConfigurationInterface;
import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class BankConfigurationImpl implements BankConfigurationServicePort {

	private BankConfigurationPersistencePort bankConfigurationPersistencePort;

	public BankConfigurationImpl(BankConfigurationPersistencePort bankConfigurationPersistencePort) {
		this.bankConfigurationPersistencePort = bankConfigurationPersistencePort;
	}

	private BankConfigurationFactory bankConfigurationFactory = new BankConfigurationFactory();

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationImpl.class);

	@Override
	public ResponseModel createBankByCompanyCode(BankDto bankDto, String method, String companyCode,
			String idOperation) {
		BankConfigurationInterface bankConfiguration = bankConfigurationFactory.getImplementationByCode(method);
		if (bankConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = bankConfiguration.createBank(bankConfigurationPersistencePort,
				bankDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewBankByIdAndCompanyCode(Long id, String method, String companyCode, String idOperation) {
		BankConfigurationInterface bankConfiguration = bankConfigurationFactory.getImplementationByCode(method);
		if (bankConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = bankConfiguration.viewBankById(bankConfigurationPersistencePort, id,
				companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateBankByCompanyCodeAndId(BankDto bankDto, String method, String companyCode,
			String idOperation) {
		BankConfigurationInterface bankConfiguration = bankConfigurationFactory.getImplementationByCode(method);
		if (bankConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = bankConfiguration.updateBankById(bankConfigurationPersistencePort,
				bankDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewBankListByEnableByCompanyCode(boolean active, String method, String companyCode,
			String idOperation) {
		BankConfigurationInterface bankConfiguration = bankConfigurationFactory.getImplementationByCode(method);
		if (bankConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = bankConfiguration
				.viewBankListByEnable(bankConfigurationPersistencePort, active, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewActuveBankListByUseTypeAndEnableByCompanyCode(String useType, String method,
			String companyCode, String idOperation) {
		BankConfigurationInterface bankConfiguration = bankConfigurationFactory.getImplementationByCode(method);
		if (bankConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = bankConfiguration.viewActiveBankListByUseTypeAndEnable(
				bankConfigurationPersistencePort, useType, companyCode, idOperation);
		return responseFromPersistencePort;
	}

}
