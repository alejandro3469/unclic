package mx.com.endtoend.infrastructure.openings.demo.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.demo.repositories.OpenPaymentInstrumentDemoRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.demo.repositories.OpeningOperationDemoRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.demo.business.UserConfigurationDemoBusinessRepository;

@Service
public class OpeningOperationDemoBusinessRepository extends BaseOpeningOperationRepositoryBusiness {

	public OpeningOperationDemoBusinessRepository(OpenPaymentInstrumentDemoRepository openPaymentInstrumentRepository,
												OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
												OpeningOperationDemoRepository openingOperationDemoRepository,
												OpeningOperationConverter openingOperationConverter,
												OpeningOperationDetailConverter openingOperationDetailConverter,
												UserConfigurationDemoBusinessRepository userConfigurationDemoBusinessRepository){
		super(OpeningOperationDemoBusinessRepository.class,
				openPaymentInstrumentRepository,
				openPaymentInstrumentConverter,
				openingOperationDemoRepository,
				openingOperationConverter,
				openingOperationDetailConverter,
				userConfigurationDemoBusinessRepository
				);
	}
}
