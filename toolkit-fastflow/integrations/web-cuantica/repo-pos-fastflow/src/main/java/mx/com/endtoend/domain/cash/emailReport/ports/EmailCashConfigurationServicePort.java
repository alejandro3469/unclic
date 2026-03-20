package mx.com.endtoend.domain.cash.emailReport.ports;

import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface EmailCashConfigurationServicePort {

	ResponseModel createEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String method,
			String companyCode, String idOperation);

	ResponseModel updateEmailReportByCompanyCode(EmailReportCashDto emailReportCashDto, String method,
			String companyCode, String idOperation);

	ResponseModel viewEmailReportByCompanyCode(String method, String companyCode, String idOperation);

}
