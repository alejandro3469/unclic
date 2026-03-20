package mx.com.endtoend.infrastructure.cash.openingInstruments.ferresamano.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.openingInstruments.ferresamano.repositories.OpenPaymentInstrumentFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentCFSamanoRepository extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentCFSamanoRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											   OpenPaymentInstrumentFSamanoRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentCFSamanoRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
