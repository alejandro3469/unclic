package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.FlatEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlatConverter {

    public FlatDto flatEntityToFlatDto(FlatEntity flatEntity) {
        FlatDto flatDto = new FlatDto();
        flatDto.setId(flatEntity.getId());
        flatDto.setIsEnable(flatEntity.isEnable());
        flatDto.setCode(flatEntity.getCode());
        flatDto.setName(flatEntity.getName());
        return flatDto;
    }

    public FlatEntity flatDtoToFlatEntity(FlatDto flatDto) {
        FlatEntity flatEntity = new FlatEntity();
        flatEntity.setId(flatDto.getId());
        flatEntity.setEnable(flatDto.getIsEnable());
        flatEntity.setCode(flatDto.getCode());
        flatEntity.setName(flatDto.getName());
        return flatEntity;
    }

    public List<FlatDto> flatEntityListToFlatDtoList(List<FlatEntity> flatEntityList) {
        List<FlatDto> flatDtoList = new ArrayList<>();
        for (FlatEntity flatEntity : flatEntityList) {
            flatDtoList.add(flatEntityToFlatDto(flatEntity));
        }
        return flatDtoList;
    }

    public List<FlatEntity> flatDtoListToFlatEntityList(List<FlatDto> flatDtoList) {
        List<FlatEntity> flatEntityList = new ArrayList<>();
        for (FlatDto flatDto : flatDtoList) {
            flatEntityList.add(flatDtoToFlatEntity(flatDto));
        }
        return flatEntityList;
    }
}
