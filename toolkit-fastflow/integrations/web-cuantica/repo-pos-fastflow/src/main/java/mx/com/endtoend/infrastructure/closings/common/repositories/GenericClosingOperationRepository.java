package mx.com.endtoend.infrastructure.closings.common.repositories;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;

public interface GenericClosingOperationRepository {

	ClosingOperationDto createClosingOperation(ClosingOperationDto closingOperationDto, String idOperation);

	ClosePaymentInstrumentDto findClosePaymentInstrumentByIdAndCompanyCode(Long id, String idOperation);

	EmployeeDto findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail, String branchCode,
			String idOperation);

}
