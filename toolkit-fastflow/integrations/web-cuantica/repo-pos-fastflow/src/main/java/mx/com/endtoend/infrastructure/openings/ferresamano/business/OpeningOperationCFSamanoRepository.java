package mx.com.endtoend.infrastructure.openings.ferresamano.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.ferresamano.repositories.OpenPaymentInstrumentFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.ferresamano.repositories.OpeningOperationFSamanoRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.business.UserConfigurationCFSamanoRepository;

@Service
public class OpeningOperationCFSamanoRepository extends BaseOpeningOperationRepositoryBusiness {

	public OpeningOperationCFSamanoRepository(OpenPaymentInstrumentFSamanoRepository openPaymentInstrumentRepository,
											  OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											  OpeningOperationFSamanoRepository openingOperationCalRepository,
											  OpeningOperationConverter openingOperationConverter,
											  OpeningOperationDetailConverter openingOperationDetailConverter,
											  UserConfigurationCFSamanoRepository userConfigurationCalzadaRepository){
		super(OpeningOperationCFSamanoRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter,
				openingOperationCalRepository,
				openingOperationConverter,
				openingOperationDetailConverter,
				userConfigurationCalzadaRepository
				);
	}
}
