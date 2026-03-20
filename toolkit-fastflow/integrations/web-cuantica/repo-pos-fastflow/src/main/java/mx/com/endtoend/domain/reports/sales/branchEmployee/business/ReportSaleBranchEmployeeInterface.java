package mx.com.endtoend.domain.reports.sales.branchEmployee.business;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleAndNotesReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportSaleBranchEmployeeInterface {

	ResponseModel generateReportBranchEmployee(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			String companyCode, String idOperation);

    ResponseModel generateReportBranchEmployeeV2(SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto,
                                                 String companyCode, String idOperation);

}
