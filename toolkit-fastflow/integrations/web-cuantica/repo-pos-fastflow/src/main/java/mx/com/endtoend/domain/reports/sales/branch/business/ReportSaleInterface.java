package mx.com.endtoend.domain.reports.sales.branch.business;

import mx.com.endtoend.domain.reports.sales.branch.dto.GenericSearchSaleReportParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportSaleInterface {

	ResponseModel generateReportBySaleBranchAndCompanyCode(GenericSearchSaleReportParamsDto saleReportParams,
			String companyCode, String idOperation);

}
