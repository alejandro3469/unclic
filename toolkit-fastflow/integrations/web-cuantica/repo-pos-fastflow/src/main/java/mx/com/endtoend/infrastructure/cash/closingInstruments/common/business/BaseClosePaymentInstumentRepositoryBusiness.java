package mx.com.endtoend.infrastructure.cash.closingInstruments.common.business;

import java.util.List;
import java.util.Optional;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.BaseClosePaymentInstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.GenericClosingInstrumentRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;

import javax.persistence.MappedSuperclass;

@NoRepositoryBean
@MappedSuperclass
public class BaseClosePaymentInstumentRepositoryBusiness implements GenericClosingInstrumentRepository {

    private final ClosePaymentInstrumentConverter closePaymentInstrumentConverter;
    private final BaseClosePaymentInstrumentRepository closePaymenttInstrumentRepository;
    private final Logger LOG;

	@Autowired
    public BaseClosePaymentInstumentRepositoryBusiness(
			Class<?> loggerClass,
            ClosePaymentInstrumentConverter closePaymentInstrumentConverter,
			BaseClosePaymentInstrumentRepository closePaymenttInstrumentRepository) {
		this.LOG = LoggerFactory.getLogger(loggerClass);
        this.closePaymentInstrumentConverter = closePaymentInstrumentConverter;
        this.closePaymenttInstrumentRepository = closePaymenttInstrumentRepository;
    }

	@Override
	public ClosePaymentInstrumentDto createClosePaymentInstrument(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation) {

		try {
			LOG.info(String.format("%s INIT createClosePaymentInstrument() ", idOperation));
			ClosePaymentInstrumentEntity closePaymentInstrumentEntity = closePaymentInstrumentConverter
					.closePaymentInstrumentDtoToClosePaymentInstrumentEntity(closePaymentInstrumentDto);
			closePaymentInstrumentEntity = closePaymenttInstrumentRepository.save(closePaymentInstrumentEntity);
			LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
			return closePaymentInstrumentConverter
					.closePaymentInstrumentEntityToClosePayymentInstrumentDto(closePaymentInstrumentEntity);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN createClosePaymentInstrument(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ClosePaymentInstrumentDto updateClosePaymentInstrument(ClosePaymentInstrumentDto closePaymentInstrumentDto,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT updateClosePaymentInstrument() ", idOperation));
			ClosePaymentInstrumentEntity closePaymentInstrumentEntity = closePaymentInstrumentConverter
					.closePaymentInstrumentDtoToClosePaymentInstrumentEntity(closePaymentInstrumentDto);
			closePaymentInstrumentEntity = closePaymenttInstrumentRepository.save(closePaymentInstrumentEntity);
			LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
			return closePaymentInstrumentConverter
					.closePaymentInstrumentEntityToClosePayymentInstrumentDto(closePaymentInstrumentEntity);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN updateClosePaymentInstrument(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ClosePaymentInstrumentDto validExisteByCodeOrNameToCreate(
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT validExisteByCodeOrNameToCreate() ", idOperation));
			List<ClosePaymentInstrumentEntity> closePaymentInstrumentEntities = closePaymenttInstrumentRepository
					.findByCodeOrName(closePaymentInstrumentDto.getCode(), closePaymentInstrumentDto.getName());

			ClosePaymentInstrumentDto closePaymentInstrument = null;
			if (!closePaymentInstrumentEntities.isEmpty()) {
				closePaymentInstrument = closePaymentInstrumentConverter
						.closePaymentInstrumentEntityToClosePayymentInstrumentDto(
								closePaymentInstrumentEntities.get(0));
			}
			LOG.info(String.format("%s RETURN DATA", idOperation));
			return closePaymentInstrument;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN validExisteByCodeOrNameToCreate(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ClosePaymentInstrumentDto validExisteByCodeOrNameToUpdate(
			ClosePaymentInstrumentDto closePaymentInstrumentDto, String idOperation) {
		try {
			LOG.info(String.format("%s INIT validExisteByCodeOrNameToUpdate() ", idOperation));
			List<ClosePaymentInstrumentEntity> closePaymentInstrumentEntities = closePaymenttInstrumentRepository
					.findByCodeOrNameAndIdNot(closePaymentInstrumentDto.getCode(), closePaymentInstrumentDto.getName(),
							closePaymentInstrumentDto.getId());
			ClosePaymentInstrumentDto closePaymentInstrument = null;
			if (!closePaymentInstrumentEntities.isEmpty()) {
				closePaymentInstrument = closePaymentInstrumentConverter
						.closePaymentInstrumentEntityToClosePayymentInstrumentDto(
								closePaymentInstrumentEntities.get(0));
			}
			LOG.info(String.format("%s RETURN DATA", idOperation));
			return closePaymentInstrument;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN validExisteByCodeOrNameToUpdate(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public List<ClosePaymentInstrumentDto> getClosePaymentInstrumentListByEnable(boolean enable, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getClosePaymentInstrumentListByEnable() ", idOperation));
			List<ClosePaymentInstrumentEntity> closePaymentInstrumentEntities = closePaymenttInstrumentRepository
					.findAllByEnable(enable);
			if (!closePaymentInstrumentEntities.isEmpty()) {
				List<ClosePaymentInstrumentDto> closePaymentInstrumentDtoList = closePaymentInstrumentConverter
						.closePaymentInstrumentEntitytListToClosePaymentInstrumentDtoList(
								closePaymentInstrumentEntities);
				LOG.info(String.format("%s RETURN DATA", idOperation));
				return closePaymentInstrumentDtoList;
			} else {
				LOG.info(String.format("%s EMPTY DATA", idOperation));
				return null;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getClosePaymentInstrumentListByEnable(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ClosePaymentInstrumentDto getClosePaymentInstrumentById(Long id, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getClosePaymentInstrumentById() ", idOperation));
			Optional<ClosePaymentInstrumentEntity> closePaymentInstrumentOptional = closePaymenttInstrumentRepository
					.findById(id);
			if (closePaymentInstrumentOptional.isPresent()) {
				LOG.info(String.format("%s RETURN DATA", idOperation));
				return closePaymentInstrumentConverter
						.closePaymentInstrumentEntityToClosePayymentInstrumentDto(closePaymentInstrumentOptional.get());
			} else {
				LOG.info(String.format("%s EMPTY DATA", idOperation));
				return null;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getClosePaymentInstrumentById(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

}
