package mx.com.endtoend.infrastructure.closings.carredana.zapta.business;

import mx.com.endtoend.infrastructure.closings.common.business.BaseClosingOperationRepositoryBusiness;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.zapata.repositories.ClosePaymenttInstrumentCZapataRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.closings.carredana.zapta.repositories.ClosingOperationCZapataRepository;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.business.UserConfigurationZapataRepository;

@Service
public class ClosingOperationFCZapataRepository extends BaseClosingOperationRepositoryBusiness {

	public ClosingOperationFCZapataRepository(ClosePaymenttInstrumentCZapataRepository closePaymenttInstrumentRepository,
											  ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
											  ClosingOperationConverter closingOperationConverter,
											  ClosingOperationDetailConverter closingOperationDetailConverter,
											  ClosingOperationCZapataRepository closingOperationCalRepository,
											  UserConfigurationZapataRepository userConfigurationCalzadaRepository
	) {
		super(ClosingOperationFCZapataRepository.class,
				closePaymenttInstrumentRepository,
				closePaymentInstrumentConverter,
				closingOperationConverter,
				closingOperationDetailConverter,
				closingOperationCalRepository,
				userConfigurationCalzadaRepository
		);
	}
}

