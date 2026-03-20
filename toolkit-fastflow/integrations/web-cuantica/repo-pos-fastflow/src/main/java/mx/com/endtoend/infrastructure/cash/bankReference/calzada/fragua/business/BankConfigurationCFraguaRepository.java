package mx.com.endtoend.infrastructure.cash.bankReference.calzada.fragua.business;

import mx.com.endtoend.infrastructure.cash.bankReference.common.business.BaseBankConfigurationBusinessRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.bankReference.calzada.fragua.repositories.BankFraguaRepository;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;

@Service
public class BankConfigurationCFraguaRepository extends BaseBankConfigurationBusinessRepository {
	public BankConfigurationCFraguaRepository(
			BankConverter _bankConverter,
			BankFraguaRepository _bankRepository) {
		super(BankConfigurationCFraguaRepository.class,
				_bankConverter,
				_bankRepository);

	}
}
