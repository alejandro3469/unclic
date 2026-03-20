package mx.com.endtoend.infrastructure.closings.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.converters.ClosePaymentInstrumentConverter;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationDetailEntity;

@Component
public class ClosingOperationDetailConverter {

	@Autowired
	private ClosePaymentInstrumentConverter closePaymentInstrumentConverter;

	public ClosingOperationDetailEntity closingOperationDetailDtoToClosingOperationDetailEntity(
			ClosingOperationDetailDto closingOperationDetailDto) {

		ClosingOperationDetailEntity closingOperationDetailEntity = new ClosingOperationDetailEntity();

		closingOperationDetailEntity.setId(closingOperationDetailDto.getId());
		closingOperationDetailEntity.setClosePaymentInstrument(
				closePaymentInstrumentConverter.closePaymentInstrumentDtoToClosePaymentInstrumentEntity(
						closingOperationDetailDto.getClosePaymentInstrument()));
		closingOperationDetailEntity.setAmount(closingOperationDetailDto.getAmount());

		return closingOperationDetailEntity;
	}

	public ClosingOperationDetailDto closingOperationDetailEntityToClosingOperationDetailDto(
			ClosingOperationDetailEntity closingOperationDetailEntity) {

		ClosingOperationDetailDto closingOperationDetailDto = new ClosingOperationDetailDto();

		closingOperationDetailDto.setId(closingOperationDetailEntity.getId());
		closingOperationDetailDto.setClosingId(closingOperationDetailEntity.getClosingOperation().getClosingId());
		closingOperationDetailDto.setClosePaymentInstrument(
				closePaymentInstrumentConverter.closePaymentInstrumentEntityToClosePayymentInstrumentDto(
						closingOperationDetailEntity.getClosePaymentInstrument()));
		closingOperationDetailDto.setAmount(closingOperationDetailEntity.getAmount());

		return closingOperationDetailDto;
	}

	public List<ClosingOperationDetailEntity> closingOperationDetailDtoListToClosingOperationDetailEntityList(
			List<ClosingOperationDetailDto> closingOperationDetailDtoList) {
		List<ClosingOperationDetailEntity> closingOperationDetailEntityList = new ArrayList<>();
		for (ClosingOperationDetailDto closingOperationDetailDto : closingOperationDetailDtoList) {
			closingOperationDetailEntityList
					.add(closingOperationDetailDtoToClosingOperationDetailEntity(closingOperationDetailDto));
		}
		return closingOperationDetailEntityList;
	}

	public List<ClosingOperationDetailDto> closingOperationDetailEntityListToClosingOperationDetailDtoList(
			List<ClosingOperationDetailEntity> closingOperationDetailEntityList) {
		List<ClosingOperationDetailDto> closingOperationDetailDtoList = new ArrayList<>();
		for (ClosingOperationDetailEntity closingOperationDetailEntity : closingOperationDetailEntityList) {
			closingOperationDetailDtoList
					.add(closingOperationDetailEntityToClosingOperationDetailDto(closingOperationDetailEntity));
		}
		return closingOperationDetailDtoList;
	}

}
