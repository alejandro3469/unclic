package mx.com.endtoend.domain.reports.sales.branch.ports;

import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface SaleReportServicePort {

	ResponseModel generateReportBySaleBranchAndCompanyCode(GenericSearchSaleReportParamsDto saleReportParams,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation);

}
