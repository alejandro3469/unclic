package mx.com.endtoend.infrastructure.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.orders.common.entities.AddressEntity;
import mx.com.endtoend.smart.bussiness.model.orders.dto.AddressDto;

@Component
public class AddressConverter {

	public AddressDto addressEntityToAddressDto(AddressEntity addressEntity) {

		AddressDto addressDto = new AddressDto();

		addressDto.setId(addressEntity.getId());
		addressDto.setStreet(addressEntity.getStreet() != null ? addressEntity.getStreet() : "");
		addressDto.setColony(addressEntity.getColony() != null ? addressEntity.getColony() : "");
		addressDto.setState(addressEntity.getState() != null ? addressEntity.getState() : "");
		addressDto.setStateCode(addressEntity.getStateCode() != null ? addressEntity.getStateCode() : "");
		addressDto.setDelegation(addressEntity.getDelegation() != null ? addressEntity.getDelegation() : "");
		addressDto
				.setDelegationCode(addressEntity.getDelegationCode() != null ? addressEntity.getDelegationCode() : "");
		addressDto
				.setInteriorNumber(addressEntity.getInteriorNumber() != null ? addressEntity.getInteriorNumber() : "");
		addressDto.setOutdoorNumber(addressEntity.getOutdoorNumber() != null ? addressEntity.getOutdoorNumber() : "");
		addressDto.setCp(addressEntity.getCp() != null ? addressEntity.getCp() : "");
		addressDto.setCity(addressEntity.getCity() != null ? addressEntity.getCity() : "");
		addressDto.setAddressType(addressEntity.getAddressType() != null ? addressEntity.getAddressType() : "");
		addressDto.setFlat(addressEntity.getFlat() != null ? addressEntity.getFlat() : "");
		addressDto.setFlatCode(addressEntity.getFlatCode() != null ? addressEntity.getFlatCode() : "");
		addressDto.setCoordinate(addressEntity.getCoordinate() != null ? addressEntity.getCoordinate() : "");
		addressDto
				.setCoordinateCode(addressEntity.getCoordinateCode() != null ? addressEntity.getCoordinateCode() : "");

		return addressDto;
	}

	public AddressEntity addressDtoToAddressEntity(AddressDto addressDto) {

		AddressEntity addressEntity = new AddressEntity();

		addressEntity.setId(addressDto.getId());
		addressEntity.setStreet(addressDto.getStreet() != null ? addressDto.getStreet() : " ");
		addressEntity.setColony(addressDto.getColony() != null ? addressDto.getColony() : " ");
		addressEntity.setState(addressDto.getState() != null ? addressDto.getState() : " ");
		addressEntity.setDelegation(addressDto.getDelegation() != null ? addressDto.getDelegation() : " ");
		addressEntity.setInteriorNumber(addressDto.getInteriorNumber() != null ? addressDto.getInteriorNumber() : " ");
		addressEntity.setOutdoorNumber(addressDto.getOutdoorNumber() != null ? addressDto.getOutdoorNumber() : " ");
		addressEntity.setCp(addressDto.getCp() != null ? addressDto.getCp() : " ");
		addressEntity.setCity(addressDto.getCity() != null ? addressDto.getCity() : " ");
		addressEntity.setAddressType(addressDto.getAddressType() != null ? addressDto.getAddressType() : " ");
		addressEntity.setFlat(addressDto.getFlat() != null ? addressDto.getFlat() : " ");
		addressEntity.setCoordinate(addressDto.getCoordinate() != null ? addressDto.getCoordinate() : " ");
		addressEntity.setStateCode(addressDto.getStateCode() != null ? addressDto.getStateCode() : " ");
		addressEntity.setDelegationCode(addressDto.getDelegationCode() != null ? addressDto.getDelegationCode() : " ");
		addressEntity.setFlatCode(addressDto.getFlatCode() != null ? addressDto.getFlatCode() : " ");
		addressEntity.setCoordinateCode(addressDto.getCoordinateCode() != null ? addressDto.getCoordinateCode() : " ");

		return addressEntity;
	}

	public List<AddressEntity> addressDtoListToAddressEntity(List<AddressDto> addressDtoList) {
		List<AddressEntity> addresEntityList = new ArrayList<AddressEntity>();
		for (AddressDto addressDto : addressDtoList) {
			addresEntityList.add(addressDtoToAddressEntity(addressDto));
		}
		return addresEntityList;
	}

	public List<AddressDto> addresEntityListToAddresDtoList(List<AddressEntity> addressEntityList) {
		List<AddressDto> addresDtoList = new ArrayList<AddressDto>();
		for (AddressEntity addressEntity : addressEntityList) {
			addresDtoList.add(addressEntityToAddressDto(addressEntity));
		}
		return addresDtoList;
	}
}
