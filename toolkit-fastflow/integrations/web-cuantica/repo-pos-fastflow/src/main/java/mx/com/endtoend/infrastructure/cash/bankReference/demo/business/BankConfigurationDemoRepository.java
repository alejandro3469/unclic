package mx.com.endtoend.infrastructure.cash.bankReference.demo.business;

import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.bankReference.demo.repositories.BankDemoRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;

@Service
public class BankConfigurationDemoRepository extends BaseBankConfigurationBusinessRepository {
	public BankConfigurationDemoRepository(
			BankConverter _bankConverter,
			BankDemoRepository _bankRepository) {
		super(BankConfigurationDemoRepository.class,
				_bankConverter,
				_bankRepository);

	}
}
