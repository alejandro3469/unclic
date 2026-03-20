package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.CreditNoteTypeEntity;

@Component
public class CreditNoteTypeConverter {

	public CreditNoteTypeEntity creditNoteTypeDtoToCreditNoteTypeEntity(CreditNoteTypeDto creditNoteTypeDto) {

		CreditNoteTypeEntity creditNoteTypeEntity = new CreditNoteTypeEntity();

		creditNoteTypeEntity.setId(creditNoteTypeDto.getId());
		creditNoteTypeEntity.setCode(creditNoteTypeDto.getCode());
		creditNoteTypeEntity.setType(creditNoteTypeDto.getType());

		return creditNoteTypeEntity;
	}

	public CreditNoteTypeDto creditNoteTypeEntityToCreditNoteTypeDto(CreditNoteTypeEntity creditNoteTypeEntity) {

		CreditNoteTypeDto creditNoteTypeDto = new CreditNoteTypeDto();

		creditNoteTypeDto.setId(creditNoteTypeEntity.getId());
		creditNoteTypeDto.setCode(creditNoteTypeEntity.getCode());
		creditNoteTypeDto.setType(creditNoteTypeEntity.getType());

		return creditNoteTypeDto;
	}

	public List<CreditNoteTypeEntity> creditNoteTypeDtoListToCreditNoteTypeEntityList(
			List<CreditNoteTypeDto> creditNoteTypeDtoList) {
		List<CreditNoteTypeEntity> creditNoteTypeEntities = new ArrayList<>();
		for (CreditNoteTypeDto creditNoteTypeDto : creditNoteTypeDtoList) {
			creditNoteTypeEntities.add(creditNoteTypeDtoToCreditNoteTypeEntity(creditNoteTypeDto));
		}
		return creditNoteTypeEntities;
	}

	public List<CreditNoteTypeDto> creditNoteTypeEntityListToCreditNoteTypeDtoList(
			List<CreditNoteTypeEntity> creditNoteTypeEntityList) {
		List<CreditNoteTypeDto> creditNoteTypeDtos = new ArrayList<>();
		for (CreditNoteTypeEntity creditNoteTypeEntity : creditNoteTypeEntityList) {
			creditNoteTypeDtos.add(creditNoteTypeEntityToCreditNoteTypeDto(creditNoteTypeEntity));
		}
		return creditNoteTypeDtos;
	}

}
