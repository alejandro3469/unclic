package mx.com.endtoend.infrastructure.reports.sales.branchEmployee.common.repository;

import java.util.List;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.*;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCashPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCheckPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditCardPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditNotePaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleTransferPaymentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericSaleBranchEmployeeRepository {

	ResponseModel generateSaleBranchEmployeeReport(ReportBranchEmployeeDto reportBranchEmployee, String format,
			String idOperation);

    ResponseModel generateSaleBranchEmployeeReportV2(List<ReportSalesDto> reportBranchEmployee, String format,
                                                   String idOperation);

	ResponseModel generateCreditNotesReport(List<ReportNotesDto> reportBranchEmployee, String format,
											String idOperation);

	List<SummarySaleCashPaymentDto> searchSaleCashByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

	List<SummarySaleCreditCardPaymentDto> searchSaleCreditCardByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

	List<SummarySaleCreditNotePaymentDto> searchSaleCredittNoteByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

	List<SummarySaleTransferPaymentDto> searchSaleTransferByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

	List<SummarySaleCheckPaymentDto> searchSaleCheckByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

	List<SummarySaleCreditPaymentDto> searchSaleCreditByEmployeeAndParams(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String idOperation);

    List<ReportSalesDto> searchSaleCreditByEmployeeAndParamsV2(
            SaleAndNotesReportParamsDto saleBranchEmployeeParams, String idOperation);

	List<ReportNotesDto> findCreditNotesByParams(SaleAndNotesReportParamsDto saleBranchEmployeeParams, String idOperation);
}
