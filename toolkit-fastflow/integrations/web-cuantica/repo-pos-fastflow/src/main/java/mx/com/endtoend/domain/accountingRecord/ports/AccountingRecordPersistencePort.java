package mx.com.endtoend.domain.accountingRecord.ports;

import java.util.List;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface AccountingRecordPersistencePort {

	ResponseModel createAccountingRecordMovementByCompanyCode(List<AccountingRecordDto> accointingRecordDtoList,
			String companuCode, String idOperation);

	ResponseModel fingAccountingRecordByOpeningIdAndCompanyCode(Long openingId, String companyCode, String idOperation);
}