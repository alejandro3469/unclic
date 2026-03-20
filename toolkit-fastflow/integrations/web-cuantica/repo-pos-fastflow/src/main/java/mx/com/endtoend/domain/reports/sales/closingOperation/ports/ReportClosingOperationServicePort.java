package mx.com.endtoend.domain.reports.sales.closingOperation.ports;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportClosingOperationServicePort {

	ResponseModel generateReportClosingOperationByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingOperationReportParamsDto,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation);
 
}
