package mx.com.endtoend.domain.cash.bankReference.business;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BankConfigurationInterface {

	ResponseModel createBank(BankConfigurationPersistencePort bankConfigurationPersistencePort, BankDto bankDto,
			String companyCode, String idOperation);

	ResponseModel viewBankById(BankConfigurationPersistencePort bankConfigurationPersistencePort, Long id,
			String companyCode, String idOperation);

	ResponseModel updateBankById(BankConfigurationPersistencePort bankConfigurationPersistencePort, BankDto bankDto,
			String companyCode, String idOperation);

	ResponseModel viewBankListByEnable(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			boolean active, String companyCode, String idOperation);

	ResponseModel viewActiveBankListByUseTypeAndEnable(
			BankConfigurationPersistencePort bankConfigurationPersistencePort, String useType, String companyCode,
			String idOperation);

}
