package mx.com.endtoend.infrastructure.cash.creditCardReference.demo.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.creditCardReference.demo.repositories.CreditCardDemoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.demo.repositories.CreditCardHistoryChangeDemoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.demo.repositories.PaymentOptionDemoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationDemoRepository extends BaseCreditCardConfigurationRepository {

	public CreditCardConfigurationDemoRepository(CreditCardReferenceConverter creditCardReferenceConverter,
												PaymentOptionReferenceConverter paymentOptionReferenceConverter,
												CreditCardHistoryChangeConverter cardHistoryChangeConverter,
												   CreditCardDemoRepository creditCardRepository,
												   PaymentOptionDemoRepository paymentOptionRepository,
												   CreditCardHistoryChangeDemoRepository cardHistoryChangeRepository) {
		super(CreditCardConfigurationDemoRepository.class,
				creditCardReferenceConverter,
				paymentOptionReferenceConverter,
				cardHistoryChangeConverter,
				creditCardRepository,
				paymentOptionRepository,
				cardHistoryChangeRepository);
	}
}
