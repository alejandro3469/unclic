package mx.com.endtoend.infrastructure.openings.calzada.business;

import mx.com.endtoend.infrastructure.openings.common.business.BaseOpeningOperationRepositoryBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.repositories.OpenPaymentInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.calzada.repositories.OpeningOperationCalRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.business.UserConfigurationCalzadaRepository;

@Service
public class OpeningOperationCalzadaRepository extends BaseOpeningOperationRepositoryBusiness {

    @Autowired
    public OpeningOperationCalzadaRepository(
            OpenPaymentInstrumentRepository openPaymentInstrumentRepository,
            OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
            OpeningOperationCalRepository openingOperationCalRepository,
            OpeningOperationConverter openingOperationConverter,
            OpeningOperationDetailConverter openingOperationDetailConverter,
            UserConfigurationCalzadaRepository userConfigurationCalzadaRepository
    ) {
        super(OpeningOperationCalzadaRepository.class,
                openPaymentInstrumentRepository,
                openPaymentInstrumentConverter,
                openingOperationCalRepository,
                openingOperationConverter,
                openingOperationDetailConverter,
                userConfigurationCalzadaRepository
        );
    }
}