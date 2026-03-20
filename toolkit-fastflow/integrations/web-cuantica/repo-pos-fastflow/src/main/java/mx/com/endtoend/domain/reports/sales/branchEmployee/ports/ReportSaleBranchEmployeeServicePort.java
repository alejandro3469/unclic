package mx.com.endtoend.domain.reports.sales.branchEmployee.ports;

import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleAndNotesReportParamsDto;
import mx.com.endtoend.domain.reports.sales.branchEmployee.dto.SaleReportBranchEmployeeParamsDto;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportSaleBranchEmployeeServicePort {

	ResponseModel generateReportByParamsAndCompanyCode(SaleReportBranchEmployeeParamsDto reportBranchEmployeeParamsDto,
			SaleReportInterfaceService reportInterfaceService, String method, String companyCode,
			String idOperation);

    ResponseModel generateReportByParamsAndCompanyCodeV2(SaleAndNotesReportParamsDto reportBranchEmployeeParamsDto,
                                                         SaleReportInterfaceService reportInterfaceService, String method, String companyCode,
                                                         String idOperation);

}
