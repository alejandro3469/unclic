package mx.com.endtoend.infrastructure.accountingRecord.ferresamano.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.ferresamano.repositories.AccountingRecordFSamanoRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordCFSamanoRepository extends BaseActionsAccountingRecord {

	public AccountingRecordCFSamanoRepository(AccountingRecordFSamanoRepository _accountingRecordRepository,
											  AccountingRecordConverter _accountingRecordConverter ){
		super(AccountingRecordCFSamanoRepository.class,
				_accountingRecordRepository,
				_accountingRecordConverter
				);
	}
}
