package mx.com.endtoend.domain.cash.bankReference.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.bankReference.business.validations.GenericBankConfigurationValidation;
import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class BankConfigurationOne implements BankConfigurationInterface {

	private final Logger LOG = LoggerFactory.getLogger(BankConfigurationOne.class);

	private GenericBankConfigurationValidation bankConfigurationValidation = new GenericBankConfigurationValidation();

	@Override
	public ResponseModel createBank(BankConfigurationPersistencePort bankConfigurationPersistencePort, BankDto bankDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createBank()", idOperation));
		String validations = bankConfigurationValidation.bankValidationOnCreate(bankConfigurationPersistencePort,
				bankDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnCreate = bankConfigurationPersistencePort.createBankByCompanyCode(bankDto, companyCode,
				idOperation);
		BankDto bankCreated = (BankDto) responseOnCreate.getData();
		if (bankCreated == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(bankCreated);
	}

	@Override
	public ResponseModel viewBankById(BankConfigurationPersistencePort bankConfigurationPersistencePort, Long id,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewBankById()", idOperation));
		ResponseModel responseGetBanck = bankConfigurationPersistencePort.getBankByIdAndCompanyCode(id, companyCode,
				idOperation);
		BankDto bank = new BankDto();
		if (responseGetBanck.getData() != null) {
			bank = (BankDto) responseGetBanck.getData();
		}
		return new ResponseModel(bank);
	}

	@Override
	public ResponseModel updateBankById(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			BankDto bankDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateBankById()", idOperation));
		String validations = bankConfigurationValidation.bankValidationOnUpdate(bankConfigurationPersistencePort,
				bankDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnUpdate = bankConfigurationPersistencePort.createBankByCompanyCode(bankDto, companyCode,
				idOperation);
		BankDto bankUpdated = (BankDto) responseOnUpdate.getData();
		if (bankUpdated == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(bankUpdated);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewBankListByEnable(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			boolean active, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewBankListByEnable()", idOperation));
		ResponseModel responseGetList = bankConfigurationPersistencePort.getBankListByCompanyCodeAndEnable(active,
				companyCode, idOperation);
		List<BankDto> bankList = new ArrayList<>();
		if (responseGetList.getData() != null) {
			bankList = (List<BankDto>) responseGetList.getData();
		}
		return new ResponseModel(bankList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewActiveBankListByUseTypeAndEnable(
			BankConfigurationPersistencePort bankConfigurationPersistencePort, String useType, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewActiveBankListByUseTypeAndEnable()", idOperation));
		ResponseModel responseGetList = bankConfigurationPersistencePort
				.getBankListByUseTypeAndCompanyCodeAndEnable(useType, true, companyCode, idOperation);
		List<BankDto> bankList = new ArrayList<>();
		if (responseGetList.getData() != null) {
			bankList = (List<BankDto>) responseGetList.getData();
		}
		return new ResponseModel(bankList);
	}

}
