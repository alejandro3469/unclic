package mx.com.endtoend.infrastructure.accountingRecord.calzada.business;

import mx.com.endtoend.infrastructure.accountingRecord.common.business.BaseActionsAccountingRecord;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.accountingRecord.calzada.repositories.AccountingRecordRepository;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;

@Service
public class AccountingRecordCalRepository extends BaseActionsAccountingRecord {


	public AccountingRecordCalRepository(AccountingRecordRepository accountingRecordRepository,
										 AccountingRecordConverter accountingRecordConverter) {
		super(AccountingRecordCalRepository.class,
				accountingRecordRepository,
				accountingRecordConverter);
	}

}
