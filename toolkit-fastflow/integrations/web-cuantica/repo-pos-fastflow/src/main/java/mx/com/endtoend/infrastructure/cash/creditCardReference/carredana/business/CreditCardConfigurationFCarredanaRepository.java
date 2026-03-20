package mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.repositories.CreditCardFCarRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.repositories.CreditCardHistoryChangeFCarRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.repositories.PaymentOptionFCarRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationFCarredanaRepository extends BaseCreditCardConfigurationRepository {

	public CreditCardConfigurationFCarredanaRepository(CreditCardReferenceConverter creditCardReferenceConverter,
													PaymentOptionReferenceConverter paymentOptionReferenceConverter,
													CreditCardHistoryChangeConverter cardHistoryChangeConverter,
													   CreditCardFCarRepository creditCardRepository,
													   PaymentOptionFCarRepository paymentOptionRepository,
													   CreditCardHistoryChangeFCarRepository cardHistoryChangeRepository) {
		super(CreditCardConfigurationFCarredanaRepository.class,
				creditCardReferenceConverter,
				paymentOptionReferenceConverter,
				cardHistoryChangeConverter,
				creditCardRepository,
				paymentOptionRepository,
				cardHistoryChangeRepository);
	}
}

