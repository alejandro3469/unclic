package mx.com.endtoend.domain.reports.sales.branchEmployee.ports;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.*;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

import java.util.List;

public interface ReportSaleBranchEmployeePersistencePort {

	ResponseModel generateSaleBranchEmployeeReportByCompanyCode(ReportBranchEmployeeDto reportBranchEmployee,
			String format, String companyCode, String idOperation);

    ResponseModel generateSaleBranchEmployeeReportByCompanyCodeV2(List<ReportSalesDto> reportBranchEmployee,
                                                                  String format, String companyCode, String idOperation);

	ResponseModel generateSCreditNotesReportByParams(List<ReportNotesDto> reportBranchEmployee,
													 String format, String companyCode, String idOperation);

	ResponseModel searchSaleCashByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

	ResponseModel searchSaleCreditCardByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

	ResponseModel searchSaleCredittNoteByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

	ResponseModel searchSaleTransferByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

	ResponseModel searchSaleCheckByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);
	
	ResponseModel searchSaleCreditByEmployeeAndParamsAndCompanyCode(
			SaleReportBranchEmployeeParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

    ResponseModel searchSaleCreditByEmployeeAndParamsAndCompanyCodeV2(
            SaleAndNotesReportParamsDto saleBranchEmployeeParams, String companyCode, String idOperation);

}
