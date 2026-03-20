package mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.business;


import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.repositories.CreditCardFraRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.repositories.CreditCardHistoryChangeFraRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.repositories.PaymentOptionFraRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationFraguaRepository extends BaseCreditCardConfigurationRepository {


	public CreditCardConfigurationFraguaRepository(CreditCardReferenceConverter creditCardReferenceConverter,
 												 PaymentOptionReferenceConverter paymentOptionReferenceConverter,
												  CreditCardHistoryChangeConverter cardHistoryChangeConverter,
													CreditCardFraRepository creditCardRepository,
													PaymentOptionFraRepository paymentOptionRepository,
													CreditCardHistoryChangeFraRepository cardHistoryChangeRepository){
		super(CreditCardConfigurationFraguaRepository.class,
				creditCardReferenceConverter,
				paymentOptionReferenceConverter,
				cardHistoryChangeConverter,
				creditCardRepository,
				paymentOptionRepository,
				cardHistoryChangeRepository);

	}
}
