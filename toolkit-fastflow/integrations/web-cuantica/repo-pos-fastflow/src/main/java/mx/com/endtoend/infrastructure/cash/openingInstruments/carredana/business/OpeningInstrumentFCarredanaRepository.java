package mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.repositories.OpenPaymentInstrumentFCarRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentFCarredanaRepository  extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentFCarredanaRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											 OpenPaymentInstrumentFCarRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentFCarredanaRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
