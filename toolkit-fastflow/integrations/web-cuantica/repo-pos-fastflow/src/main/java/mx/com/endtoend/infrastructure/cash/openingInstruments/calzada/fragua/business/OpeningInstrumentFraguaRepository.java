package mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.fragua.business;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.business.OpeningInstrumentCalRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.business.BaseOpeningInstrumentRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.fragua.repositories.OpenPaymentInstrumentFraRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;

@Service
public class OpeningInstrumentFraguaRepository extends BaseOpeningInstrumentRepository {

	public OpeningInstrumentFraguaRepository(OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											 OpenPaymentInstrumentFraRepository openPaymentInstrumentRepository) {
		super(OpeningInstrumentFraguaRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter);
	}

}
