package mx.com.endtoend.infrastructure.closings.demo.business;

import mx.com.endtoend.infrastructure.cash.closingInstruments.demo.repositories.ClosePaymentInstrumentDemoRepository;
import mx.com.endtoend.infrastructure.closings.demo.repositories.ClosingOperationDemoRepository;
import mx.com.endtoend.infrastructure.closings.common.business.BaseClosingOperationRepositoryBusiness;
import mx.com.endtoend.infrastructure.userConfiguration.demo.business.UserConfigurationDemoBusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;

@Service
public class ClosingOperationDemoBusinessRepository extends BaseClosingOperationRepositoryBusiness {

    @Autowired
    public ClosingOperationDemoBusinessRepository(
            ClosePaymentInstrumentDemoRepository closePaymentInstrumentRepository,
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
            ClosingOperationConverter closingOperationConverter,
            ClosingOperationDetailConverter closingOperationDetailConverter,
            ClosingOperationDemoRepository closingOperationRepository,
            UserConfigurationDemoBusinessRepository userConfigurationDemoBusinessRepository
    ) {
        super(ClosingOperationDemoBusinessRepository.class,
                closePaymentInstrumentRepository,
                closePaymentInstrumentConverter,
                closingOperationConverter,
                closingOperationDetailConverter,
                closingOperationRepository,
                userConfigurationDemoBusinessRepository
        );
    }
}
