package mx.com.endtoend.domain.reports.sales.branch.ports;

import mx.com.endtoend.domain.reports.sales.branch.dto.BranchSaleReportDto;
import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface SaleReportPersistencePort {

	ResponseModel generateReportBySaleBranchAndCompanyCode(BranchSaleReportDto branchSaleReport, String format,
			String companyCode, String idOperation);

	ResponseModel searchPaymentCashSummaryByCompanyCodeAndParams(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation);

	ResponseModel searchPaymentTransferSummaryByCompanyCodeAndParams(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation);

	ResponseModel searchPaymentCheckSummaryByCompanyCodeAndParams(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation);

	ResponseModel searchPaymentCreditCardSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation);

	ResponseModel searchPaymentCreditNoteSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation);
	
	ResponseModel searchPaymentCreditSummaryByCompanyCodeAndParams(
			GenericSearchSaleReportParamsDto saleReportParams, String companyCode, String idOperation);

}
