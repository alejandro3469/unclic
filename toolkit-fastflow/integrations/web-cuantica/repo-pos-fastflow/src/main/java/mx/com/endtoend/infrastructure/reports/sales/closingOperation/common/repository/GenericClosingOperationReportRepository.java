package mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository;

import java.util.List;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingRecordReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.AccountingTicketRecord;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReported;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationSummarySeach;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.BranchClosingOperationReport;
import mx.com.endtoend.domain.reports.sales.closingOperation.dto.report.GeneralClosingOperationReport;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericClosingOperationReportRepository {

	ResponseModel generateReportByBranchClosing(BranchClosingOperationReport branchClosingOperationReport,
			String format, String idOperation);

	ResponseModel generateReportByGeneralClosing(GeneralClosingOperationReport generalClosingOperationReport,
			String format, String idOperation);

	List<ClosingOperationSummarySeach> findClosingRecordsByParams(
			ClosingOperationReportParamsDto closingOperationReportParamsDto, String idOperation);

	List<AccountingRecordReported> findIncomeAccountingRecordsByParams(
			ClosingOperationSummarySeach closingOSummarySeach, String idOperation);

	List<ClosingOperationReported> findClosingAccountingRecordsByParams(
			ClosingOperationSummarySeach closingOSummarySeach, String idOperation);

	List<ClosingOperationReported> findClosingRecordsByParams(ClosingOperationSummarySeach closingOSummarySeach,
			String idOperation);

	List<AccountingTicketRecord> findAccountingTicketReferenceByParamsAndCompanyCode(ClosingOperationSummarySeach closingOSummarySeach,
			String idOperation);

}
