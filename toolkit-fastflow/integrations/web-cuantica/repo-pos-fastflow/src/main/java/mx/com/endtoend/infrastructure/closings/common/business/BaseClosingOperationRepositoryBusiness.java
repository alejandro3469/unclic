package mx.com.endtoend.infrastructure.closings.common.business;

import java.util.List;
import java.util.Optional;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.BaseClosePaymentInstrumentRepository;
import mx.com.endtoend.infrastructure.closings.calzada.business.ClosingOperationCalzadaRepositoryBusiness;
import mx.com.endtoend.infrastructure.closings.common.repositories.BaseClosingOperationRepository;
import mx.com.endtoend.infrastructure.userConfiguration.common.business.BaseUserConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.repositories.ClosePaymenttInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.closings.common.repositories.GenericClosingOperationRepository;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationDetailEntity;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;

public class BaseClosingOperationRepositoryBusiness implements GenericClosingOperationRepository {

    private final BaseClosePaymentInstrumentRepository closePaymenttInstrumentRepository;
    private final ClosePaymentInstrumentConverter closePaymentInstrumentConverter;
    private final ClosingOperationConverter closingOperationConverter;
    private final ClosingOperationDetailConverter closingOperationDetailConverter;
    private final BaseClosingOperationRepository closingOperationCalRepository;
    private final BaseUserConfigurationRepository userConfigurationCalzadaRepository;
    private final Logger LOG;

    @Autowired
    public BaseClosingOperationRepositoryBusiness(
            Class<?> loggerClass,
            BaseClosePaymentInstrumentRepository closePaymenttInstrumentRepository,
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
            ClosingOperationConverter closingOperationConverter,
            ClosingOperationDetailConverter closingOperationDetailConverter,
            BaseClosingOperationRepository closingOperationCalRepository,
            BaseUserConfigurationRepository userConfigurationCalzadaRepository
    ) {
        this.closePaymenttInstrumentRepository = closePaymenttInstrumentRepository;
        this.closePaymentInstrumentConverter = closePaymentInstrumentConverter;
        this.closingOperationConverter = closingOperationConverter;
        this.closingOperationDetailConverter = closingOperationDetailConverter;
        this.closingOperationCalRepository = closingOperationCalRepository;
        this.userConfigurationCalzadaRepository = userConfigurationCalzadaRepository;
        this.LOG = LoggerFactory.getLogger(loggerClass);
    }

    @Override
    public ClosingOperationDto createClosingOperation(ClosingOperationDto closingOperationDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createClosingOperation()", idOperation));

            // Validación: El detalle de cierre de operación no puede estar vacío
            if (closingOperationDto.getClosingOperationDetail() == null || 
                closingOperationDto.getClosingOperationDetail().isEmpty()) {
                LOG.error(String.format("%s ERROR: closingOperationDetail está vacío en repository", idOperation));
                throw new ValidationError("El detalle de cierre de operación no puede estar vacío.");
            }

            LOG.info(String.format("%s CONVERT CLOSING TO ENTITY", idOperation));
            ClosingOperationEntity closingOperationEntity = closingOperationConverter
                    .closingOperationDtoToClosingOperationEntity(closingOperationDto);

            LOG.info(String.format("%s CONVERT CLOSING DETAIL TO ENTITY", idOperation));
            List<ClosingOperationDetailEntity> closingOperationDetailEntityList = closingOperationDetailConverter
                    .closingOperationDetailDtoListToClosingOperationDetailEntityList(
                            closingOperationDto.getClosingOperationDetail());

            LOG.info(String.format("%s ALLOCATION OF CLOSING IN CLOSING DETAIL", idOperation));
            for (ClosingOperationDetailEntity closingOperationDetailEntity : closingOperationDetailEntityList) {
                closingOperationDetailEntity.setClosingOperation(closingOperationEntity);
            }

            closingOperationEntity.setClosingOperationDetail(closingOperationDetailEntityList);

            LOG.info(String.format("%s SAVE DATA", idOperation));
            closingOperationEntity = closingOperationCalRepository.save(closingOperationEntity);

            ClosingOperationDto closingOperationCreated = convertSavedDataToDto(idOperation, closingOperationEntity);

            return closingOperationCreated;
        } catch (Exception e) {
            LOG.error(
                    String.format("%s ERROR IN createClosingOperation(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    private ClosingOperationDto convertSavedDataToDto(String idOperation,
                                                      ClosingOperationEntity closingOperationEntity) {
        LOG.info(String.format("%s CONVERT SAVED DATA TO DTO", idOperation));

        ClosingOperationDto closingOperationCreated = closingOperationConverter
                .closingOperationEntityToClosingOperationDto(closingOperationEntity);
        List<ClosingOperationDetailDto> closingOperationDetailDtoList = closingOperationDetailConverter
                .closingOperationDetailEntityListToClosingOperationDetailDtoList(
                        closingOperationEntity.getClosingOperationDetail());
        for (ClosingOperationDetailDto closingOperationDetailDto : closingOperationDetailDtoList) {
            ClosePaymentInstrumentDto closePaymentInstrumentDto = this.findClosePaymentInstrumentByIdAndCompanyCode(
                    closingOperationDetailDto.getClosePaymentInstrument().getId(), idOperation);
            closingOperationDetailDto.setClosePaymentInstrument(closePaymentInstrumentDto);
        }
        closingOperationCreated.setClosingOperationDetail(closingOperationDetailDtoList);
        return closingOperationCreated;
    }

    @Override
    public ClosePaymentInstrumentDto findClosePaymentInstrumentByIdAndCompanyCode(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findClosePaymentInstrumentByIdAndCompanyCode()", idOperation));

            Optional<ClosePaymentInstrumentEntity> closePaymentInstrumentOptional = closePaymenttInstrumentRepository
                    .findById(id);
            ClosePaymentInstrumentDto closePaymentInstrumentDto = null;
            if (closePaymentInstrumentOptional.isPresent()) {
                closePaymentInstrumentDto = closePaymentInstrumentConverter
                        .closePaymentInstrumentEntityToClosePayymentInstrumentDto(closePaymentInstrumentOptional.get());
            }
            return closePaymentInstrumentDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findClosePaymentInstrumentByIdAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public EmployeeDto findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(String employeeEmail,
                                                                                   String branchCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode()",
                    idOperation));
            EmployeeDto employeeDto = null;
            employeeDto = userConfigurationCalzadaRepository.findUserConfigurationByEmailAndBranchCode(employeeEmail,
                    branchCode, idOperation);
            return employeeDto;

        } catch (Exception e) {
            LOG.error(String.format(
                    "%s ERROR IN findEmployeeConfigurationByEmailAndBranchCodeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }
}