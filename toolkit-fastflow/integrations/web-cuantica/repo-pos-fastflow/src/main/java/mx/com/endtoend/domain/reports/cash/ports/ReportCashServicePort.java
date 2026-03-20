package mx.com.endtoend.domain.reports.cash.ports;

import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportCashServicePort {

	ResponseModel generateOpeningReportByEmailAndCompanyCode(EmailServicePort emailServicePort, String employeeEmail,
			String method, String companyCode, String idOperation);

	ResponseModel generateClosingReportByEmailAndCompanyCode(EmailServicePort emailServicePort, String employeeEmail,
			String method, String companyCode, String idOperation);

}
