package mx.com.endtoend.infrastructure.accountingRecord.common.adapter;

import mx.com.endtoend.infrastructure.accountingRecord.common.factory.AccountingRecordRepositoryFactory;
import org.springframework.stereotype.Service;


@Service
public class AccountingRecordJpaAdapter extends BaseAccountingRecordJpaAdapter {

	 public AccountingRecordJpaAdapter(AccountingRecordRepositoryFactory accountingRecordRepositoryFactory){
		 super(AccountingRecordJpaAdapter.class,accountingRecordRepositoryFactory);
	 }

}
