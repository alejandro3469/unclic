package mx.com.endtoend.infrastructure.catalogue.address.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityCPEntity;

@Component
public class MunicipalityCPConverter {

	public MunicipalityCPDto municipalityCpEntityToMunicipalityCpDto(MunicipalityCPEntity municipalityCPEntity) {

		MunicipalityCPDto municipalityCPDto = new MunicipalityCPDto();

		municipalityCPDto.setId(municipalityCPEntity.getId());
		municipalityCPDto.setName(municipalityCPEntity.getName());
		municipalityCPDto.setCp(municipalityCPEntity.getCp());
		municipalityCPDto.setStateCode(municipalityCPEntity.getStateCode());

		return municipalityCPDto;
	}

	public MunicipalityCPEntity municipalityCpDtoToMunicipalityCpEntity(MunicipalityCPDto municipalityCPDto) {

		MunicipalityCPEntity municipalityCPEntity = new MunicipalityCPEntity();

		municipalityCPEntity.setId(municipalityCPDto.getId());
		municipalityCPEntity.setName(municipalityCPDto.getName());
		municipalityCPEntity.setCp(municipalityCPDto.getCp());
		municipalityCPEntity.setStateCode(municipalityCPDto.getStateCode());

		return municipalityCPEntity;
	}

	public List<MunicipalityCPDto> municipalityCpEntityListToMunicipalityCpDtoLits(
			List<MunicipalityCPEntity> municipalityCPEntityList) {
		List<MunicipalityCPDto> municipalityCPDtoList = new ArrayList<>();
		for (MunicipalityCPEntity municipalityCPEntity : municipalityCPEntityList) {
			municipalityCPDtoList.add(municipalityCpEntityToMunicipalityCpDto(municipalityCPEntity));
		}
		return municipalityCPDtoList;
	}

	public List<MunicipalityCPEntity> municipalityCpDtoListToMunicipalityCpEntityLits(
			List<MunicipalityCPDto> municipalityCPDtoList) {
		List<MunicipalityCPEntity> municipalityCPEntityList = new ArrayList<>();
		for (MunicipalityCPDto municipalityCPDto : municipalityCPDtoList) {
			municipalityCPEntityList.add(municipalityCpDtoToMunicipalityCpEntity(municipalityCPDto));
		}
		return municipalityCPEntityList;
	}
}
