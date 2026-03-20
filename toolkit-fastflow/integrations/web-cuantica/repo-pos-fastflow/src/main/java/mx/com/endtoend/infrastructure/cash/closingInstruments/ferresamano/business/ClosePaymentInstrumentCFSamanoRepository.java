package mx.com.endtoend.infrastructure.cash.closingInstruments.ferresamano.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.ferresamano.repositories.ClosePaymenttInstrumentFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentCFSamanoRepository extends BaseClosePaymentInstumentRepositoryBusiness {

	@Autowired
	public ClosePaymentInstrumentCFSamanoRepository(
			ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			ClosePaymenttInstrumentFSamanoRepository closePaymenttInstrumentRepository
	) {
		super(ClosePaymentInstrumentCFSamanoRepository.class,
				closePaymentInstrumentConverter,
				closePaymenttInstrumentRepository
		);
	}
}
