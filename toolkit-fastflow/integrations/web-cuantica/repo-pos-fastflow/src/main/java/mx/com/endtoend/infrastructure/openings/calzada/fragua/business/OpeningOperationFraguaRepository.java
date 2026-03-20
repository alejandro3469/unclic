package mx.com.endtoend.infrastructure.openings.calzada.fragua.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.fragua.repositories.OpenPaymentInstrumentFraRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.calzada.fragua.repositories.OpeningOperationFraRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.business.UserConfigFraguaRepository;

@Service
public class OpeningOperationFraguaRepository extends BaseOpeningOperationRepositoryBusiness {

	public OpeningOperationFraguaRepository(OpenPaymentInstrumentFraRepository openPaymentInstrumentRepository,
											OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											OpeningOperationFraRepository openingOperationCalRepository,
											OpeningOperationConverter openingOperationConverter,
											OpeningOperationDetailConverter openingOperationDetailConverter,
											UserConfigFraguaRepository userConfigurationCalzadaRepository){
		super(OpeningOperationFraguaRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter,
				openingOperationCalRepository,
				openingOperationConverter,
				openingOperationDetailConverter,
				userConfigurationCalzadaRepository
				);
	}
}
