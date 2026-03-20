package mx.com.endtoend.domain.closings.ports;

import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingOperationPersistencePort {

	ResponseModel createClosingOperationByCompanyCode(ClosingOperationDto closingOperationDto, String companyCode,
			String idOperation);

	ResponseModel findClosePaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation);

	ResponseModel findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail, String branchCode,
			String companyCode, String idOperation);

}
