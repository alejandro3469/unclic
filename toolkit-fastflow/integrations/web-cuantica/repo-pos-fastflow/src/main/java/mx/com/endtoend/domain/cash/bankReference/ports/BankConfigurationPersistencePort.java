package mx.com.endtoend.domain.cash.bankReference.ports;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BankConfigurationPersistencePort {

	ResponseModel createBankByCompanyCode(BankDto bankDto, String companyCode, String idOperation);

	ResponseModel updateBankByCompanyCode(BankDto bankDto, String companyCode, String idOperation);

	ResponseModel getBankByIdAndCompanyCode(Long id, String companyCode, String idOperation);

	ResponseModel getBankListByCompanyCodeAndEnable(boolean active, String companyCode, String idOperation);

	ResponseModel getBankListByUseTypeAndCompanyCodeAndEnable(String useType, boolean active, String companyCode,
			String idOperation);

	ResponseModel findBankByInstitutionAndCompanyCode(String institutionName, String companyCode, String idOperation);

	ResponseModel findBankByInstitutionAndIdNotAndCompanyCode(BankDto bankDto, String companyCode, String idOperation);

}
