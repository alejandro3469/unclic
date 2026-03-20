package mx.com.endtoend.infrastructure.cash.closingInstruments.demo.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.demo.repositories.ClosePaymentInstrumentDemoRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentDemoBusinessRepository extends BaseClosePaymentInstumentRepositoryBusiness {

	@Autowired
	public ClosePaymentInstrumentDemoBusinessRepository(
			ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			ClosePaymentInstrumentDemoRepository closePaymentInstrumentRepository
	) {
		super(ClosePaymentInstrumentDemoBusinessRepository.class,
				closePaymentInstrumentConverter,
				closePaymentInstrumentRepository
		);
	}
}