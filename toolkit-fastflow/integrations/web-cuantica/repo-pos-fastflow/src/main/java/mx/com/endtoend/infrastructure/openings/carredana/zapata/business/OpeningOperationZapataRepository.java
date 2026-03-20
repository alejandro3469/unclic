package mx.com.endtoend.infrastructure.openings.carredana.zapata.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.zapata.repositories.OpenPaymentInstrumentCZapataRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.carredana.zapata.repositories.OpeningOperationCZapataRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.business.UserConfigurationZapataRepository;

@Service
public class OpeningOperationZapataRepository extends BaseOpeningOperationRepositoryBusiness {

	public OpeningOperationZapataRepository(OpenPaymentInstrumentCZapataRepository openPaymentInstrumentRepository,
											OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
											OpeningOperationCZapataRepository openingOperationCalRepository,
											OpeningOperationConverter openingOperationConverter,
											OpeningOperationDetailConverter openingOperationDetailConverter,
											UserConfigurationZapataRepository userConfigurationCalzadaRepository){
		super(OpeningOperationZapataRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter,
				openingOperationCalRepository,
				openingOperationConverter,
				openingOperationDetailConverter,
				userConfigurationCalzadaRepository);
	}
}
