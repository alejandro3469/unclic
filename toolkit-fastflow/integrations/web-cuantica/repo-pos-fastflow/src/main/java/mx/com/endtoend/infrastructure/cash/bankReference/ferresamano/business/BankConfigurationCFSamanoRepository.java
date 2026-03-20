package mx.com.endtoend.infrastructure.cash.bankReference.ferresamano.business;

import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.bankReference.ferresamano.repositories.BankFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;

@Service
public class BankConfigurationCFSamanoRepository extends BaseBankConfigurationBusinessRepository {
	public BankConfigurationCFSamanoRepository(
			BankConverter _bankConverter,
			BankFSamanoRepository _bankRepository) {
		super(BankConfigurationCFSamanoRepository.class,
				_bankConverter,
				_bankRepository);

	}
}

