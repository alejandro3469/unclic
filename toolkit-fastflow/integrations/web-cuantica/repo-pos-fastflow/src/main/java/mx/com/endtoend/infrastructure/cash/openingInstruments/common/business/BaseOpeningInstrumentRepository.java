package mx.com.endtoend.infrastructure.cash.openingInstruments.common.business;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities.OpenPaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository.BaseOpenPaymentInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository.GenericOpeningInstrumentRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@MappedSuperclass
public class BaseOpeningInstrumentRepository implements GenericOpeningInstrumentRepository {


    private final OpenPaymentInstrumentConverter openPaymentInstrumentConverter;
    private final BaseOpenPaymentInstrumentRepository openPaymentInstrumentRepository;

    private final Logger LOG;

    public BaseOpeningInstrumentRepository(Class<?> loggerClass, BaseOpenPaymentInstrumentRepository _openPaymentInstrumentRepository,
                                           OpenPaymentInstrumentConverter _openPaymentInstrumentConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        openPaymentInstrumentConverter = _openPaymentInstrumentConverter;
        openPaymentInstrumentRepository = _openPaymentInstrumentRepository;
    }



    @Override
    public OpenPaymentInstrumentDto createOpenPaymentInstrumentByCompanyCode(
            OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createOpenPaymentInstrumentByCompanyCode() ", idOperation));
            OpenPaymentInstrumentEntity openPaymentInstrumentEntity = openPaymentInstrumentConverter
                    .openPaymentInstrumentDtoToOpenPaymentInstrumentEntity(openPaymentInstrumentDto);
            openPaymentInstrumentEntity = openPaymentInstrumentRepository.save(openPaymentInstrumentEntity);
            LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
            return openPaymentInstrumentConverter
                    .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createOpenPaymentInstrumentByCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpenPaymentInstrumentDto updateOpenPaymentInstrumentByCompanyCode(
            OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateOpenPaymentInstrumentByCompanyCode() ", idOperation));
            OpenPaymentInstrumentEntity openPaymentInstrumentEntity = openPaymentInstrumentConverter
                    .openPaymentInstrumentDtoToOpenPaymentInstrumentEntity(openPaymentInstrumentDto);
            openPaymentInstrumentEntity = openPaymentInstrumentRepository.save(openPaymentInstrumentEntity);
            LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
            return openPaymentInstrumentConverter
                    .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateOpenPaymentInstrumentByCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpenPaymentInstrumentDto validExisteByCodeOrNameByCompanyCodeToCreate(
            OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToCreate() ", idOperation));
            List<OpenPaymentInstrumentEntity> openPaymentInstrumentEntities = openPaymentInstrumentRepository
                    .findByCodeOrName(openPaymentInstrumentDto.getCode(), openPaymentInstrumentDto.getName());
            OpenPaymentInstrumentDto openPaymentInstrument = null;
            if (!openPaymentInstrumentEntities.isEmpty()) {
                openPaymentInstrument = openPaymentInstrumentConverter
                        .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentEntities.get(0));
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return openPaymentInstrument;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN validExisteByCodeOrNameByCompanyCodeToCreate(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpenPaymentInstrumentDto validExisteByCodeOrNameByCompanyCodeToUpdate(
            OpenPaymentInstrumentDto openPaymentInstrumentDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT validExisteByCodeOrNameByCompanyCodeToUpdate() ", idOperation));
            List<OpenPaymentInstrumentEntity> openPaymentInstrumentEntities = openPaymentInstrumentRepository
                    .findByCodeOrNameAndIdNot(openPaymentInstrumentDto.getCode(), openPaymentInstrumentDto.getName(),
                            openPaymentInstrumentDto.getId());
            OpenPaymentInstrumentDto openPaymentInstrument = null;
            if (!openPaymentInstrumentEntities.isEmpty()) {
                openPaymentInstrument = openPaymentInstrumentConverter
                        .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentEntities.get(0));
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return openPaymentInstrument;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN validExisteByCodeOrNameByCompanyCodeToUpdate(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<OpenPaymentInstrumentDto> getOpenPaymentInstrumentListByEnableAndCompanyCode(boolean enable,
                                                                                             String idOperation) {
        try {
            LOG.info(String.format("%s INIT getOpenPaymentInstrumentListByEnableAndCompanyCode() ", idOperation));
            List<OpenPaymentInstrumentEntity> openPaymentInstrumentEntities = openPaymentInstrumentRepository
                    .findAllByEnable(enable);
            if (!openPaymentInstrumentEntities.isEmpty()) {
                List<OpenPaymentInstrumentDto> openPaymentInstrumentDtoList = openPaymentInstrumentConverter
                        .openPaymentInstrumentEntityListToOpenPaymentInstrumentDtoList(openPaymentInstrumentEntities);
                LOG.info(String.format("%s RETURN DATA", idOperation));
                return openPaymentInstrumentDtoList;
            } else {
                LOG.info(String.format("%s EMPTY DATA", idOperation));
                return null;
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getOpenPaymentInstrumentListByEnableAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public OpenPaymentInstrumentDto getOpenPaymentInstrumentById(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getOpenPaymentInstrumentById() ", idOperation));
            Optional<OpenPaymentInstrumentEntity> openPaymentInstrumentOptional = openPaymentInstrumentRepository
                    .findById(id);
            if (openPaymentInstrumentOptional.isPresent()) {
                LOG.info(String.format("%s RETURN DATA", idOperation));
                return openPaymentInstrumentConverter
                        .openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentOptional.get());
            } else {
                LOG.info(String.format("%s EMPTY DATA", idOperation));
                return null;
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getOpenPaymentInstrumentById(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }
}
