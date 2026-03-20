package mx.com.endtoend.domain.reports.sales.closingOperation.ports;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationSummarySeach;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingOperationReport;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportClosingOperationPersistencePort {

	ResponseModel generateReportByBranchClosingAndCompanyCode(BranchClosingOperationReport branchClosingOperationReport,
			String format, String companyCode, String idOperation);

	ResponseModel generateReportByGeneralClosingAndCompanyCode(
			GeneralClosingOperationReport generalClosingOperationReport, String format, String companyCode,
			String idOperation);

	ResponseModel findClosingRecordsByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingOperationReportParamsDto, String companyCode, String idOperation);

	ResponseModel findIncomeAccountingRecordsByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String companyCode, String idOperation);

	ResponseModel findClosingAccountingRecordsByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String companyCode, String idOperation);

	ResponseModel findClosingRecordsByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String companyCode, String idOperation);
	
	ResponseModel findAccountingTicketReferenceByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String companyCode, String idOperation);
}
