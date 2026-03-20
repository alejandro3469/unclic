package mx.com.endtoend.domain.openings.ports;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpeningOperationPersistencePort {

	ResponseModel findOpenPaymentInstrumentByIdAndCompanyCode(Long id, String companyCode, String idOperation);

	ResponseModel findOpeningOperationActiveByEmployeeEmailAndCompanyCode(String employeeEmail, String companyCode,
			String idOperation);

	ResponseModel findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail, String branchCode,
			String companyCode, String idOperation);

	ResponseModel createOpeningOperationByCompanyCode(OpeningOperationDto openingOperationDto, String companyCode,
			String idOperation);

	ResponseModel updateOperativeDataByIdAndCompanyCode(OpeningOperationDto openingOperationDto, String companyCode,
			String idOperation);

}
