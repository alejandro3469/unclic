package mx.com.endtoend.domain.closings.business;

import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingOperationInterface {

	ResponseModel createClosingOperationByCompanyCode(ClosingOperationPersistencePort closingOperationPersistencePort,
			ClosingOperationDto closingOperationDto, String companyCode, String idOperation);
}
