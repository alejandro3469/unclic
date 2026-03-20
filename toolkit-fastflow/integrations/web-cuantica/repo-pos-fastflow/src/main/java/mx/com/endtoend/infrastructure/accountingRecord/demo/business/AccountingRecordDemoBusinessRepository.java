package mx.com.endtoend.infrastructure.accountingRecord.demo.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.demo.repositories.AccountingRecordDemoRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordDemoBusinessRepository extends BaseActionsAccountingRecord {

	 public AccountingRecordDemoBusinessRepository(AccountingRecordDemoRepository _accountingRecordDemoRepository,
												   AccountingRecordConverter _accountingRecordConverter) {
		 super(AccountingRecordDemoBusinessRepository.class,
				 _accountingRecordDemoRepository,
				 _accountingRecordConverter);
	 }
}
