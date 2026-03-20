package mx.com.endtoend.domain.reports.cash.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.reports.cash.business.ReportCashFactory;
import mx.com.endtoend.domain.reports.cash.business.ReportCashInterface;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashPersistencePort;
import mx.com.endtoend.domain.reports.cash.ports.ReportCashServicePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.email.EmailServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportCashServiceImpl implements ReportCashServicePort {

	private ReportCashPersistencePort reportCashPersistencePort;

	public ReportCashServiceImpl(ReportCashPersistencePort reportCashPersistencePort) {
		this.reportCashPersistencePort = reportCashPersistencePort;
	}

	private ReportCashFactory reportCashFactory = new ReportCashFactory();

	private final Logger LOG = LoggerFactory.getLogger(ReportCashServiceImpl.class);

	@Override
	public ResponseModel generateOpeningReportByEmailAndCompanyCode(EmailServicePort emailServicePort,
			String employeeEmail, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateOpeningReportByEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ employeeEmail: %s , method: %s, companyCode: %s ]", idOperation,
				employeeEmail, method, companyCode));
		ReportCashInterface reportCash = reportCashFactory.getImplementationByCode(method, emailServicePort);
		if (reportCash == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel response = reportCash.generateOpeningReportByEmail(reportCashPersistencePort, employeeEmail,
				companyCode, idOperation);
		return response;
	}

	@Override
	public ResponseModel generateClosingReportByEmailAndCompanyCode(EmailServicePort emailServicePort,
			String employeeEmail, String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateClosingReportByEmailAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ employeeEmail: %s , method: %s, companyCode: %s ]", idOperation,
				employeeEmail, method, companyCode));
		ReportCashInterface reportCash = reportCashFactory.getImplementationByCode(method, emailServicePort);
		if (reportCash == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel response = reportCash.generateClosingReportByEmail(reportCashPersistencePort, employeeEmail,
				companyCode, idOperation);
		return response;
	}
}
