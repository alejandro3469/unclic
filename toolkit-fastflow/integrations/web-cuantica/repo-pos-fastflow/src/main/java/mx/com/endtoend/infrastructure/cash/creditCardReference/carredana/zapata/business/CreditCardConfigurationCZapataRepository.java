package mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.zapata.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.zapata.repositories.CreditCardCZapataRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.zapata.repositories.CreditCardHistoryChangeCZapataRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.zapata.repositories.PaymentOptionCZapataRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationCZapataRepository extends BaseCreditCardConfigurationRepository {

	public CreditCardConfigurationCZapataRepository(CreditCardReferenceConverter creditCardReferenceConverter,
												   PaymentOptionReferenceConverter paymentOptionReferenceConverter,
												   CreditCardHistoryChangeConverter cardHistoryChangeConverter,
													CreditCardCZapataRepository creditCardRepository,
													PaymentOptionCZapataRepository paymentOptionRepository,
													CreditCardHistoryChangeCZapataRepository cardHistoryChangeRepository){
		super(CreditCardConfigurationCZapataRepository.class,
				creditCardReferenceConverter,
				paymentOptionReferenceConverter,
				cardHistoryChangeConverter,
				creditCardRepository,
				paymentOptionRepository,
				cardHistoryChangeRepository);

	}
}
