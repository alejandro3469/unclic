package mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.adapter;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.factory.ClosingReportRepositoryFactory;
import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.GenericClosingOperationReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationSummarySeach;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.ports.ReportClosingOperationPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class ReportClosingOperationJpaAdapter implements ReportClosingOperationPersistencePort {

	@Autowired
	private ClosingReportRepositoryFactory closingReportRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(ReportClosingOperationJpaAdapter.class);

	@Override
	public ResponseModel generateReportByBranchClosingAndCompanyCode(
			BranchClosingOperationReport branchClosingOperationReport, String format, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT generateReportByBranchClosingAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ branchClosingOperationReport: %s , format: %s companyCode: %s ] ",
				idOperation, branchClosingOperationReport.toString(), format, companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateReportByBranchClosing(branchClosingOperationReport, format, idOperation);
	}

	@Override
	public ResponseModel generateReportByGeneralClosingAndCompanyCode(
			GeneralClosingOperationReport generalClosingOperationReport, String format, String companyCode,
			String idOperation) {
		LOG.info(String.format("%s INIT generateReportByGeneralClosingAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ generalClosingOperationReport: %s , format: %s companyCode: %s ] ",
				idOperation, generalClosingOperationReport.toString(), format, companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateReportByGeneralClosing(generalClosingOperationReport, format, idOperation);
	}

	@Override
	public ResponseModel findClosingRecordsByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingOperationReportParamsDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findClosingRecordsByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOperationReportParamsDto: %s, companyCode: %s ] ", idOperation,
				closingOperationReportParamsDto.toString(), companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		return new ResponseModel(repository.findClosingRecordsByParams(closingOperationReportParamsDto, idOperation));
	}

	@Override
	public ResponseModel findIncomeAccountingRecordsByParamsAndCompanyCode(
			ClosingOperationSummarySeach closingOSummarySeach, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findIncomeAccountingRecordsByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOSummarySeach: %s , companyCode: %s ] ", idOperation,
				closingOSummarySeach.toString(), companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		return new ResponseModel(repository.findIncomeAccountingRecordsByParams(closingOSummarySeach, idOperation));
	}

	@Override
	public ResponseModel findClosingAccountingRecordsByParamsAndCompanyCode(
			ClosingOperationSummarySeach closingOSummarySeach, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findClosingAccountingRecordsByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOSummarySeach: %s , companyCode: %s ] ", idOperation,
				closingOSummarySeach.toString(), companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		return new ResponseModel(repository.findClosingAccountingRecordsByParams(closingOSummarySeach, idOperation));
	}

	@Override
	public ResponseModel findClosingRecordsByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findClosingRecordsByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOSummarySeach: %s , companyCode: %s ] ", idOperation,
				closingOSummarySeach.toString(), companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		return new ResponseModel(repository.findClosingRecordsByParams(closingOSummarySeach, idOperation));
	}

	@Override
	public ResponseModel findAccountingTicketReferenceByParamsAndCompanyCode(
			ClosingOperationSummarySeach closingOSummarySeach, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT findAccountingTicketReferenceByParamsAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS:[ closingOSummarySeach: %s , companyCode: %s ] ", idOperation,
				closingOSummarySeach.toString(), companyCode));
		GenericClosingOperationReportRepository repository = closingReportRepositoryFactory
				.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		return new ResponseModel(
				repository.findAccountingTicketReferenceByParamsAndCompanyCode(closingOSummarySeach, idOperation));
	}

}
