package mx.com.endtoend.infrastructure.cash.openingInstruments.demo.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.demo.repositories.OpenPaymentInstrumentDemoRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentDemoRepository  extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentDemoRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
										 OpenPaymentInstrumentDemoRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentDemoRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
