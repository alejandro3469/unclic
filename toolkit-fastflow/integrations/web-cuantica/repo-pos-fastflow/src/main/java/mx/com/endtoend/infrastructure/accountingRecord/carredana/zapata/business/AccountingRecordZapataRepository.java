package mx.com.endtoend.infrastructure.accountingRecord.carredana.zapata.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.carredana.zapata.repositories.AccountingRecordCZapataRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordZapataRepository extends BaseActionsAccountingRecord {


	public AccountingRecordZapataRepository(AccountingRecordCZapataRepository _accountingRecordRepository,
											AccountingRecordConverter _accountingRecordConverter) {
		super(AccountingRecordZapataRepository.class,
				_accountingRecordRepository,
				_accountingRecordConverter);
	}
}
