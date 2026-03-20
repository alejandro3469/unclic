package mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.repositories.OpenPaymentInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentCalRepository extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentCalRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
										  OpenPaymentInstrumentRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentCalRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
