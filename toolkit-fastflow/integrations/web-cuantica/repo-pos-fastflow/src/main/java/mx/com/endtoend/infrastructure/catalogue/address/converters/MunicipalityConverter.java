package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.MunicipalityEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MunicipalityConverter {

    public MunicipalityDto municipalityEntityToMunicipalityDto(MunicipalityEntity municipalityEntity) {
        MunicipalityDto municipalityDto = new MunicipalityDto();
        municipalityDto.setId(municipalityEntity.getId());
        municipalityDto.setIsEnable(municipalityEntity.isEnable());
        municipalityDto.setName(municipalityEntity.getName());
        municipalityDto.setCode(municipalityEntity.getCode());
        municipalityDto.setStateCode(municipalityEntity.getStateCode());
        return municipalityDto;
    }

    public MunicipalityEntity municipalityDtoToMunicipalityEntity(MunicipalityDto municipalityDto) {
        MunicipalityEntity municipalityEntity = new MunicipalityEntity();
        municipalityEntity.setId(municipalityDto.getId());
        municipalityEntity.setEnable(municipalityDto.getIsEnable());
        municipalityEntity.setName(municipalityDto.getName());
        municipalityEntity.setCode(municipalityDto.getCode());
        municipalityEntity.setStateCode(municipalityDto.getStateCode());
        return municipalityEntity;
    }

    public List<MunicipalityDto> municipalityEntityListToMunicipalityDtoList(List<MunicipalityEntity> municipalityEntityList) {
        List<MunicipalityDto> municipalityDtoList = new ArrayList<>();
        for (MunicipalityEntity municipalityEntity : municipalityEntityList) {
            municipalityDtoList.add(municipalityEntityToMunicipalityDto(municipalityEntity));
        }
        return municipalityDtoList;
    }

    public List<MunicipalityEntity> municipalityDtoListToMunicipalityEntityList(List<MunicipalityDto> municipalityDtoList) {
        List<MunicipalityEntity> municipalityEntityList = new ArrayList<>();
        for (MunicipalityDto municipalityDto: municipalityDtoList) {
            municipalityEntityList.add(municipalityDtoToMunicipalityEntity(municipalityDto));
        }
        return municipalityEntityList;
    }
}
