package mx.com.endtoend.domain.cash.emailReport.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.application.cash.EmailCashConfigurationController;
import mx.com.endtoend.domain.cash.emailReport.business.EmailCashConfigurationFactory;
import mx.com.endtoend.domain.cash.emailReport.business.EmailCashConfigurationInterface;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class EmailCashConfigurationImpl implements EmailCashConfigurationServicePort {

	private EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort;

	public EmailCashConfigurationImpl(EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort) {
		this.emailCashConfigurationPersistencePort = emailCashConfigurationPersistencePort;
	}

	private final Logger LOG = LoggerFactory.getLogger(EmailCashConfigurationController.class);

	private EmailCashConfigurationFactory emailCashConfigurationFactory = new EmailCashConfigurationFactory();

	@Override
	public ResponseModel createEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createEmailReportByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ emailReportCashDto: %s , companyCode: %s , method: %s ]", idOperation,
				emailReportCashDto.toString(), companyCode, method));
		EmailCashConfigurationInterface emailCashConfiguration = emailCashConfigurationFactory
				.getImplementationByCode(method);
		if (emailCashConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = emailCashConfiguration.createEmailReportByCompanyCode(
				emailCashConfigurationPersistencePort, emailReportCashDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateEmailReportByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ emailReportCashDto: %s , companyCode: %s , method: %s ]", idOperation,
				emailReportCashDto.toString(), companyCode, method));
		EmailCashConfigurationInterface emailCashConfiguration = emailCashConfigurationFactory
				.getImplementationByCode(method);
		if (emailCashConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = emailCashConfiguration.updateEmailReportByCompanyCode(
				emailCashConfigurationPersistencePort, emailReportCashDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel viewEmailReportByCompanyCode(String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT viewEmailReportByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , method: %s ]", idOperation, companyCode, method));
		EmailCashConfigurationInterface emailCashConfiguration = emailCashConfigurationFactory
				.getImplementationByCode(method);
		if (emailCashConfiguration == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = emailCashConfiguration
				.viewEmailReportByCompanyCode(emailCashConfigurationPersistencePort, companyCode, idOperation);
		return responseFromPersistencePort;
	}

}
