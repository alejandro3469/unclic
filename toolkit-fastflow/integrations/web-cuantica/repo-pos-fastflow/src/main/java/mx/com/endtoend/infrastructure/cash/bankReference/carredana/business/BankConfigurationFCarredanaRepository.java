package mx.com.endtoend.infrastructure.cash.bankReference.carredana.business;

import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.bankReference.carredana.repositories.BankFCarepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;

@Service
public class BankConfigurationFCarredanaRepository extends BaseBankConfigurationBusinessRepository {
	public BankConfigurationFCarredanaRepository(
			BankConverter _bankConverter,
			BankFCarepository _bankRepository) {
		super(BankConfigurationFCarredanaRepository.class,
				_bankConverter,
				_bankRepository);

	}
}
