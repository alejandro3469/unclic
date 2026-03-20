package mx.com.endtoend.domain.openings.ports;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface OpeningOperationServicePort {

	ResponseModel createOpeningOperationByCompanyCode(AccountingRecordPersistencePort accountingRecordPersistencePort,
			OpeningOperationDto openingOperationDto, String method, String companyCode, String idOperation);

	ResponseModel validOpeningOperationStatusByEmployeeEmailAndCompanyCode(
			AccountingRecordPersistencePort accountingRecordPersistencePort, String method, String employeeEmail,
			String companyCode, String idOperation);
}
