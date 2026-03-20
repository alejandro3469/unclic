package mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.business;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.business.BaseCreditCardConfigurationRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.repositories.CreditCardHistoryChangeRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.repositories.CreditCardRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.repositories.PaymentOptionRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;

@Service
public class CreditCardConfigurationCalzadaRepository extends BaseCreditCardConfigurationRepository {

    public CreditCardConfigurationCalzadaRepository(CreditCardReferenceConverter creditCardReferenceConverter,
                                                    PaymentOptionReferenceConverter paymentOptionReferenceConverter,
                                                    CreditCardHistoryChangeConverter cardHistoryChangeConverter,
                                                    CreditCardRepository creditCardRepository,
                                                    PaymentOptionRepository paymentOptionRepository,
                                                    CreditCardHistoryChangeRepository cardHistoryChangeRepository) {
        super(CreditCardConfigurationCalzadaRepository.class,
                creditCardReferenceConverter,
                paymentOptionReferenceConverter,
                cardHistoryChangeConverter,
                creditCardRepository,
                paymentOptionRepository,
                cardHistoryChangeRepository);
    }
}
