package mx.com.endtoend.infrastructure.closings.carredana.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.repositories.ClosePaymenttInstrumentFCarRepository;
import mx.com.endtoend.infrastructure.closings.carredana.repositories.ClosingOperationFCarRepository;
import mx.com.endtoend.infrastructure.closings.common.business.BaseClosingOperationRepositoryBusiness;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.business.UserConfigurationCarredanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;

@Service
public class ClosingOperationFCarredanaRepository extends BaseClosingOperationRepositoryBusiness {

    @Autowired
    public ClosingOperationFCarredanaRepository(
            ClosePaymenttInstrumentFCarRepository closePaymenttInstrumentRepository,
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
            ClosingOperationConverter closingOperationConverter,
            ClosingOperationDetailConverter closingOperationDetailConverter,
            ClosingOperationFCarRepository  closingOperationCalRepository,
            UserConfigurationCarredanaRepository userConfigurationCalzadaRepository
    ) {
        super(ClosingOperationFCarredanaRepository.class,
                closePaymenttInstrumentRepository,
                closePaymentInstrumentConverter,
                closingOperationConverter,
                closingOperationDetailConverter,
                closingOperationCalRepository,
                userConfigurationCalzadaRepository
        );
    }
}