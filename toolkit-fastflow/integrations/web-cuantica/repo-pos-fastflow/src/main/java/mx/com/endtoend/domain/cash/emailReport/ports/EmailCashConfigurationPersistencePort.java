package mx.com.endtoend.domain.cash.emailReport.ports;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface EmailCashConfigurationPersistencePort {

	ResponseModel createEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String companyCode,
			String idOperation);

	ResponseModel updateEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String companyCode,
			String idOperation);

	ResponseModel getEmailReportByCompanyCode(String companyCode, String idOperation);

}
