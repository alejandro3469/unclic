package mx.com.endtoend.infrastructure.cash.openingInstruments.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.openingInstruments.dto.OpenPaymentInstrumentDto;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.entities.OpenPaymentInstrumentEntity;

@Component
public class OpenPaymentInstrumentConverter {

	public OpenPaymentInstrumentEntity openPaymentInstrumentDtoToOpenPaymentInstrumentEntity(
			OpenPaymentInstrumentDto openPaymentInstrumentDto) {

		OpenPaymentInstrumentEntity openPaymentInstrumentEntity = new OpenPaymentInstrumentEntity();

		openPaymentInstrumentEntity.setId(openPaymentInstrumentDto.getId());
		openPaymentInstrumentEntity.setCode(openPaymentInstrumentDto.getCode());
		openPaymentInstrumentEntity.setName(openPaymentInstrumentDto.getName());
		openPaymentInstrumentEntity.setIncomeType(openPaymentInstrumentDto.getIncomeType());
		openPaymentInstrumentEntity.setEnabled(openPaymentInstrumentDto.getIsEnabled());

		return openPaymentInstrumentEntity;
	}

	public OpenPaymentInstrumentDto openPaymentInstrumentEntityToOpenPaymentInstrumentDto(
			OpenPaymentInstrumentEntity openPaymentInstrumentEntity) {

		OpenPaymentInstrumentDto openPaymentInstrumentDto = new OpenPaymentInstrumentDto();

		openPaymentInstrumentDto.setId(openPaymentInstrumentEntity.getId());
		openPaymentInstrumentDto.setCode(openPaymentInstrumentEntity.getCode());
		openPaymentInstrumentDto.setName(openPaymentInstrumentEntity.getName());
		openPaymentInstrumentDto.setIncomeType(openPaymentInstrumentEntity.getIncomeType());
		openPaymentInstrumentDto.setIsEnabled(openPaymentInstrumentEntity.isEnabled());

		return openPaymentInstrumentDto;
	}

	public List<OpenPaymentInstrumentEntity> openPaymentInstrumentDtoListToOpenPaymentInstrumentEntityList(
			List<OpenPaymentInstrumentDto> openPaymentInstrumentDtoList) {
		List<OpenPaymentInstrumentEntity> openPaymentInstrumentEntityList = new ArrayList<>();
		for (OpenPaymentInstrumentDto openPaymentInstrumentDto : openPaymentInstrumentDtoList) {
			openPaymentInstrumentEntityList
					.add(openPaymentInstrumentDtoToOpenPaymentInstrumentEntity(openPaymentInstrumentDto));
		}
		return openPaymentInstrumentEntityList;
	}

	public List<OpenPaymentInstrumentDto> openPaymentInstrumentEntityListToOpenPaymentInstrumentDtoList(
			List<OpenPaymentInstrumentEntity> openPaymentInstrumentEntityList) {
		List<OpenPaymentInstrumentDto> openPaymentInstrumentDtoList = new ArrayList<>();
		for (OpenPaymentInstrumentEntity openPaymentInstrumentEntity : openPaymentInstrumentEntityList) {
			openPaymentInstrumentDtoList
					.add(openPaymentInstrumentEntityToOpenPaymentInstrumentDto(openPaymentInstrumentEntity));
		}
		return openPaymentInstrumentDtoList;
	}

}
