package mx.com.endtoend.infrastructure.cash.bankReference.common.adapter;

import java.util.List;

import mx.com.endtoend.infrastructure.cash.bankReference.common.factory.BankConfigurationRepositoryFactory;
import mx.com.endtoend.infrastructure.cash.bankReference.common.persistence.GenericBankConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class BankConfigurationJpaAdapter implements BankConfigurationPersistencePort {

	@Autowired
	private BankConfigurationRepositoryFactory bankConfigurationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationJpaAdapter.class);

	@Override
	public ResponseModel createBankByCompanyCode(BankDto bankDto, String companyCode, String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BankDto bankCreated = repository.createBank(bankDto, idOperation);
		return new ResponseModel(bankCreated);
	}

	@Override
	public ResponseModel updateBankByCompanyCode(BankDto bankDto, String companyCode, String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BankDto bankUpdated = repository.updateBank(bankDto, idOperation);
		return new ResponseModel(bankUpdated);
	}

	@Override
	public ResponseModel getBankByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BankDto bank = repository.getBankById(id, idOperation);
		return new ResponseModel(bank);
	}

	@Override
	public ResponseModel getBankListByCompanyCodeAndEnable(boolean active, String companyCode, String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<BankDto> bankList = repository.getBankListByEnable(active, idOperation);
		return new ResponseModel(bankList);
	}

	@Override
	public ResponseModel findBankByInstitutionAndCompanyCode(String institutionName, String companyCode,
			String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BankDto bank = repository.findBankByInstitution(institutionName, idOperation);
		return new ResponseModel(bank);
	}

	@Override
	public ResponseModel findBankByInstitutionAndIdNotAndCompanyCode(BankDto bankDto, String companyCode,
			String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		BankDto bank = repository.findBankByInstitutionAndIdNot(bankDto, idOperation);
		return new ResponseModel(bank);
	}

	@Override
	public ResponseModel getBankListByUseTypeAndCompanyCodeAndEnable(String useType, boolean active, String companyCode,
			String idOperation) {
		GenericBankConfigurationPersistence repository = bankConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<BankDto> bankList = repository.getBankListByUseTypeAndEnable(useType, active, idOperation);
		return new ResponseModel(bankList);
	}

}
