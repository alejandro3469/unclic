package mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.business.BaseClosePaymentInstumentRepositoryBusiness;
import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.repositories.ClosePaymenttInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;

@Service
public class ClosePaymentInstrumentCalRepository extends BaseClosePaymentInstumentRepositoryBusiness {

    @Autowired
    public ClosePaymentInstrumentCalRepository(
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
            ClosePaymenttInstrumentRepository closePaymenttInstrumentRepository
    ) {
        super(ClosePaymentInstrumentCalRepository.class,
                closePaymentInstrumentConverter,
                closePaymenttInstrumentRepository
        );
    }
}