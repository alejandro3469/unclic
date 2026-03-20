package mx.com.endtoend.infrastructure.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientMailEntity;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientMailDto;

@Component
public class ClientMailConverter {

	public ClientMailEntity clientMailDtoToClientMailEntity(ClientMailDto clientMailDto) {
		
		ClientMailEntity clientMailEntity = new ClientMailEntity();
		
		clientMailEntity.setId(clientMailDto.getId());
		clientMailEntity.setMail(clientMailDto.getMail());
		clientMailEntity.getIdClient().setId(clientMailDto.getIdClient());
		
		return clientMailEntity;
	}
	
	public ClientMailDto clientMailEntityToClientMailDto(ClientMailEntity clientMailEntity) {
		
		ClientMailDto clientMailDto = new ClientMailDto();
		
		clientMailDto.setId(clientMailEntity.getId());
		clientMailDto.setMail(clientMailEntity.getMail());
		clientMailDto.setIdClient(clientMailEntity.getIdClient().getId());
		
		return clientMailDto;
	}
	
	public List<ClientMailEntity> clientMailDtoListToClientMailEntity(List<ClientMailDto> clientMailDto){
			
		List<ClientMailEntity> shippingAddressEntityList = new ArrayList<ClientMailEntity>();
			
		for (ClientMailDto clientMailDtoList : clientMailDto) {
			shippingAddressEntityList.add(clientMailDtoToClientMailEntity(clientMailDtoList));
		}
		return shippingAddressEntityList;
	}
		
	public List<ClientMailDto> clientMailEntityListclientMailDtoListCalzada(List<ClientMailEntity> ClientMailEntity){
				
		List<ClientMailDto> clientMailDtosList = new ArrayList<ClientMailDto>();
				
		for (ClientMailEntity clientMailEntityList : ClientMailEntity) {
			clientMailDtosList.add(clientMailEntityToClientMailDto(clientMailEntityList));
		}
			return clientMailDtosList;
	}
	
	public ClientMailEntity clientMailDtoToClientMailEntityClient(ClientMailDto clientMailDto, ClientEntity clientEntity) {
		
		ClientMailEntity clientMailEntity = new ClientMailEntity();
		
		clientMailEntity.setId(clientMailDto.getId());
		clientMailEntity.setMail(clientMailDto.getMail());
		clientMailEntity.setIdClient(clientEntity);
		
		return clientMailEntity;
	}
}
