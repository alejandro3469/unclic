package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.CoordinateEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoordianteConverter {

    public CoordinateDto coordinateEntityToCoordinateDto(CoordinateEntity coordinateEntity) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setId(coordinateEntity.getId());
        coordinateDto.setIsEnable(coordinateEntity.isEnable());
        coordinateDto.setCode(coordinateEntity.getCode());
        coordinateDto.setName(coordinateEntity.getName());
        return coordinateDto;
    }

    public CoordinateEntity coordinateDtoToCoordinateEntity(CoordinateDto coordinateDto) {
        CoordinateEntity coordinateEntity = new CoordinateEntity();
        coordinateEntity.setId(coordinateDto.getId());
        coordinateEntity.setEnable(coordinateDto.getIsEnable());
        coordinateEntity.setCode(coordinateDto.getCode());
        coordinateEntity.setName(coordinateDto.getName());
        return coordinateEntity;
    }

    public List<CoordinateDto> coordinateEntityListToCoordianteDtoList(List<CoordinateEntity> coordinateEntityList) {
        List<CoordinateDto> coordinateDtoList = new ArrayList<>();
        for (CoordinateEntity coordinateEntity : coordinateEntityList) {
            coordinateDtoList.add(coordinateEntityToCoordinateDto(coordinateEntity));
        }
        return coordinateDtoList;
    }

    public List<CoordinateEntity> coordinateDtoListToCoordinateEntityList(List<CoordinateDto> coordinateDtoList) {
        List<CoordinateEntity> coordinateEntityList = new ArrayList<>();
        for (CoordinateDto coordinateDto : coordinateDtoList) {
            coordinateEntityList.add(coordinateDtoToCoordinateEntity(coordinateDto));
        }
        return coordinateEntityList;
    }

}


