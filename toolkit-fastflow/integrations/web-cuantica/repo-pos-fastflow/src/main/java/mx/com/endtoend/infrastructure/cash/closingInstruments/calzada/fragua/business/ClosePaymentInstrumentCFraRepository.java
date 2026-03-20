package mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.fragua.business;


import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.fragua.repositories.ClosePaymenttInstrumentFraRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentCFraRepository extends BaseClosePaymentInstumentRepositoryBusiness {

	@Autowired
	public ClosePaymentInstrumentCFraRepository(
			ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			ClosePaymenttInstrumentFraRepository closePaymenttInstrumentRepository
	) {
		super(ClosePaymentInstrumentCFraRepository.class,
				closePaymentInstrumentConverter,
				closePaymenttInstrumentRepository
		);
	}
}
