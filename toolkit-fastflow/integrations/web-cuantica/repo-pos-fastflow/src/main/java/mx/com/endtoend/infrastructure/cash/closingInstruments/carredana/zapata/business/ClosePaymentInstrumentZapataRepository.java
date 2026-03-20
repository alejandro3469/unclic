package mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.zapata.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.zapata.repositories.ClosePaymenttInstrumentCZapataRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentZapataRepository extends BaseClosePaymentInstumentRepositoryBusiness {

	@Autowired
	public ClosePaymentInstrumentZapataRepository(
			ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			ClosePaymenttInstrumentCZapataRepository closePaymenttInstrumentRepository
	) {
		super(ClosePaymentInstrumentZapataRepository.class,
				closePaymentInstrumentConverter,
				closePaymenttInstrumentRepository
		);
	}
}
