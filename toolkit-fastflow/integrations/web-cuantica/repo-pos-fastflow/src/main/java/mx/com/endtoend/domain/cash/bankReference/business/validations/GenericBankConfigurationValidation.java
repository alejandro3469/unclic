package mx.com.endtoend.domain.cash.bankReference.business.validations;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class GenericBankConfigurationValidation {

	public String bankValidationOnCreate(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			BankDto bankDto, String companyCode, String idOperation) {
		String validations = "";
		validations = validRequiredField(bankDto, validations);
		validations = validExistsByInstitution(bankConfigurationPersistencePort, bankDto, validations, companyCode,
				idOperation);
		return validations;
	}

	public String bankValidationOnUpdate(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			BankDto bankDto, String companyCode, String idOperation) {
		String validations = "";
		validations = validRequiredField(bankDto, validations);
		validations = validExistsByInstitutionAndIdNot(bankConfigurationPersistencePort, bankDto, validations,
				companyCode, idOperation);
		return validations;
	}

	private String validRequiredField(BankDto bankDto, String validations) {

		validations = bankDto.getCode() != null ? "" : " -CODE IS REQUIRED- ";
		validations = bankDto.getCode() != null
				? bankDto.getCode().isEmpty() ? validations + " -CODE IS REQUIRED- " : ""
				: validations;

		validations = bankDto.getBankingInstitution() != null ? validations
				: validations + " -BANKING INSTITUTION IS REQUIRED- ";

		validations = bankDto.getBankingInstitution() != null
				? bankDto.getBankingInstitution().isEmpty() ? validations + " -BANKING INSTITUTION IS REQUIRED- "
						: validations
				: validations;

		validations = bankDto.getUseType() != null
				? bankDto.getUseType().isEmpty() ? validations + " - USE TYPE IS REQUIRED- " : validations
				: validations + " - USE TYPE IS REQUIRED- ";

		return validations;
	}

	private String validExistsByInstitution(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			BankDto bankDto, String validations, String companyCode, String idOperation) {
		ResponseModel responseGetBank = bankConfigurationPersistencePort
				.findBankByInstitutionAndCompanyCode(bankDto.getBankingInstitution(), companyCode, idOperation);
		BankDto bankSearch = (BankDto) responseGetBank.getData();
		if (bankSearch != null) {
			validations = validations + " -BANKING INSTITUTION EXISTS- ";
		}
		return validations;
	}

	private String validExistsByInstitutionAndIdNot(BankConfigurationPersistencePort bankConfigurationPersistencePort,
			BankDto bankDto, String validations, String companyCode, String idOperation) {
		ResponseModel responseGetBank = bankConfigurationPersistencePort
				.findBankByInstitutionAndIdNotAndCompanyCode(bankDto, companyCode, idOperation);
		BankDto bankSearch = (BankDto) responseGetBank.getData();
		if (bankSearch != null) {
			validations = validations + " -BANKING INSTITUTION ALREADY EXISTS- ";
		}
		return validations;
	}
}
