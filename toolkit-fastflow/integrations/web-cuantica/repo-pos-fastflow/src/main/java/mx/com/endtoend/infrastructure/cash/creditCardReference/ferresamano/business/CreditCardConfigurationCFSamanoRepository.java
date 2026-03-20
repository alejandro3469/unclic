package mx.com.endtoend.infrastructure.cash.creditCardReference.ferresamano.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.creditCardReference.ferresamano.repositories.CreditCardFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.ferresamano.repositories.CreditCardHistoryChangeFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.ferresamano.repositories.PaymentOptionFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationCFSamanoRepository extends BaseCreditCardConfigurationRepository {

	public CreditCardConfigurationCFSamanoRepository(CreditCardReferenceConverter creditCardReferenceConverter,
													   PaymentOptionReferenceConverter paymentOptionReferenceConverter,
													   CreditCardHistoryChangeConverter cardHistoryChangeConverter,
													   CreditCardFSamanoRepository creditCardRepository,
													   PaymentOptionFSamanoRepository paymentOptionRepository,
													   CreditCardHistoryChangeFSamanoRepository cardHistoryChangeRepository) {
		super(CreditCardConfigurationCFSamanoRepository.class,
				creditCardReferenceConverter,
				paymentOptionReferenceConverter,
				cardHistoryChangeConverter,
				creditCardRepository,
				paymentOptionRepository,
				cardHistoryChangeRepository);
	}
}
