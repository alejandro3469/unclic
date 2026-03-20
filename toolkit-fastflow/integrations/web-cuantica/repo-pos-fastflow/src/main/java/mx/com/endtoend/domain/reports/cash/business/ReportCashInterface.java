package mx.com.endtoend.domain.reports.cash.business;

import mx.com.endtoend.domain.reports.cash.ports.ReportCashPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportCashInterface {

	ResponseModel generateOpeningReportByEmail(ReportCashPersistencePort reportCashPersistencePort,
			String employeeEmail, String companyCode, String idOperation);

	ResponseModel generateClosingReportByEmail(ReportCashPersistencePort reportCashPersistencePort,
			String employeeEmail, String companyCode, String idOperation);

}
