package mx.com.endtoend.infrastructure.accountingRecord.carredana.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.accountingRecord.carredana.repositories.AccountingRecordFCarRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordFCarredanaRepository extends BaseActionsAccountingRecord {


	 public AccountingRecordFCarredanaRepository(AccountingRecordFCarRepository _accountingRecordFCarredanaRepository,
												 AccountingRecordConverter _accountingRecordConverter) {
		 super(AccountingRecordFCarredanaRepository.class,
				 _accountingRecordFCarredanaRepository,
				 _accountingRecordConverter);
	 }
}
