package mx.com.endtoend.infrastructure.cash.bankReference.carredana.zapata.business;

import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.bankReference.carredana.zapata.repositories.BankCZaparaRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;

@Service
public class BankConfigurationCZapataRepository extends BaseBankConfigurationBusinessRepository {
	public BankConfigurationCZapataRepository(
			BankConverter _bankConverter,
			BankCZaparaRepository _bankRepository) {
		super(BankConfigurationCZapataRepository.class,
				_bankConverter,
				_bankRepository);

	}
}

