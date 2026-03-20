package mx.com.endtoend.infrastructure.catalogue.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.CFDIEntity;

@Component
public class CFDIConverter {

	public CFDIDto CfdiEntityToCfdiDto(CFDIEntity cfdiEntity) {

		CFDIDto cfdiDto = new CFDIDto();

		cfdiDto.setId(cfdiEntity.getId());
		cfdiDto.setIsEnable(cfdiEntity.isEnable());
		cfdiDto.setCode(cfdiEntity.getCode());
		cfdiDto.setValue(cfdiEntity.getValue());

		return cfdiDto;
	}

	public CFDIEntity cfdiDtoToCfdiEntity(CFDIDto cfdiDto) {

		CFDIEntity cfdiEntity = new CFDIEntity();

		cfdiEntity.setId(cfdiDto.getId());
		cfdiEntity.setEnable(cfdiDto.getIsEnable());
		cfdiEntity.setCode(cfdiDto.getCode());
		cfdiEntity.setValue(cfdiDto.getValue());
		
		return cfdiEntity;
	}

	public List<CFDIDto> cfdiEntityListToCfdiDtoList(List<CFDIEntity> cfdiEntityList) {
		List<CFDIDto> cfdiDtoList = new ArrayList<>();
		for (CFDIEntity cfdiEntity : cfdiEntityList) {
			cfdiDtoList.add(CfdiEntityToCfdiDto(cfdiEntity));
		}
		return cfdiDtoList;
	}

	public List<CFDIEntity> cfdiDtoListToCfdiEntityList(List<CFDIDto> cfdiDtoList) {
		List<CFDIEntity> cfdiEntityList = new ArrayList<CFDIEntity>();
		for (CFDIDto cfdiDto : cfdiDtoList) {
			cfdiEntityList.add(cfdiDtoToCfdiEntity(cfdiDto));
		}
		return cfdiEntityList;
	}
}
