package mx.com.endtoend.infrastructure.closings.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationEntity;

@Component
public class ClosingOperationConverter {

	public ClosingOperationEntity closingOperationDtoToClosingOperationEntity(ClosingOperationDto closingOperationDto) {

		ClosingOperationEntity closingOperationEntity = new ClosingOperationEntity();

		closingOperationEntity.setClosingId(closingOperationDto.getClosingId());
		closingOperationEntity.setBranchCode(closingOperationDto.getBranchCode());
		closingOperationEntity.setEmployeeEmail(closingOperationDto.getEmployeeEmail());
		closingOperationEntity.setTotalAmount(closingOperationDto.getTotalAmount());
		closingOperationEntity.setCreationDate(closingOperationDto.getCreationDate());

		return closingOperationEntity;
	}

	public ClosingOperationDto closingOperationEntityToClosingOperationDto(
			ClosingOperationEntity closingOperationEntity) {

		ClosingOperationDto closingOperationDto = new ClosingOperationDto();

		closingOperationDto.setClosingId(closingOperationEntity.getClosingId());
		closingOperationDto.setBranchCode(closingOperationEntity.getBranchCode());
		closingOperationDto.setEmployeeEmail(closingOperationEntity.getEmployeeEmail());
		closingOperationDto.setTotalAmount(closingOperationEntity.getTotalAmount());
		closingOperationDto.setCreationDate(closingOperationEntity.getCreationDate());

		return closingOperationDto;
	}

	public List<ClosingOperationEntity> closingOperationDtoListToClosingOperationEntityList(
			List<ClosingOperationDto> closingOperationDtoList) {
		List<ClosingOperationEntity> closingOperationEntityList = new ArrayList<>();
		for (ClosingOperationDto closingOperationDto : closingOperationDtoList) {
			closingOperationEntityList.add(closingOperationDtoToClosingOperationEntity(closingOperationDto));
		}
		return closingOperationEntityList;
	}

	public List<ClosingOperationDto> closingOperationEntityListToClosingOperationDtoList(
			List<ClosingOperationEntity> closingOperationEntityList) {
		List<ClosingOperationDto> closingOperationDtoList = new ArrayList<>();
		for (ClosingOperationEntity closingOperationEntity : closingOperationEntityList) {
			closingOperationDtoList.add(closingOperationEntityToClosingOperationDto(closingOperationEntity));
		}
		return closingOperationDtoList;
	}
}
