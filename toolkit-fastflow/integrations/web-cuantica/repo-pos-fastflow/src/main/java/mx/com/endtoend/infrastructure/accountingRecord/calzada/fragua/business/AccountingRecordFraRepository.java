package mx.com.endtoend.infrastructure.accountingRecord.calzada.fragua.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.calzada.fragua.repositories.AccountingRecordCFraRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordFraRepository extends BaseActionsAccountingRecord {

	public AccountingRecordFraRepository(AccountingRecordCFraRepository _accountingRecordRepository,
										 AccountingRecordConverter _accountingRecordConverter) {
		super(AccountingRecordFraRepository.class,
				_accountingRecordRepository,
				_accountingRecordConverter);
	}
}
