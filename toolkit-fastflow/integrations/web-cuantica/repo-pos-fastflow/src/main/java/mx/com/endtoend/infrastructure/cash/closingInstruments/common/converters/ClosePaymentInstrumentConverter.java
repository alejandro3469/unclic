package mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.closingInstruments.dto.ClosePaymentInstrumentDto;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.entities.ClosePaymentInstrumentEntity;

@Component
public class ClosePaymentInstrumentConverter {

	public ClosePaymentInstrumentDto closePaymentInstrumentEntityToClosePayymentInstrumentDto(
			ClosePaymentInstrumentEntity closePaymentInstrumentEntity) {

		ClosePaymentInstrumentDto closePaymentInstrumentDto = new ClosePaymentInstrumentDto();

		closePaymentInstrumentDto.setId(closePaymentInstrumentEntity.getId());
		closePaymentInstrumentDto.setCode(closePaymentInstrumentEntity.getCode());
		closePaymentInstrumentDto.setName(closePaymentInstrumentEntity.getName());
		closePaymentInstrumentDto.setIncomeType(closePaymentInstrumentEntity.getIncomeType());
		closePaymentInstrumentDto.setIsEnabled(closePaymentInstrumentEntity.isEnabled());

		return closePaymentInstrumentDto;
	}

	public ClosePaymentInstrumentEntity closePaymentInstrumentDtoToClosePaymentInstrumentEntity(
			ClosePaymentInstrumentDto closePaymentInstrumentDto) {

		ClosePaymentInstrumentEntity closePaymentInstrumentEntity = new ClosePaymentInstrumentEntity();

		closePaymentInstrumentEntity.setId(closePaymentInstrumentDto.getId());
		closePaymentInstrumentEntity.setCode(closePaymentInstrumentDto.getCode());
		closePaymentInstrumentEntity.setName(closePaymentInstrumentDto.getName());
		closePaymentInstrumentEntity.setIncomeType(closePaymentInstrumentDto.getIncomeType());
		closePaymentInstrumentEntity.setEnabled(closePaymentInstrumentDto.getIsEnabled());

		return closePaymentInstrumentEntity;
	}

	public List<ClosePaymentInstrumentEntity> closePaymentInstrumentDtoListToClosePaymentInstrumentEntityList(
			List<ClosePaymentInstrumentDto> closePaymentInstrumentDtoList) {
		List<ClosePaymentInstrumentEntity> closePaymentInstrumentEntityList = new ArrayList<>();
		for (ClosePaymentInstrumentDto closePaymentInstrumentDto : closePaymentInstrumentDtoList) {
			closePaymentInstrumentEntityList
					.add(closePaymentInstrumentDtoToClosePaymentInstrumentEntity(closePaymentInstrumentDto));
		}
		return closePaymentInstrumentEntityList;
	}

	public List<ClosePaymentInstrumentDto> closePaymentInstrumentEntitytListToClosePaymentInstrumentDtoList(
			List<ClosePaymentInstrumentEntity> closePaymentInstrumentEntityList) {
		List<ClosePaymentInstrumentDto> closePaymentInstrumentDtoList = new ArrayList<>();
		for (ClosePaymentInstrumentEntity closePaymentInstrumentEntity : closePaymentInstrumentEntityList) {
			closePaymentInstrumentDtoList
					.add(closePaymentInstrumentEntityToClosePayymentInstrumentDto(closePaymentInstrumentEntity));
		}
		return closePaymentInstrumentDtoList;
	}
}