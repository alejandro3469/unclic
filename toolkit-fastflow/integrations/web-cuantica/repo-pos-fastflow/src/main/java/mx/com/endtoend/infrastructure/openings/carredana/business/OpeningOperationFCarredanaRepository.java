package mx.com.endtoend.infrastructure.openings.carredana.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.repositories.OpenPaymentInstrumentFCarRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.carredana.repositories.OpeningOperationFCarRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.business.UserConfigurationCarredanaRepository;

@Service
public class OpeningOperationFCarredanaRepository extends BaseOpeningOperationRepositoryBusiness {

	public OpeningOperationFCarredanaRepository(OpenPaymentInstrumentFCarRepository openPaymentInstrumentRepository,
												OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
												OpeningOperationFCarRepository openingOperationCalRepository,
												OpeningOperationConverter openingOperationConverter,
												OpeningOperationDetailConverter openingOperationDetailConverter,
												UserConfigurationCarredanaRepository userConfigurationCalzadaRepository){
		super(OpeningOperationFCarredanaRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter,
				openingOperationCalRepository,
				openingOperationConverter,
				openingOperationDetailConverter,
				userConfigurationCalzadaRepository
				);
	}
}
