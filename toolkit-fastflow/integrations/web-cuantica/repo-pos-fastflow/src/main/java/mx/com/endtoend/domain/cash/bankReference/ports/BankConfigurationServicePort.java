package mx.com.endtoend.domain.cash.bankReference.ports;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface BankConfigurationServicePort {

	ResponseModel createBankByCompanyCode(BankDto bankDto, String method, String companyCode, String idOperation);

	ResponseModel viewBankByIdAndCompanyCode(Long id, String method, String companyCode, String idOperation);

	ResponseModel updateBankByCompanyCodeAndId(BankDto bankDto, String method, String companyCode, String idOperation);

	ResponseModel viewBankListByEnableByCompanyCode(boolean active, String method, String companyCode,
			String idOperation);

	ResponseModel viewActuveBankListByUseTypeAndEnableByCompanyCode(String useType, String method, String companyCode,
			String idOperation);

}
