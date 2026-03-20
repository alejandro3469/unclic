package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ColonyConverter {

    public ColonyDto colonyEntityToColonyDto(ColonyEntity colonyEntity) {
        ColonyDto colonyDto = new ColonyDto();
        colonyDto.setId(colonyEntity.getId());
        colonyDto.setCity(colonyEntity.getCity());
        colonyDto.setName(colonyEntity.getName());
        colonyDto.setIsEnable(colonyEntity.isEnable());
        colonyDto.setCp(colonyEntity.getCp());
        colonyDto.setStateCode(colonyEntity.getStateCode());
        return colonyDto;
    }

    public ColonyEntity colonyDtoToColonyEntity(ColonyDto colonyDto) {
        ColonyEntity colonyEntity = new ColonyEntity();
        colonyEntity.setId(colonyDto.getId());
        colonyEntity.setCity(colonyDto.getCity());
        colonyEntity.setName(colonyDto.getName());
        colonyEntity.setEnable(colonyDto.getIsEnable());
        colonyEntity.setCp(colonyDto.getCp());
        colonyEntity.setStateCode(colonyDto.getStateCode());
        return colonyEntity;
    }

    public List<ColonyDto> colonyEntityListToColonyDtoList(List<ColonyEntity> colonyEntityList) {
        List<ColonyDto> colonyDtoList = new ArrayList<>();
        for (ColonyEntity colonyEntity : colonyEntityList) {
            colonyDtoList.add(colonyEntityToColonyDto(colonyEntity));
        }
        return colonyDtoList;
    }

    public List<ColonyEntity> colonyDtoListToColonyEntityList(List<ColonyDto> colonyDtoList) {
        List<ColonyEntity> colonyEntityList = new ArrayList<>();
        for (ColonyDto colonyDto : colonyDtoList) {
            colonyEntityList.add(colonyDtoToColonyEntity(colonyDto));
        }
        return colonyEntityList;
    }
}
