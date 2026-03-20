package mx.com.endtoend.infrastructure.closings.calzada.fragua.business;

import java.util.List;
import java.util.Optional;

import mx.com.endtoend.infrastructure.closings.common.business.BaseClosingOperationRepositoryBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.fragua.repositories.ClosePaymenttInstrumentFraRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.closings.common.repositories.GenericClosingOperationRepository;
import mx.com.endtoend.infrastructure.closings.calzada.fragua.repositories.ClosingOperationFraRepository;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationDetailEntity;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.business.UserConfigFraguaRepository;

@Service
public class ClosingOperationFraguaRepository extends BaseClosingOperationRepositoryBusiness {

 	public ClosingOperationFraguaRepository(ClosePaymenttInstrumentFraRepository closePaymenttInstrumentRepository,
					ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
					ClosingOperationConverter closingOperationConverter,
					ClosingOperationDetailConverter closingOperationDetailConverter,
					ClosingOperationFraRepository closingOperationCalRepository,
					UserConfigFraguaRepository userConfigurationCalzadaRepository
) {
		 super(ClosingOperationFraRepository.class,
				 closePaymenttInstrumentRepository,
				 closePaymentInstrumentConverter,
				 closingOperationConverter,
				 closingOperationDetailConverter,
				 closingOperationCalRepository,
				 userConfigurationCalzadaRepository
				 );
	}
}
