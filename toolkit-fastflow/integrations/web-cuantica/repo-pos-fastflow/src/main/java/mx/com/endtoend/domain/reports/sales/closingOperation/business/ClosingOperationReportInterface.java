package mx.com.endtoend.domain.reports.sales.closingOperation.business;

import mx.com.endtoend.domain.reports.sales.closingOperation.dto.ClosingOperationReportParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingOperationReportInterface {

	ResponseModel generateReportClosingOperationByParamsAndCompanyCode(
			ClosingOperationReportParamsDto closingReportParamsDto, String companyCode, String idOperation);

}
