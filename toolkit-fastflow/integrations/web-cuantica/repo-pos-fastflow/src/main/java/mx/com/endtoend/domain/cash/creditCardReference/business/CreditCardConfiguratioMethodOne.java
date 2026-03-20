package mx.com.endtoend.domain.cash.creditCardReference.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.creditCardReference.business.validations.GenericCreditCardConfigurationValidation;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase con la implementación de la lógica de negocio correspondiente al método
 * uno para la adminisntración del catálogo de referencia de tarjetas de crédito
 * en el cobro
 * 
 * @author ddcasas
 *
 */
public class CreditCardConfiguratioMethodOne implements CreditCardConfigurationInterface {

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfiguratioMethodOne.class);

	private GenericCreditCardConfigurationValidation creditCardConfigurationValidation = new GenericCreditCardConfigurationValidation();

	/**
	 * Método para la ceación de referencias de tarjetas de crédito
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return
	 * 
	 */
	@Override
	public ResponseModel createCreditCardReference(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String user, String idOperation) {
		LOG.info(String.format("%s INIT createCreditCardReference()", idOperation));
		String validations = creditCardConfigurationValidation.cardReferenceValidationOnCreate(
				creditCardConfigurationPersistencePort, creditCardDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnCreate = creditCardConfigurationPersistencePort
				.createCreditCardReferenceByCompanyCode(creditCardDto, companyCode, idOperation);
		creditCardDto = (CreditCardDto) responseOnCreate.getData();
		if (creditCardDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		saveCreditCardChanges(creditCardConfigurationPersistencePort, companyCode, creditCardDto.getId(), user,
				idOperation);
		return new ResponseModel(creditCardDto);
	}

	/**
	 * Método para la obtención de referencias de tarjetas de crédito por su id
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param id
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return
	 */
	@Override
	public ResponseModel viewCreditCardReferenceById(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, Long id, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT CreditCardConfigurationPersistencePort()", idOperation));
		ResponseModel responseViewDetail = creditCardConfigurationPersistencePort
				.getCreditCardReferenceByIdAndCompanyCode(id, companyCode, idOperation);
		CreditCardDto creditCard = (CreditCardDto) responseViewDetail.getData();
		if (creditCard == null) {
			LOG.warn(String.format("%s DATA NOT FOUND", idOperation));
			creditCard = new CreditCardDto();
		}
		return new ResponseModel(creditCard);
	}

	/**
	 * Método para la actualización de referencias de tarjetas de crédito
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return
	 * 
	 */
	@Override
	public ResponseModel updateCreditCardReferenceById(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, CreditCardDto creditCardDto,
			String companyCode, String user, String idOperation) {
		LOG.info(String.format("%s INIT updateCreditCardReferenceById()", idOperation));
		String validations = creditCardConfigurationValidation.cardReferenceValidationOnUpdate(
				creditCardConfigurationPersistencePort, creditCardDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnUpdate = creditCardConfigurationPersistencePort
				.updateCreditCardReferenceByCompanyCode(creditCardDto, companyCode, idOperation);
		creditCardDto = (CreditCardDto) responseOnUpdate.getData();
		if (creditCardDto == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		saveCreditCardChanges(creditCardConfigurationPersistencePort, companyCode, creditCardDto.getId(), user,
				idOperation);
		return new ResponseModel(creditCardDto);
	}

	/**
	 * Método para el registro de cambios relizados en las comisiones configuradas a
	 * la tarjeta de cédito
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param creditCardDto
	 * @param companyCode
	 * @param user
	 * @param idOperation
	 */
	private void saveCreditCardChanges(CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort,
			String companyCode, Long id, String user, String idOperation) {
		CreditCardDto creditCardUpdated = (CreditCardDto) creditCardConfigurationPersistencePort
				.getCreditCardReferenceByIdAndCompanyCode(id, companyCode, idOperation).getData();
		Date modifiedDate = new Date();
		List<CreditCardHistoryChangeDto> creditCardHistoryChangeList = new ArrayList<>();
		for (PaymentOptionDto paymentOption : creditCardUpdated.getPaymentOptionDetail()) {
			creditCardHistoryChangeList.add(new CreditCardHistoryChangeDto(null, creditCardUpdated.getId(),
					creditCardUpdated.getBankingInstitution(), creditCardUpdated.getType(), paymentOption.getPeriod(),
					paymentOption.getCommission(), paymentOption.getIsEnable(), modifiedDate, user));
		}

		creditCardConfigurationPersistencePort.saveCreditCardChangesByCompanyCode(creditCardHistoryChangeList,
				companyCode, idOperation);

	}

	/**
	 * Método para la recuperación de los registros de referencias de tarjetas de
	 * crédito por su estado (activo o inactivo)
	 * 
	 * @param creditCardConfigurationPersistencePort
	 * @param enabled
	 * @param companyCode
	 * @param idOperation
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel viewCreditCardReferenceListByEnable(
			CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort, boolean enabled,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewCreditCardReferenceListByEnable()", idOperation));
		ResponseModel responseList = creditCardConfigurationPersistencePort
				.getCreditCardReferenceListByCompanyCodeAndEnable(enabled, companyCode, idOperation);
		List<CreditCardDto> creditCardList = (List<CreditCardDto>) responseList.getData();
		if (creditCardList == null) {
			LOG.warn(String.format("%s RETURN EMPTY LIST", idOperation));
			creditCardList = new ArrayList<>();
		}
		return new ResponseModel(creditCardList);
	}

}
