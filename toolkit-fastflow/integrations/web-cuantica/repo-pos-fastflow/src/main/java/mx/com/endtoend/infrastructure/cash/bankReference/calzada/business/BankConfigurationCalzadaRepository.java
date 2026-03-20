package mx.com.endtoend.infrastructure.cash.bankReference.calzada.business;

import mx.com.endtoend.infrastructure.cash.bankReference.calzada.repositories.BankRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;
import org.springframework.stereotype.Service;

@Service
public class BankConfigurationCalzadaRepository extends BaseBankConfigurationBusinessRepository  {
	public BankConfigurationCalzadaRepository(
											  BankConverter _bankConverter,
											  BankRepository _bankRepository) {
		super(BankConfigurationCalzadaRepository.class,
				_bankConverter,
				_bankRepository);

	}
}
