package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.StateEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StateConverter {

    public StateDto stateEntityToStateDto(StateEntity stateEntity) {
        StateDto stateDto = new StateDto();
        stateDto.setId(stateEntity.getId());
        stateDto.setIsEnable(stateEntity.isEnable());
        stateDto.setCode(stateEntity.getCode());
        stateDto.setName(stateEntity.getName());
        return stateDto;
    }

    public StateEntity stateDtoToStateEntity(StateDto stateDto) {
        StateEntity stateEntity = new StateEntity();
        stateEntity.setId(stateDto.getId());
        stateEntity.setEnable(stateDto.getIsEnable());
        stateEntity.setCode(stateDto.getCode());
        stateEntity.setName(stateDto.getName());
        return stateEntity;
    }

    public List<StateDto> stateEntityListToStateDtoList(List<StateEntity> stateEntityList) {
        List<StateDto> stateDtoList = new ArrayList<>();
        for (StateEntity stateEntity : stateEntityList) {
            stateDtoList.add(stateEntityToStateDto(stateEntity));
        }
        return stateDtoList;
    }

    public List<StateEntity> stateDtoListToStateEntityList(List<StateDto> stateDtoList) {
        List<StateEntity> stateEntityList = new ArrayList<>();
        for (StateDto stateDto : stateDtoList) {
            stateEntityList.add(stateDtoToStateEntity(stateDto));
        }
        return stateEntityList;
    }
}
