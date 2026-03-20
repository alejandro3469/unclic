package mx.com.endtoend.infrastructure.catalogue.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.StatusEntity;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;

@Component
public class StatusConverter {

	public StatusEntity statusDtoToStatusEntity(StatusDto statusDto) {

		StatusEntity statusEntity = new StatusEntity();

		statusEntity.setId(statusDto.getId());
		statusEntity.setCode(statusDto.getCode());
		statusEntity.setDescription(statusDto.getDescription());

		return statusEntity;

	}

	public StatusDto statusEntityToStatusDto(StatusEntity statusEntity) {

		StatusDto statusDto = new StatusDto();

		statusDto.setId(statusEntity.getId());
		statusDto.setCode(statusEntity.getCode());
		statusDto.setDescription(statusEntity.getDescription());

		return statusDto;
	}

	public List<StatusDto> statusEntityListToStatusDtoList(List<StatusEntity> statusEntityList) {

		List<StatusDto> statusDtoList = new ArrayList<StatusDto>();

		for (StatusEntity statusEntity : statusEntityList) {
			statusDtoList.add(statusEntityToStatusDto(statusEntity));
		}

		return statusDtoList;

	}

	public List<StatusEntity> statusDtoListToStatusEntityList(List<StatusDto> statusDtoList) {

		List<StatusEntity> statusEntityList = new ArrayList<StatusEntity>();

		for (StatusDto statusDto : statusDtoList) {
			statusEntityList.add(statusDtoToStatusEntity(statusDto));
		}

		return statusEntityList;
	}

}
