package mx.com.endtoend.infrastructure.catalogue.client.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.infrastructure.catalogue.client.common.entities.ContactMethodEntity;

@Component
public class ContactMethodConverter {

	public ContactMethodDto contactMethodEntityToContactMethodDto(ContactMethodEntity contactMethodEntity) {

		ContactMethodDto contactMethodDto = new ContactMethodDto();

		contactMethodDto.setId(contactMethodEntity.getId());
		contactMethodDto.setIsEnable(contactMethodEntity.isEnable());
		contactMethodDto.setCode(contactMethodEntity.getCode());
		contactMethodDto.setValue(contactMethodEntity.getValue());

		return contactMethodDto;
	}

	public ContactMethodEntity contactMethodDtoToContactMethodEntity(ContactMethodDto contactMethodDto) {

		ContactMethodEntity contactMethodEntity = new ContactMethodEntity();

		contactMethodEntity.setId(contactMethodDto.getId());
		contactMethodEntity.setEnable(contactMethodDto.getIsEnable());
		contactMethodEntity.setCode(contactMethodDto.getCode());
		contactMethodEntity.setValue(contactMethodDto.getValue());

		return contactMethodEntity;
	}

	public List<ContactMethodDto> contactMethodEntityListToConctactMethodDtoList(
			List<ContactMethodEntity> contactMethodEntityList) {
		List<ContactMethodDto> conatctMethodDtoList = new ArrayList<ContactMethodDto>();
		for (ContactMethodEntity contactMethodEntity : contactMethodEntityList) {
			conatctMethodDtoList.add(contactMethodEntityToContactMethodDto(contactMethodEntity));
		}
		return conatctMethodDtoList;
	}

	public List<ContactMethodEntity> contactMethodtoListToContactMethodEntityList(
			List<ContactMethodDto> contactMethodDtoList) {
		List<ContactMethodEntity> contactMethodEntityList = new ArrayList<ContactMethodEntity>();
		for (ContactMethodDto contactMethodDto : contactMethodDtoList) {
			contactMethodEntityList.add(contactMethodDtoToContactMethodEntity(contactMethodDto));
		}
		return contactMethodEntityList;
	}
}
