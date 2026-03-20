package mx.com.endtoend.domain.openings.business;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpeningOperationInterface {

	ResponseModel createOpeningOperation(OpeningOperationPersistencePort openingOperationPersistencePort,
			OpeningOperationDto openingOperationDto, String companyCode, String idOperation);

	ResponseModel getOpeningOperationStatusActiveByEmployeeEmail(
			OpeningOperationPersistencePort openingOperationPersistencePort, String employeeEmail, String companyCode,
			String idOperation);
}
