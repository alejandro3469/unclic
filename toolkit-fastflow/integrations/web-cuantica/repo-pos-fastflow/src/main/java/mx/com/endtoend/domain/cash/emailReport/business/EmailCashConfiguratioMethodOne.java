package mx.com.endtoend.domain.cash.emailReport.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.cash.creditCardReference.business.CreditCardConfiguratioMethodOne;
import mx.com.endtoend.domain.cash.emailReport.business.validations.GenericEmailCashConfigurationValidation;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class EmailCashConfiguratioMethodOne implements EmailCashConfigurationInterface {

	private GenericEmailCashConfigurationValidation cashConfigurationValidation = new GenericEmailCashConfigurationValidation();

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfiguratioMethodOne.class);

	@Override
	public ResponseModel createEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			EmailReportCashDto emailReportCashDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createEmailReportByCompanyCode()", idOperation));
		String validations = cashConfigurationValidation.emailReportValidation(emailCashConfigurationPersistencePort,
				emailReportCashDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnCreate = emailCashConfigurationPersistencePort
				.createEmailReportByCompanyCode(emailReportCashDto, companyCode, idOperation);
		EmailReportCashDto emailReportCreated = (EmailReportCashDto) responseOnCreate.getData();
		if (emailReportCreated == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(emailReportCreated);

	}

	@Override
	public ResponseModel updateEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			EmailReportCashDto emailReportCashDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateEmailReportByCompanyCode()", idOperation));
		String validations = cashConfigurationValidation.emailReportValidation(emailCashConfigurationPersistencePort,
				emailReportCashDto, companyCode, idOperation);
		if (!validations.isEmpty()) {
			LOG.warn(String.format("%s BAD VALIDATIONS: %s", idOperation, validations));
			throw new ValidationError(validations);
		}
		ResponseModel responseOnUpdate = emailCashConfigurationPersistencePort
				.updateEmailReportByCompanyCode(emailReportCashDto, companyCode, idOperation);
		EmailReportCashDto emailReportpdated = (EmailReportCashDto) responseOnUpdate.getData();
		if (emailReportpdated == null) {
			LOG.error(String.format("%s ERROR IN SAVE DATA", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(emailReportpdated);
	}

	@Override
	public ResponseModel viewEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT viewEmailReportByCompanyCode()", idOperation));
		ResponseModel responseView = emailCashConfigurationPersistencePort.getEmailReportByCompanyCode(companyCode,
				idOperation);
		EmailReportCashDto emailReport = (EmailReportCashDto) responseView.getData();
		if (emailReport == null) {
			LOG.error(String.format("%s DATA NOT FOUND", idOperation));
			return new ResponseModel(new EmailReportCashDto(null, ""));
		}
		return new ResponseModel(emailReport);
	}

}
