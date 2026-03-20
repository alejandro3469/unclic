package mx.com.endtoend.infrastructure.openings.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationEntity;

@Component
public class OpeningOperationConverter {

	public OpeningOperationEntity openingOperationDtoToOpeningOperationEntity(OpeningOperationDto openingOperationDto) {

		OpeningOperationEntity openingOperationEntity = new OpeningOperationEntity();

		openingOperationEntity.setOpeningId(openingOperationDto.getOpeningId());
		openingOperationEntity.setBranchCode(openingOperationDto.getBranchCode());
		openingOperationEntity.setEmployeeEmail(openingOperationDto.getEmployeeEmail());
		openingOperationEntity.setClosingId(openingOperationDto.getClosingId());
		openingOperationEntity.setActive(openingOperationDto.getIsActive());
		openingOperationEntity.setTotalAmount(openingOperationDto.getTotalAmount());
		openingOperationEntity.setCreationDate(openingOperationDto.getCreationDate());
		openingOperationEntity.setCloseAttempts(openingOperationDto.getCloseAttempts());
		
		return openingOperationEntity;
	}

	public OpeningOperationDto openingOperationEntityToOpeningOperationDto(
			OpeningOperationEntity openingOperationEntity) {

		OpeningOperationDto openingOperationDto = new OpeningOperationDto();

		openingOperationDto.setOpeningId(openingOperationEntity.getOpeningId());
		openingOperationDto.setBranchCode(openingOperationEntity.getBranchCode());
		openingOperationDto.setEmployeeEmail(openingOperationEntity.getEmployeeEmail());
		openingOperationDto.setClosingId(openingOperationEntity.getClosingId());
		openingOperationDto.setIsActive(openingOperationEntity.isActive());
		openingOperationDto.setTotalAmount(openingOperationEntity.getTotalAmount());
		openingOperationDto.setCreationDate(openingOperationEntity.getCreationDate());
		openingOperationDto.setCloseAttempts(openingOperationEntity.getCloseAttempts());
		
		return openingOperationDto;
	}

	public List<OpeningOperationEntity> openingOperationDtoListToOpeningOperationEntityList(
			List<OpeningOperationDto> openingOperationDtoList) {
		List<OpeningOperationEntity> entities = new ArrayList<>();
		for (OpeningOperationDto openingOperationDto : openingOperationDtoList) {
			entities.add(openingOperationDtoToOpeningOperationEntity(openingOperationDto));
		}
		return entities;
	}

	public List<OpeningOperationDto> openingOperationEntityListToOpeningOperationDtoList(
			List<OpeningOperationEntity> openingOperationEntityList) {
		List<OpeningOperationDto> dtos = new ArrayList<>();
		for (OpeningOperationEntity openingOperationEntity : openingOperationEntityList) {
			dtos.add(openingOperationEntityToOpeningOperationDto(openingOperationEntity));
		}
		return dtos;
	}
}
