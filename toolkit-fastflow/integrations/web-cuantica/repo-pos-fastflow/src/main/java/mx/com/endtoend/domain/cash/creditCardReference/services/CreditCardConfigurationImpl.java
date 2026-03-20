package mx.com.endtoend.domain.cash.creditCardReference.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.creditCardReference.business.CreditCardConfigurationFactory;
import mx.com.endtoend.domain.cash.creditCardReference.business.CreditCardConfigurationInterface;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CreditCardConfigurationImpl implements CreditCardConfigurationServicePort {

	private CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort;

	public CreditCardConfigurationImpl(CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort) {
		this.creditCardConfigurationPersistencePort = creditCardConfigurationPersistencePort;
	}

	private CreditCardConfigurationFactory creditCardConfigurationFactory = new CreditCardConfigurationFactory();

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfigurationImpl.class);

	@Override
	public ResponseModel createCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String method,
			String companyCode, String user, String idOperation) {
		LOG.info(String.format("%s INIT createCreditCardReferenceByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ creditCardDto: %s , companyCode: %s , method: %s ]", idOperation,
				creditCardDto.toString(), companyCode, method));
		CreditCardConfigurationInterface creditCardConfiguration = creditCardConfigurationFactory
				.getImplementationByCode(method);
		if (creditCardConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = creditCardConfiguration.createCreditCardReference(
				creditCardConfigurationPersistencePort, creditCardDto, companyCode, user, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewCreditCardReferenceByIdAndCompanyCode(Long id, String method, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewCreditCardReferenceByIdAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , companyCode: %s , method: %s ]", idOperation, id.toString(),
				companyCode, method));
		CreditCardConfigurationInterface creditCardConfiguration = creditCardConfigurationFactory
				.getImplementationByCode(method);
		if (creditCardConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = creditCardConfiguration
				.viewCreditCardReferenceById(creditCardConfigurationPersistencePort, id, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateCreditCardReferenceByCompanyCodeAndId(CreditCardDto creditCardDto, String method,
			String companyCode, String user, String idOperation) {
		LOG.info(String.format("%s INIT updateCreditCardReferenceByCompanyCodeAndId() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ creditCardDto: %s , companyCode: %s , method: %s ]", idOperation,
				creditCardDto.toString(), companyCode, method));
		CreditCardConfigurationInterface creditCardConfiguration = creditCardConfigurationFactory
				.getImplementationByCode(method);
		if (creditCardConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = creditCardConfiguration.updateCreditCardReferenceById(
				creditCardConfigurationPersistencePort, creditCardDto, companyCode, user, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewCreditCardReferenceListByEnableByCompanyCode(boolean enabled, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewCreditCardReferenceListByEnableByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ enabled: %b , companyCode: %s , method: %s ]", idOperation, enabled,
				companyCode, method));
		CreditCardConfigurationInterface creditCardConfiguration = creditCardConfigurationFactory
				.getImplementationByCode(method);
		if (creditCardConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = creditCardConfiguration.viewCreditCardReferenceListByEnable(
				creditCardConfigurationPersistencePort, enabled, companyCode, idOperation);
		return responseFromPersistencePort;
	}

}
