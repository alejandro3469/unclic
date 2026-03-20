package mx.com.endtoend.infrastructure.cash.emailReport.common.adapter;

import mx.com.endtoend.infrastructure.cash.emailReport.common.factory.EmailCashConfigurationRepositoryFactory;
import mx.com.endtoend.infrastructure.cash.emailReport.common.persistence.GenericEmailCashConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class EmailCashConfigurationJpaAdapter implements EmailCashConfigurationPersistencePort {

	@Autowired
	private EmailCashConfigurationRepositoryFactory emailCashConfigurationRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(EmailCashConfigurationJpaAdapter.class);

	@Override
	public ResponseModel createEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT createEmailReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ emailReportCashDto: %s , companyCode: %s ] ", idOperation,
				emailReportCashDto.toString(), companyCode));
		GenericEmailCashConfigurationPersistence repository = emailCashConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmailReportCashDto EmailReportCash = repository.createEmailReport(emailReportCashDto, idOperation);
		return new ResponseModel(EmailReportCash);
	}

	@Override
	public ResponseModel updateEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT updateEmailReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ emailReportCashDto: %s , companyCode: %s ] ", idOperation,
				emailReportCashDto.toString(), companyCode));
		GenericEmailCashConfigurationPersistence repository = emailCashConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmailReportCashDto EmailReportCash = repository.updateEmailReport(emailReportCashDto, idOperation);
		return new ResponseModel(EmailReportCash);
	}

	@Override
	public ResponseModel getEmailReportByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getEmailReportByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ companyCode: %s ] ", idOperation, companyCode));
		GenericEmailCashConfigurationPersistence repository = emailCashConfigurationRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		EmailReportCashDto EmailReportCash = repository.getEmailReport(idOperation);
		return new ResponseModel(EmailReportCash);
	}

}
