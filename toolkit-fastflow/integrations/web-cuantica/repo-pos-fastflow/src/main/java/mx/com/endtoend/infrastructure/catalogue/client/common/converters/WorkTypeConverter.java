package mx.com.endtoend.infrastructure.catalogue.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.WorkTypeEntity;

@Component
public class WorkTypeConverter {

	public WorkTypeDto workTypeEntityToWorkTypeDto(WorkTypeEntity workTypeEntity) {

		WorkTypeDto workTypeDto = new WorkTypeDto();

		workTypeDto.setId(workTypeEntity.getId());
		workTypeDto.setIsEnable(workTypeEntity.isEnable());
		workTypeDto.setCode(workTypeEntity.getCode());
		workTypeDto.setValue(workTypeEntity.getValue());

		return workTypeDto;
	}

	public WorkTypeEntity workTypeDtoToWorkTypeEntity(WorkTypeDto workTypeDto) {

		WorkTypeEntity workTypeEntity = new WorkTypeEntity();

		workTypeEntity.setId(workTypeDto.getId());
		workTypeEntity.setEnable(workTypeDto.getIsEnable());
		workTypeEntity.setCode(workTypeDto.getCode());
		workTypeEntity.setValue(workTypeDto.getValue());

		return workTypeEntity;
	}

	public List<WorkTypeDto> workTypeEntityListToWorkTypeDtoList(List<WorkTypeEntity> workTypeEntityList) {
		List<WorkTypeDto> workTypeDtoList = new ArrayList<WorkTypeDto>();
		for (WorkTypeEntity workTypeEntity : workTypeEntityList) {
			workTypeDtoList.add(workTypeEntityToWorkTypeDto(workTypeEntity));
		}
		return workTypeDtoList;
	}

	public List<WorkTypeEntity> workTypeDtoListToWorkTypeEntityList(List<WorkTypeDto> workTypeDtoList) {
		List<WorkTypeEntity> workTypeEntityList = new ArrayList<WorkTypeEntity>();
		for (WorkTypeDto workTypeDto : workTypeDtoList) {
			workTypeEntityList.add(workTypeDtoToWorkTypeEntity(workTypeDto));
		}
		return workTypeEntityList;
	}
}
