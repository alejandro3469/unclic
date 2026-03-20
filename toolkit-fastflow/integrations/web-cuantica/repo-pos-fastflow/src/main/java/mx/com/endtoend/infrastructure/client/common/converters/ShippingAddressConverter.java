package mx.com.endtoend.infrastructure.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.clients.dto.ShippingAddressListDto;
import mx.com.endtoend.infrastructure.client.common.entities.ClientDirectionEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.client.common.entities.ShippingAddressEntity;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDirectionDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ShippingAddressDto;

@Component
public class ShippingAddressConverter {

	public ShippingAddressEntity shippingAddressDtoToShippingAddressEntity(ShippingAddressDto shippingAddressDto) {

		ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();

		shippingAddressEntity.setId(shippingAddressDto.getId());
		shippingAddressEntity.setStreet(shippingAddressDto.getStreet());
		shippingAddressEntity.setNoOutdoor(shippingAddressDto.getNoOutdoor());
		shippingAddressEntity.setNoInterior(shippingAddressDto.getNoInterior());
		shippingAddressEntity.setCp(shippingAddressDto.getCp());
		shippingAddressEntity.setCity(shippingAddressDto.getCity());
		shippingAddressEntity.setColony(shippingAddressDto.getColony());
		shippingAddressEntity.setStateCode(shippingAddressDto.getStateCode());
		shippingAddressEntity.setDelegationCode(shippingAddressDto.getDelegationCode());
		shippingAddressEntity.setFlatCode(shippingAddressDto.getFlatCode());
		shippingAddressEntity.setCoordinatesCode(shippingAddressDto.getCoordinatesCode());

		return shippingAddressEntity;
	}

	public ShippingAddressDto shippingAddressEntityToShippingAddressDto(ShippingAddressEntity shippingAddressEntity) {

		ShippingAddressDto shippingAddressDto = new ShippingAddressDto();

		shippingAddressDto.setId(shippingAddressEntity.getId());
		if (shippingAddressEntity.getStreet() == null) {
			shippingAddressDto.setStreet("");
		} else {
			shippingAddressDto.setStreet(shippingAddressEntity.getStreet());
		}
		if (shippingAddressEntity.getNoOutdoor() == null) {
			shippingAddressDto.setNoOutdoor("");
		} else {
			shippingAddressDto.setNoOutdoor(shippingAddressEntity.getNoOutdoor());
		}
		if (shippingAddressEntity.getNoInterior() == null) {
			shippingAddressDto.setNoInterior("");
		} else {
			shippingAddressDto.setNoInterior(shippingAddressEntity.getNoInterior());
		}
		if (shippingAddressEntity.getCp() == null) {
			shippingAddressDto.setCp("");
		} else {
			shippingAddressDto.setCp(shippingAddressEntity.getCp());
		}
		if (shippingAddressEntity.getCity() == null) {
			shippingAddressDto.setCity("");
		} else {
			shippingAddressDto.setCity(shippingAddressEntity.getCity());
		}
		if (shippingAddressEntity.getColony() == null) {
			shippingAddressDto.setColony("");
		} else {
			shippingAddressDto.setColony(shippingAddressEntity.getColony());
		}
		if (shippingAddressEntity.getStateCode() == null) {
			shippingAddressDto.setStateCode("");
		} else {
			shippingAddressDto.setStateCode(shippingAddressEntity.getStateCode());
		}
		if (shippingAddressEntity.getDelegationCode() == null) {
			shippingAddressDto.setDelegationCode("");
		}
		if (shippingAddressEntity.getDelegationCode() != null) {

			shippingAddressDto.setDelegationCode(shippingAddressEntity.getDelegationCode());

		}
		if (shippingAddressEntity.getFlatCode() == null) {
			shippingAddressDto.setFlatCode("");
		} else {
			shippingAddressDto.setFlatCode(shippingAddressEntity.getFlatCode());
		}
		if (shippingAddressEntity.getCoordinatesCode() == null) {
			shippingAddressDto.setCoordinatesCode("");
		} else {
			shippingAddressDto.setCoordinatesCode(shippingAddressEntity.getCoordinatesCode());
		}
		shippingAddressDto.setUvicationName(shippingAddressEntity.getUvicationName());
		shippingAddressDto.setIdClient(shippingAddressEntity.getIdClient().getId());
//		shippingAddressDto.setIdDirection(shippingAddressEntity.getIdDirection().getId());

		return shippingAddressDto;
	}

	public List<ShippingAddressEntity> shippingAddressDtoListToShippingAddressEntity(
			List<ShippingAddressDto> shippingAddressDtoList) {

		List<ShippingAddressEntity> shippingAddressEntityList = new ArrayList<ShippingAddressEntity>();

		for (ShippingAddressDto shippingAddressDto : shippingAddressDtoList) {
			shippingAddressEntityList.add(shippingAddressDtoToShippingAddressEntity(shippingAddressDto));
		}
		return shippingAddressEntityList;
	}

	public List<ShippingAddressDto> shippingAddressEntityListToShippingAddressDtoListCalzada(
			List<ShippingAddressEntity> shippingAddressEntityList) {

		List<ShippingAddressDto> shippingAddressDtoList = new ArrayList<ShippingAddressDto>();

		for (ShippingAddressEntity shippingAddressEntity : shippingAddressEntityList) {
			shippingAddressDtoList.add(shippingAddressEntityToShippingAddressDto(shippingAddressEntity));
		}
		return shippingAddressDtoList;
	}

	public ShippingAddressEntity shippingAddressDtoToShippingAddressEntityClient(ShippingAddressDto shippingAddressDto,
			ClientEntity clientEntity) {

		ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();

		shippingAddressEntity.setId(shippingAddressDto.getId());
		shippingAddressEntity.setStreet(shippingAddressDto.getStreet());
		shippingAddressEntity.setNoOutdoor(shippingAddressDto.getNoOutdoor());
		shippingAddressEntity.setNoInterior(shippingAddressDto.getNoInterior());
		shippingAddressEntity.setCp(shippingAddressDto.getCp());
		shippingAddressEntity.setCity(shippingAddressDto.getCity());
		shippingAddressEntity.setColony(shippingAddressDto.getColony());
		shippingAddressEntity.setStateCode(shippingAddressDto.getStateCode());
		shippingAddressEntity.setDelegationCode(shippingAddressDto.getDelegationCode());
		shippingAddressEntity.setFlatCode(shippingAddressDto.getFlatCode());
		shippingAddressEntity.setCoordinatesCode(shippingAddressDto.getCoordinatesCode());
		shippingAddressEntity.setUvicationName(shippingAddressDto.getUvicationName());
		shippingAddressEntity.setIdClient(clientEntity);
//		shippingAddressEntity.setIdDirection(clientDirectionEntity);

		return shippingAddressEntity;
	}

	public ShippingAddressEntity clientDirectionDtoToShippingAddressEntity(ClientDirectionDto clientDirectionDto,
			ClientEntity clientEntity, ClientDirectionEntity clientDirectionEntity) {

		ShippingAddressEntity shippingAddressEntity = new ShippingAddressEntity();

		shippingAddressEntity.setId(clientDirectionDto.getId());
		shippingAddressEntity.setStreet(clientDirectionDto.getStreet());
		shippingAddressEntity.setNoOutdoor(clientDirectionDto.getNoOutdoor());
		shippingAddressEntity.setNoInterior(clientDirectionDto.getNoInterior());
		shippingAddressEntity.setCp(clientDirectionDto.getCp());
		shippingAddressEntity.setCity(clientDirectionDto.getCity());
		shippingAddressEntity.setColony(clientDirectionDto.getColony());
		shippingAddressEntity.setStateCode(clientDirectionDto.getStateCode());
		shippingAddressEntity.setDelegationCode(clientDirectionDto.getDelegationCode());
		shippingAddressEntity.setFlatCode(clientDirectionDto.getFlatCode());
		shippingAddressEntity.setCoordinatesCode(clientDirectionDto.getCoordinatesCode());
//			shippingAddressEntity.setUvicationName(clientDirectionDto.getUvicationName());
		shippingAddressEntity.setIdClient(clientEntity);
//			shippingAddressEntity.setIdDirection(clientDirectionEntity);

		return shippingAddressEntity;
	}

	public ShippingAddressListDto shippingAddressCatalog(ShippingAddressEntity shippingAddressEntity) {

		ShippingAddressListDto shippingAddressDto = new ShippingAddressListDto();

		shippingAddressDto.setId(shippingAddressEntity.getId());

		shippingAddressDto.setUvicationName(shippingAddressEntity.getUvicationName());

		return shippingAddressDto;
	}

	public List<ShippingAddressListDto> shippingAddressCatalogList(
			List<ShippingAddressEntity> shippingAddressEntityList) {
		List<ShippingAddressListDto> shippingAddressDtoList = new ArrayList<ShippingAddressListDto>();

		for (ShippingAddressEntity shippingAddressEntity : shippingAddressEntityList) {
			shippingAddressDtoList.add(shippingAddressCatalog(shippingAddressEntity));
		}
		return shippingAddressDtoList;
	}
}