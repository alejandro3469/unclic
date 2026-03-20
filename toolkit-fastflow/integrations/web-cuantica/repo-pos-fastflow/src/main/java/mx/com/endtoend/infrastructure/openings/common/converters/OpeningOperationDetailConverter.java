package mx.com.endtoend.infrastructure.openings.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters.OpenPaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationDetailEntity;

@Component
public class OpeningOperationDetailConverter {

	@Autowired
	private OpenPaymentInstrumentConverter openPaymentInstrumentConverter;

	public OpeningOperationDetailEntity openingOperationDetailDtoToOpeningOperationDetailEntity(
			OpeningOperationDetailDto openingOperationDetailDto) {

		OpeningOperationDetailEntity openingOperationDetailEntity = new OpeningOperationDetailEntity();

		openingOperationDetailEntity.setId(openingOperationDetailDto.getId());
		openingOperationDetailEntity.setOpenPaymentInstrument(
				openPaymentInstrumentConverter.openPaymentInstrumentDtoToOpenPaymentInstrumentEntity(
						openingOperationDetailDto.getOpenPaymentInstrument()));
		openingOperationDetailEntity.setAmount(openingOperationDetailDto.getAmount());

		return openingOperationDetailEntity;
	}

	public OpeningOperationDetailDto openingOperationDetailEntityToOpeningOperationDetailDto(
			OpeningOperationDetailEntity openingOperationDetailEntity) {

		OpeningOperationDetailDto openingOperationDetailDto = new OpeningOperationDetailDto();

		openingOperationDetailDto.setId(openingOperationDetailEntity.getId());
		openingOperationDetailDto.setOpeningId(openingOperationDetailEntity.getOpeningOperation().getOpeningId());
		openingOperationDetailDto.setOpenPaymentInstrument(
				openPaymentInstrumentConverter.openPaymentInstrumentEntityToOpenPaymentInstrumentDto(
						openingOperationDetailEntity.getOpenPaymentInstrument()));
		openingOperationDetailDto.setAmount(openingOperationDetailEntity.getAmount());

		return openingOperationDetailDto;
	}

	public List<OpeningOperationDetailEntity> openingOperationDetailDtoListToOpeningOperationDetailEntityList(
			List<OpeningOperationDetailDto> openingOperationDetailDtoList) {
		List<OpeningOperationDetailEntity> detailEntities = new ArrayList<>();
		for (OpeningOperationDetailDto openingOperationDetailDto : openingOperationDetailDtoList) {
			detailEntities.add(openingOperationDetailDtoToOpeningOperationDetailEntity(openingOperationDetailDto));
		}
		return detailEntities;
	}

	public List<OpeningOperationDetailDto> openingOperationDetailEntityListToOpeningOperationDetailDtoList(
			List<OpeningOperationDetailEntity> operationDetailEntityList) {
		List<OpeningOperationDetailDto> detailDtos = new ArrayList<>();
		for (OpeningOperationDetailEntity openingOperationDetailEntity : operationDetailEntityList) {
			detailDtos.add(openingOperationDetailEntityToOpeningOperationDetailDto(openingOperationDetailEntity));
		}
		return detailDtos;
	}
}
