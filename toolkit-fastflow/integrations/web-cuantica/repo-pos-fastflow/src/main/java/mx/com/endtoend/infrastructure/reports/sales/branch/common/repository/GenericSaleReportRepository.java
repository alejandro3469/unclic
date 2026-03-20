package mx.com.endtoend.infrastructure.reports.sales.branch.common.repository;

import java.util.List;

import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleReportDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCashPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCheckPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditCardPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditNotePaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleCreditPaymentDto;
import mx.com.endtoend.domain.reports.sales.models.SummarySaleTransferPaymentDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericSaleReportRepository {

	ResponseModel generateReportBySaleBranchAndCompanyCode(BranchSaleReportDto branchSaleReport, String format,
			String idOperation);

	List<SummarySaleCashPaymentDto> searchPaymentCashSummaryByParams(GenericSearchSaleReportParamsDto saleReportParams,
			String idOperation);

	List<SummarySaleTransferPaymentDto> searchPaymentTransferSummaryByParams(
			GenericSearchSaleReportParamsDto saleReportParams, String idOperation);

	List<SummarySaleCheckPaymentDto> searchPaymentCheckSummaryByParams(
			GenericSearchSaleReportParamsDto saleReportParams, String idOperation);

	List<SummarySaleCreditCardPaymentDto> searchPaymentCreditCardSummaryByParams(
			GenericSearchSaleReportParamsDto saleReportParams, String idOperation);

	List<SummarySaleCreditNotePaymentDto> searchPaymentCreditNoteSummaryByParams(
			GenericSearchSaleReportParamsDto saleReportParams, String idOperation);

	List<SummarySaleCreditPaymentDto> searchPaymentCreditSummaryByParams(
			GenericSearchSaleReportParamsDto saleReportParams, String idOperation);

}
