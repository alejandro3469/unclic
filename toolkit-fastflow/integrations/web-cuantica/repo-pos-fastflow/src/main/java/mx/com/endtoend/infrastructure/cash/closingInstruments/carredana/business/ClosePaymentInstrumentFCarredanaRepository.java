package mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.repositories.ClosePaymenttInstrumentFCarRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentFCarredanaRepository extends BaseClosePaymentInstumentRepositoryBusiness {

	@Autowired
	public ClosePaymentInstrumentFCarredanaRepository(
			ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			ClosePaymenttInstrumentFCarRepository closePaymenttInstrumentRepository
	) {
		super(ClosePaymentInstrumentFCarredanaRepository.class,
				closePaymentInstrumentConverter,
				closePaymenttInstrumentRepository
		);
	}
}
