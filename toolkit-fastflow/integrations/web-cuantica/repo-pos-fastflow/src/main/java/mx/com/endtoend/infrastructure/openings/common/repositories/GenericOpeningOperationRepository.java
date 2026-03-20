package mx.com.endtoend.infrastructure.openings.common.repositories;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;

public interface GenericOpeningOperationRepository {

	OpenPaymentInstrumentDto findOpenPaymentInstrumentById(Long id, String idOperation);

	OpeningOperationDto findOpeningOperationActiveByEmployeeEmail(String employeeEmail, String idOperation);

	EmployeeDto findEmployeeConfigurationByEmailAndBranchCode(String employeeEmail, String branchCode,
			String idOperation);

	OpeningOperationDto createOpeningOperation(OpeningOperationDto openingOperationDto, String idPeration);

	boolean updateOperativeDataById(OpeningOperationDto openingOperationDto, String idOperation);

}
