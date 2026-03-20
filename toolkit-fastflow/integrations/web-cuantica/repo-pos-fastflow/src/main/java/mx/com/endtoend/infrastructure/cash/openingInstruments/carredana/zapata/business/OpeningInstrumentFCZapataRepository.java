package mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.zapata.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.zapata.repositories.OpenPaymentInstrumentCZapataRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentFCZapataRepository extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentFCZapataRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											   OpenPaymentInstrumentCZapataRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentFCZapataRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
