package mx.com.endtoend.domain.closings.ports;

import mx.com.endtoend.domain.accountingRecord.ports.AccountingRecordPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ClosingOperationServicePort {

	ResponseModel createClosingOperationByCompanyCode(AccountingRecordPersistencePort accountingRecordPersistencePort,
			OpeningOperationPersistencePort openingOperationPersistencePort,
			EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort,
			ClosingOperationDto closingOperationDto, String method, String companyCode, String idOperation);

}
