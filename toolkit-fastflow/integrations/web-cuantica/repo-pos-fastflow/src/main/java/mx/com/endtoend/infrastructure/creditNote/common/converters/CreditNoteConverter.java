package mx.com.endtoend.infrastructure.creditNote.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteDto;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteEntity;

@Component
public class CreditNoteConverter {

	public CreditNoteEntity creditNoteDtoToCreditNoteEntity(CreditNoteDto creditNoteDto) {

		CreditNoteEntity creditNoteEntity = new CreditNoteEntity();

		creditNoteEntity.setId(creditNoteDto.getId());
		creditNoteEntity.setOrderNumber(creditNoteDto.getOrderNumber());
		creditNoteEntity.setEmployeeId(creditNoteDto.getEmployeeId());
		creditNoteEntity.setOrderCode(creditNoteDto.getOrderCode());
		creditNoteEntity.setOrderId(creditNoteDto.getOrderId());
		creditNoteEntity.setOrderTotal(creditNoteDto.getOrderTotal().doubleValue());
		creditNoteEntity.setCreditNoteTotal(creditNoteDto.getCreditNoteTotal().doubleValue());
		creditNoteEntity.setTotal(creditNoteDto.getIsTotal());

		return creditNoteEntity;

	}

	public CreditNoteDto creditNoteEntityToCreditNoteDto(CreditNoteEntity creditNoteEntity) {

		CreditNoteDto creditNoteDto = new CreditNoteDto();

		creditNoteDto.setId(creditNoteEntity.getId());
		creditNoteDto.setOrderNumber(creditNoteEntity.getOrderNumber());
		creditNoteDto.setEmployeeId(creditNoteEntity.getEmployeeId());
		creditNoteDto.setOrderCode(creditNoteEntity.getOrderCode());
		creditNoteDto.setOrderId(creditNoteEntity.getOrderId());
		creditNoteDto.setOrderTotal(BigDecimal.valueOf(creditNoteEntity.getOrderTotal()));
		creditNoteDto.setCreditNoteTotal(BigDecimal.valueOf(creditNoteEntity.getCreditNoteTotal()));
		creditNoteDto.setIsTotal(creditNoteEntity.isTotal());

		return creditNoteDto;

	}

	public List<CreditNoteEntity> creditNoteDtoListToCreditNoteEntityList(List<CreditNoteDto> creditNoteDtoList) {
		List<CreditNoteEntity> creditNoteEntities = new ArrayList<>();
		for (CreditNoteDto creditNoteDto : creditNoteDtoList) {
			creditNoteEntities.add(creditNoteDtoToCreditNoteEntity(creditNoteDto));
		}
		return creditNoteEntities;

	}

	public List<CreditNoteDto> creditNoteEntityListToCreditNoteDtoList(List<CreditNoteEntity> creditNoteEntityList) {
		List<CreditNoteDto> creditNoteDtos = new ArrayList<>();
		for (CreditNoteEntity creditNoteEntity : creditNoteEntityList) {
			creditNoteDtos.add(creditNoteEntityToCreditNoteDto(creditNoteEntity));
		}
		return creditNoteDtos;

	}
}
