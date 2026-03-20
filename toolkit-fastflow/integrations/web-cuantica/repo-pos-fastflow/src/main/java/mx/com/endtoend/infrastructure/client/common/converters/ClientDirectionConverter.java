package mx.com.endtoend.infrastructure.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDirectionDto;

@Component
public class ClientDirectionConverter {

	public ClientDirectionEntity clientDirectionDtoToClientDirectionEntity(ClientDirectionDto clientDirectionDto) {
		ClientDirectionEntity clientDirectionEntity = new ClientDirectionEntity();

		clientDirectionEntity.setId(clientDirectionDto.getId());
		clientDirectionEntity.setCp(clientDirectionDto.getCp());
		clientDirectionEntity.setStateCode(clientDirectionDto.getStateCode());
		clientDirectionEntity.setNoOutdoor(clientDirectionDto.getNoOutdoor());
		clientDirectionEntity.setStreet(clientDirectionDto.getStreet());
		clientDirectionEntity.setDelegationCode(clientDirectionDto.getDelegationCode());
		clientDirectionEntity.setNoInterior(clientDirectionDto.getNoInterior());
		clientDirectionEntity.setCity(clientDirectionDto.getCity());
		clientDirectionEntity.setFlatCode(clientDirectionDto.getFlatCode());
		clientDirectionEntity.setColony(clientDirectionDto.getColony());
		clientDirectionEntity.setCoordinatesCode(clientDirectionDto.getCoordinatesCode());
//		clientDirectionEntity.setUvicationName(clientDirectionDto.getUvicationName());

		return clientDirectionEntity;

	}

	public ClientDirectionDto clientDirectionEntityToClientDirectionDto(ClientDirectionEntity clientDirectionEntity) {

		ClientDirectionDto clientDirectionDto = new ClientDirectionDto();

		clientDirectionDto.setId(clientDirectionEntity.getId());
		if (clientDirectionEntity.getCp() == null) {
			clientDirectionDto.setCp("");
		} else {
			clientDirectionDto.setCp(clientDirectionEntity.getCp());
		}
		if (clientDirectionEntity.getStateCode() == null) {
			clientDirectionDto.setStateCode("");
		} else {
			clientDirectionDto.setStateCode(clientDirectionEntity.getStateCode());
		}
		if (clientDirectionEntity.getNoOutdoor() == null) {
			clientDirectionDto.setNoOutdoor("");
		} else {
			clientDirectionDto.setNoOutdoor(clientDirectionEntity.getNoOutdoor());
		}
		if (clientDirectionEntity.getStreet() == null) {
			clientDirectionDto.setStreet("");
		} else {
			clientDirectionDto.setStreet(clientDirectionEntity.getStreet());
		}
		if (clientDirectionEntity.getDelegationCode() != null) {

			clientDirectionDto.setDelegationCode(clientDirectionEntity.getDelegationCode());

		}
		if (clientDirectionEntity.getDelegationCode() == null) {
			clientDirectionDto.setDelegationCode("");
		}
		if (clientDirectionEntity.getNoInterior() == null) {
			clientDirectionDto.setNoInterior("");
		} else {
			clientDirectionDto.setNoInterior(clientDirectionEntity.getNoInterior());
		}
		clientDirectionDto.setCity(clientDirectionEntity.getCity());
		if (clientDirectionEntity.getFlatCode() == null) {
			clientDirectionDto.setFlatCode("");
		} else {
			clientDirectionDto.setFlatCode(clientDirectionEntity.getFlatCode());
		}
		clientDirectionDto.setColony(clientDirectionEntity.getColony());
		if (clientDirectionEntity.getCoordinatesCode() == null) {
			clientDirectionDto.setCoordinatesCode("");
		} else {
			clientDirectionDto.setCoordinatesCode(clientDirectionEntity.getCoordinatesCode());
		}
//		clientDirectionDto.setUvicationName(clientDirectionEntity.getUvicationName());
		clientDirectionDto.setIdClient(clientDirectionEntity.getIdClient().getId());

		return clientDirectionDto;
	}

	public List<ClientDirectionDto> clientDirectionEntityListToClientDirectionDtoList(
			List<ClientDirectionEntity> clientDirectionEntityList) {
		List<ClientDirectionDto> clientDirectionDtoList = new ArrayList<ClientDirectionDto>();
		for (ClientDirectionEntity clientDirectionEntity : clientDirectionEntityList) {
			clientDirectionDtoList.add(clientDirectionEntityToClientDirectionDto(clientDirectionEntity));
		}
		return clientDirectionDtoList;
	}

	public List<ClientDirectionEntity> clientDirectionDtoListToClientDirectionEntityList(
			List<ClientDirectionDto> clientDirectionDtoList) {
		List<ClientDirectionEntity> clientDirectionEntityList = new ArrayList<ClientDirectionEntity>();
		for (ClientDirectionDto clientDirectionDto : clientDirectionDtoList) {
			clientDirectionEntityList.add(clientDirectionDtoToClientDirectionEntity(clientDirectionDto));
		}
		return clientDirectionEntityList;
	}

	public ClientDirectionEntity clientDirectionDtoToClientDirectionEntityClient(ClientDirectionDto clientDirectionDto,
			ClientEntity clientEntity) {
		ClientDirectionEntity clientDirectionEntity = new ClientDirectionEntity();

		clientDirectionEntity.setId(clientDirectionDto.getId());
		clientDirectionEntity.setCp(clientDirectionDto.getCp());
		clientDirectionEntity.setStateCode(clientDirectionDto.getStateCode());
		clientDirectionEntity.setNoOutdoor(clientDirectionDto.getNoOutdoor());
		clientDirectionEntity.setStreet(clientDirectionDto.getStreet());
		clientDirectionEntity.setDelegationCode(clientDirectionDto.getDelegationCode());
		clientDirectionEntity.setNoInterior(clientDirectionDto.getNoInterior());
		clientDirectionEntity.setCity(clientDirectionDto.getCity());
		clientDirectionEntity.setFlatCode(clientDirectionDto.getFlatCode());
		clientDirectionEntity.setColony(clientDirectionDto.getColony());
		clientDirectionEntity.setCoordinatesCode(clientDirectionDto.getCoordinatesCode());
//		clientDirectionEntity.setUvicationName(clientDirectionDto.getUvicationName());
		clientDirectionEntity.setIdClient(clientEntity);

		return clientDirectionEntity;

	}

//	public ClientDirectionDto clientDirectionDtoToClientDirectionEntityClient(ClientDirectionEntity clientDirectionEntity, ClientDto clientDto ) {
//		
//		ClientDirectionDto clientDirectionDto = new ClientDirectionDto();
//		
//		clientDirectionDto.setId(clientDirectionEntity.getId());
//		clientDirectionDto.setCp(clientDirectionEntity.getCp());
//		clientDirectionDto.setState(clientDirectionEntity.getState());
//		clientDirectionDto.setNoOutdoor(clientDirectionEntity.getNoOutdoor());
//		clientDirectionDto.setStreet(clientDirectionEntity.getStreet());
//		clientDirectionDto.setDelegation(clientDirectionEntity.getDelegation());
//		clientDirectionDto.setNoInterior(clientDirectionEntity.getNoInterior());
//		clientDirectionDto.setCity(clientDirectionEntity.getCity());
//		clientDirectionDto.setFlat(clientDirectionEntity.getFlat());
//		clientDirectionDto.setColony(clientDirectionEntity.getColony());
//		clientDirectionDto.setCoordinates(clientDirectionEntity.getCoordinates());
//		clientDirectionDto.setUvicationName(clientDirectionEntity.getUvicationName());
//		clientDirectionDto.setIdClient(clientDto);
//		
//		return clientDirectionDto;
//	}

	public ClientDirectionDto clientDirectionCatalog(ClientDirectionEntity clientDirectionEntity) {

		ClientDirectionDto clientDirectionDto = new ClientDirectionDto();

		clientDirectionDto.setId(clientDirectionEntity.getId());

//		clientDirectionDto.setUvicationName(clientDirectionEntity.getUvicationName());

		return clientDirectionDto;
	}

	public List<ClientDirectionDto> clientDirectionCatalogList(List<ClientDirectionEntity> clientDirectionEntityList) {
		List<ClientDirectionDto> clientDirectionDtoList = new ArrayList<ClientDirectionDto>();
		for (ClientDirectionEntity clientDirectionEntity : clientDirectionEntityList) {
			clientDirectionDtoList.add(clientDirectionCatalog(clientDirectionEntity));
		}
		return clientDirectionDtoList;
	}

}