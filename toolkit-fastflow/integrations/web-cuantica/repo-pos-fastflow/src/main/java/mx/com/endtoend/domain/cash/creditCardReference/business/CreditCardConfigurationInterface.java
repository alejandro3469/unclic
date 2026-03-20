package mx.com.endtoend.domain.cash.creditCardReference.business;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CreditCardConfigurationInterface {

	ResponseModel createCreditCardReference(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String user, String idOperation);

	ResponseModel viewCreditCardReferenceById(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, Long id, String companyCode,
			String idOperation);

	ResponseModel updateCreditCardReferenceById(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String user, String idOperation);

	ResponseModel viewCreditCardReferenceListByEnable(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, boolean enabled,
			String companyCode, String idOperation);

}
