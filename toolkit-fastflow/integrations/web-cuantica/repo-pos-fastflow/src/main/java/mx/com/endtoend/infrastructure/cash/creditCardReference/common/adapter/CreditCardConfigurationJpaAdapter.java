package mx.com.endtoend.infrastructure.cash.creditCardReference.common.adapter;

import java.util.List;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.factory.CreditCardConfigurationRepositoryFactory;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.persistence.GenericCreditCardConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */
public class CreditCardConfigurationJpaAdapter implements CreditCardConfigurationPersistencePort {

	@Autowired
	private CreditCardConfigurationRepositoryFactory creditCardConfigurationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfigurationJpaAdapter.class);

	@Transactional
	@Override
	public ResponseModel createCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT createCreditCardReferenceByCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);

		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditCardDto creditCardCreated = repository.createCreditCardReference(creditCardDto, idOperation);
		return new ResponseModel(creditCardCreated);
	}

	@Transactional
	@Override
	public ResponseModel updateCreditCardReferenceByCompanyCode(CreditCardDto creditCardDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updateCreditCardReferenceByCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditCardDto creditCardUpdated = repository.updateCreditCardReference(creditCardDto, idOperation);
		return new ResponseModel(creditCardUpdated);
	}

	@Transactional
	@Override
	public ResponseModel getCreditCardReferenceByIdAndCompanyCode(Long id, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getCreditCardReferenceByIdAndCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditCardDto creditCard = repository.getCreditCardReferenceById(id, idOperation);
		return new ResponseModel(creditCard);
	}

	@Transactional
	@Override
	public ResponseModel getCreditCardReferenceListByCompanyCodeAndEnable(boolean enable, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT getCreditCardReferenceListByCompanyCodeAndEnable()", idOperation));
		LOG.info(String.format("%s PARAMS:[ enable: %b , companyCode: %s ] ", idOperation, enable, companyCode));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		List<CreditCardDto> creditCardList = repository.getCreditCardReferenceListByEnable(enable, idOperation);
		return new ResponseModel(creditCardList);
	}

	@Transactional
	@Override
	public ResponseModel findCreditCardReferenceByCodeOrInstitutionOrTypeAndCompanyCode(CreditCardDto creditCardDto,
			String companyCode, String idOperation) {
		LOG.info(
				String.format("%s INIT findCreditCardReferenceByCodeOrInstitutionOrTypeAndCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditCardDto creditCard = repository.findCreditCardReferenceByCodeOrInstitutionOrType(creditCardDto,
				idOperation);
		return new ResponseModel(creditCard);
	}

	@Transactional
	@Override
	public ResponseModel findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNotAndCompanyCode(
			CreditCardDto creditCardDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findCreditCardReferenceByCodeOrInstitutionAndIdNotAndCompanyCode()",
				idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		CreditCardDto creditCard = repository.findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNot(creditCardDto,
				idOperation);
		return new ResponseModel(creditCard);
	}

	@Transactional
	@Override
	public ResponseModel findPaymentOptionByPeriodAndInstitutionAndTypeCompanyCode(String period, String institution,
			String type, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findPaymentOptionByPeriodAndInstitutionAndCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		PaymentOptionDto paymentOption = repository.findPaymentOptionByPeriodAndInstitutionAndType(period, institution,
				type, idOperation);
		return new ResponseModel(paymentOption);
	}

	@Transactional
	@Override
	public ResponseModel findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNotAndCompanyCode(Long idPaymentOption,
			String period, String institution, String type, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findPaymentOptionByPeriodAndInstitutionAndIdNotAndCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		PaymentOptionDto paymentOption = repository.findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNot(
				idPaymentOption, period, institution, type, idOperation);
		return new ResponseModel(paymentOption);
	}

	@Transactional
	@Override
	public void saveCreditCardChangesByCompanyCode(List<CreditCardHistoryChangeDto> creditCardHistoryChangeList,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT saveCreditCardChangesByCompanyCode()", idOperation));
		GenericCreditCardConfigurationPersistence repository = creditCardConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		repository.saveCreditCardChanges(creditCardHistoryChangeList, idOperation);
	}

}
