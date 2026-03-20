package mx.com.endtoend.infrastructure.accountingRecord.common.repository;

import java.util.List;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;

public interface GenericAccountingRecordRepository {

	boolean createAccountingRecordMovement(List<AccountingRecordDto> accointingRecordDtoList, String idOperation);

	List<AccountingRecordDto> fingAccountingRecordByOpeningIdAndCompanyCode(Long openingId, String idOperation);

}
