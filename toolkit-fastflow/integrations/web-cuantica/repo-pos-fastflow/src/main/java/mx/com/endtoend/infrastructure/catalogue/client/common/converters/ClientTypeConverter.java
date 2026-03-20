package mx.com.endtoend.infrastructure.catalogue.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ClientTypeEntity;

@Component
public class ClientTypeConverter {

	public ClientTypeDto clientTypeEntityToClientTypeDto(ClientTypeEntity clientTypeEntity) {

		ClientTypeDto clientTypeDto = new ClientTypeDto();

		clientTypeDto.setId(clientTypeEntity.getId());
		clientTypeDto.setIsEnable(clientTypeEntity.isEnable());
		clientTypeDto.setCode(clientTypeEntity.getCode());
		clientTypeDto.setValue(clientTypeEntity.getValue());

		return clientTypeDto;
	}

	public ClientTypeEntity clientTypeDtoToClientTypeEntity(ClientTypeDto clientTypeDto) {

		ClientTypeEntity clientTypeEntity = new ClientTypeEntity();

		clientTypeEntity.setId(clientTypeDto.getId());
		clientTypeEntity.setEnable(clientTypeDto.getIsEnable());
		clientTypeEntity.setCode(clientTypeDto.getCode());
		clientTypeEntity.setValue(clientTypeDto.getValue());

		return clientTypeEntity;
	}

	public List<ClientTypeDto> clientTypeEntityListToClientTypeDtoList(List<ClientTypeEntity> clientTypeEntityList) {
		List<ClientTypeDto> clientTypeDtoList = new ArrayList<ClientTypeDto>();
		for (ClientTypeEntity clientTypeEntity : clientTypeEntityList) {
			clientTypeDtoList.add(clientTypeEntityToClientTypeDto(clientTypeEntity));
		}
		return clientTypeDtoList;
	}
	
	public List<ClientTypeEntity> clientTypeDtoListToClientTypeEntityList(List<ClientTypeDto> clientTypeDtoList){
		List<ClientTypeEntity> clientTypeEntityList = new ArrayList<ClientTypeEntity>();
		for (ClientTypeDto clientTypeDto : clientTypeDtoList) {
			clientTypeEntityList.add(clientTypeDtoToClientTypeEntity(clientTypeDto));
		}
		return clientTypeEntityList;
	}

}
