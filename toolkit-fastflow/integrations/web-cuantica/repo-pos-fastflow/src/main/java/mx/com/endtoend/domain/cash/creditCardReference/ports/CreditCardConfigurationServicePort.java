package mx.com.endtoend.domain.cash.creditCardReference.ports;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CreditCardConfigurationServicePort {

	ResponseModel createCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String method, String companyCode,
			String user, String idOperation);

	ResponseModel viewCreditCardReferenceByIdAndCompanyCode(Long id, String method, String companyCode,
			String idOperation);

	ResponseModel updateCreditCardReferenceByCompanyCodeAndId(CreditCardDto creditCardDto, String method,
			String companyCode, String user, String idOperation);

	ResponseModel viewCreditCardReferenceListByEnableByCompanyCode(boolean enabled, String method, String companyCode,
			String idOperation);

}