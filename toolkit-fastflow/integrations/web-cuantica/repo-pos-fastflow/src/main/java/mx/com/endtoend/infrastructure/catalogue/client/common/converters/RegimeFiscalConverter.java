package mx.com.endtoend.infrastructure.catalogue.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.RegimeFiscalEntity;

@Component
public class RegimeFiscalConverter {

	public RegimeFiscalDto regimeFiscalEntityToRegimeFiscalDto(RegimeFiscalEntity regimeFiscalEntity) {

		RegimeFiscalDto regimeFiscalDto = new RegimeFiscalDto();

		regimeFiscalDto.setId(regimeFiscalEntity.getId());
		regimeFiscalDto.setIsEnable(regimeFiscalEntity.isEnable());
		regimeFiscalDto.setCode(regimeFiscalEntity.getCode());
		regimeFiscalDto.setSatCode(regimeFiscalEntity.getSatCode());
		regimeFiscalDto.setValue(regimeFiscalEntity.getValue());

		return regimeFiscalDto;
	}

	public RegimeFiscalEntity regimeFiscalDtoToRegimeFiscalEntity(RegimeFiscalDto regimeFiscalDto) {

		RegimeFiscalEntity regimeFiscalEntity = new RegimeFiscalEntity();

		regimeFiscalEntity.setId(regimeFiscalDto.getId());
		regimeFiscalEntity.setEnable(regimeFiscalDto.getIsEnable());
		regimeFiscalEntity.setCode(regimeFiscalDto.getCode());
		regimeFiscalEntity.setSatCode(regimeFiscalDto.getSatCode());
		regimeFiscalEntity.setValue(regimeFiscalDto.getValue());

		return regimeFiscalEntity;
	}

	public List<RegimeFiscalDto> regimeFiscalEntityListToRegimeFiscalDtoList(
			List<RegimeFiscalEntity> regimeFiscalEntityList) {
		List<RegimeFiscalDto> regimeFiscalDtoList = new ArrayList<>();
		for (RegimeFiscalEntity regimeFiscalEntity : regimeFiscalEntityList) {
			regimeFiscalDtoList.add(regimeFiscalEntityToRegimeFiscalDto(regimeFiscalEntity));
		}
		return regimeFiscalDtoList;
	}

	public List<RegimeFiscalEntity> regimeFiscalDtoListToRegimeFiscalEntityList(
			List<RegimeFiscalDto> regimeFiscalDtoList) {
		List<RegimeFiscalEntity> regimeFiscalEntityList = new ArrayList<>();
		for (RegimeFiscalDto regimeFiscalDto : regimeFiscalDtoList) {
			regimeFiscalEntityList.add(regimeFiscalDtoToRegimeFiscalEntity(regimeFiscalDto));
		}
		return regimeFiscalEntityList;
	}
}
