package mx.com.endtoend.infrastructure.closings.calzada.business;

import mx.com.endtoend.infrastructure.closings.common.business.BaseClosingOperationRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.repositories.ClosePaymenttInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.closings.calzada.repositories.ClosingOperationCalRepository;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.business.UserConfigurationCalzadaRepository;

@Service
public class ClosingOperationCalzadaRepositoryBusiness extends BaseClosingOperationRepositoryBusiness {

    @Autowired
    public ClosingOperationCalzadaRepositoryBusiness(
            ClosePaymenttInstrumentRepository closePaymenttInstrumentRepository,
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
            ClosingOperationConverter closingOperationConverter,
            ClosingOperationDetailConverter closingOperationDetailConverter,
            ClosingOperationCalRepository closingOperationCalRepository,
            UserConfigurationCalzadaRepository userConfigurationCalzadaRepository
    ) {
        super(ClosingOperationCalzadaRepositoryBusiness.class,
                closePaymenttInstrumentRepository,
                closePaymentInstrumentConverter,
                closingOperationConverter,
                closingOperationDetailConverter,
                closingOperationCalRepository,
                userConfigurationCalzadaRepository
        );
    }
}