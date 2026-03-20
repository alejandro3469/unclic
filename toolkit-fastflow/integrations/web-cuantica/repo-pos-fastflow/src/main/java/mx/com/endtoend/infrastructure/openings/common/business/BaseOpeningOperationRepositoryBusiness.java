package mx.com.endtoend.infrastructure.openings.common.business;

import java.util.List;
import java.util.Optional;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository.BaseOpenPaymentInstrumentRepository;
import mx.com.endtoend.infrastructure.openings.common.repositories.BaseOpeningOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities.OpenPaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.infrastructure.openings.common.repositories.GenericOpeningOperationRepository;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationDetailEntity;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationEntity;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public class BaseOpeningOperationRepositoryBusiness implements GenericOpeningOperationRepository {

    private final BaseOpenPaymentInstrumentRepository openPaymentInstrumentRepository;
    private final OpenPaymentInstrumentConverter openPaymentInstrumentConverter;
    private final BaseOpeningOperationRepository openingOperationCalRepository;
    private final OpeningOperationConverter openingOperationConverter;
    private final OpeningOperationDetailConverter openingOperationDetailConverter;
    private final BaseUserConfigurationRepository userConfigurationCalzadaRepository;
    private final Logger LOG;

    @Autowired
    public BaseOpeningOperationRepositoryBusiness(
            Class<?> loggerClass,
            BaseOpenPaymentInstrumentRepository openPaymentInstrumentRepository,
            OpenPaymentInstrumentConverter openPaymentInstrumentConverter,
            BaseOpeningOperationRepository openingOperationCalRepository,
            OpeningOperationConverter openingOperationConverter,
            OpeningOperationDetailConverter openingOperationDetailConverter,
            BaseUserConfigurationRepository userConfigurationCalzadaRepository
    ) {
        this.openPaymentInstrumentRepository = openPaymentInstrumentRepository;
        this.openPaymentInstrumentConverter = openPaymentInstrumentConverter;
        this.openingOperationCalRepository = openingOperationCalRepository;
        this.openingOperationConverter = openingOperationConverter;
        this.openingOperationDetailConverter = openingOperationDetailConverter;
        this.userConfigurationCalzadaRepository = userConfigurationCalzadaRepository;
        this.LOG = LoggerFactory.getLogger(loggerClass);
    }

    @Override
    public OpenPaymentInstrumentDto findOpenPaymentInstrumentById(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findOpenPaymentInstrumentById()", idOperation));

            Optional<OpenPaymentInstrumentEntity> openPaymentInstrumentOptional = openPaymentInstrumentRepository
                    .findById(id);
            OpenPaymentInstrumentDto openPaymentInstrumentDto = null;
            if (openPaymentInstrumentOptional.isPresent()) {
                openPaymentInstrumentDto = openPaymentInstrumentConverter
                        .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentOptional.get());
            }
            return openPaymentInstrumentDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findOpenPaymentInstrumentById(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpeningOperationDto findOpeningOperationActiveByEmployeeEmail(String employeeEmail, String idOperation) {
        try {

            LOG.info(String.format("%s INIT findOpeningOperationActiveByEmployeeEmail()", idOperation));
            OpeningOperationDto openingOperationDto = null;
            Optional<OpeningOperationEntity> openingOperationOptional = openingOperationCalRepository
                    .findByEmployeeEmailAndIsActive(employeeEmail, true);
            if (openingOperationOptional.isPresent()) {
                openingOperationDto = openingOperationConverter
                        .openingOperationEntityToOpeningOperationDto(openingOperationOptional.get());
            }
            return openingOperationDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findOpeningOperationActiveByEmployeeEmail(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public EmployeeDto findEmployeeConfigurationByEmailAndBranchCode(String employeeEmail, String branchCode,
                                                                     String idOperation) {
        try {
            LOG.info(String.format("%s INIT findEmployeeConfigurationByEmailAndBranchCode()", idOperation));

            EmployeeDto employeeDto = null;
            employeeDto = userConfigurationCalzadaRepository.findUserConfigurationByEmailAndBranchCode(employeeEmail,
                    branchCode, idOperation);
            return employeeDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findEmployeeConfigurationByEmailAndBranchCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpeningOperationDto createOpeningOperation(OpeningOperationDto openingOperationDto, String idOperation) {
        try {

            LOG.info(String.format("%s INIT createOpeningOperation()", idOperation));

            // Validación: El detalle de apertura de operación no puede estar vacío
            if (openingOperationDto.getOpeningOperationDetail() == null || 
                openingOperationDto.getOpeningOperationDetail().isEmpty()) {
                LOG.error(String.format("%s ERROR: openingOperationDetail está vacío en repository", idOperation));
                throw new ValidationError("El detalle de apertura de operación no puede estar vacío.");
            }

            LOG.info(String.format("%s CONVERT OPENING TO ENTITY", idOperation));
            OpeningOperationEntity openingOperationEntity = openingOperationConverter
                    .openingOperationDtoToOpeningOperationEntity(openingOperationDto);

            LOG.info(String.format("%s CONVERT OPENING DETAIL TO ENTITY", idOperation));
            List<OpeningOperationDetailEntity> openingOperationDetailEntityList = openingOperationDetailConverter
                    .openingOperationDetailDtoListToOpeningOperationDetailEntityList(
                            openingOperationDto.getOpeningOperationDetail());

            LOG.info(String.format("%s ALLOCATION OF OPENING IN OPENING DETAIL", idOperation));
            for (OpeningOperationDetailEntity openingOperationDetailEntity : openingOperationDetailEntityList) {
                openingOperationDetailEntity.setOpeningOperation(openingOperationEntity);
            }

            openingOperationEntity.setOpeningOperationDetail(openingOperationDetailEntityList);

            LOG.info(String.format("%s SAVE DATA", idOperation));
            openingOperationEntity = openingOperationCalRepository.save(openingOperationEntity);

            OpeningOperationDto openingOperationCreated = convertSavedDataToDto(idOperation, openingOperationEntity);

            return openingOperationCreated;

        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN createOpeningOperation(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

	@Override
	public boolean updateOperativeDataById(OpeningOperationDto openingOperationDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateOperativeDataById()", idOperation));
			Optional<OpeningOperationEntity> openingOperationOptional = openingOperationCalRepository
					.findById(openingOperationDto.getOpeningId());
			if (openingOperationOptional.isPresent()) {
				OpeningOperationEntity openingOperationEntity = openingOperationOptional.get();
				openingOperationEntity.setActive(openingOperationDto.getIsActive());
				openingOperationEntity.setCloseAttempts(openingOperationDto.getCloseAttempts());
				openingOperationEntity.setClosingId(openingOperationDto.getClosingId());
				openingOperationCalRepository.save(openingOperationEntity);
				LOG.info(String.format("%s OPENING UPDATED", idOperation));
				return true;
			} else {
				LOG.info(String.format("%s OPENING NOT FOUND", idOperation));
				return false;
			}
		} catch (Exception e) {
			LOG.error(
					String.format("%s ERROR IN updateOperativeDataById(). EXCEPTION: %s", idOperation, e.getMessage()));
			return false;
		}
	}

	private OpeningOperationDto convertSavedDataToDto(String idOperation,
			OpeningOperationEntity openingOperationEntity) {
		LOG.info(String.format("%s CONVERT SAVED DATA TO DTO", idOperation));
		OpeningOperationDto openingOperationCreated = openingOperationConverter
				.openingOperationEntityToOpeningOperationDto(openingOperationEntity);
		List<OpeningOperationDetailDto> openingOperationDetailDtoList = openingOperationDetailConverter
				.openingOperationDetailEntityListToOpeningOperationDetailDtoList(
						openingOperationEntity.getOpeningOperationDetail());

		for (OpeningOperationDetailDto openingOperationDetailDto : openingOperationDetailDtoList) {
			OpenPaymentInstrumentDto openPaymentInstrumentDto = this.findOpenPaymentInstrumentById(
					openingOperationDetailDto.getOpenPaymentInstrument().getId(), idOperation);
			openingOperationDetailDto.setOpenPaymentInstrument(openPaymentInstrumentDto);
		}
		openingOperationCreated.setOpeningOperationDetail(openingOperationDetailDtoList);
		LOG.info(String.format("%s DATA TO DTO : %s", idOperation, openingOperationCreated.toString()));
		return openingOperationCreated;
	}

}
