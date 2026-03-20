package mx.com.endtoend.domain.cash.emailReport.business;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface EmailCashConfigurationInterface {

	ResponseModel createEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			EmailReportCashDto emailReportCashDto, String companyCode, String idOperation);

	ResponseModel updateEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			EmailReportCashDto emailReportCashDto, String companyCode, String idOperation);

	ResponseModel viewEmailReportByCompanyCode(
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort, String companyCode,
			String idOperation);

}
