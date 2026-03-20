package mx.com.endtoend.infrastructure.creditNote.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.creditNote.dto.CreditNoteHeaderDto;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteHeaderEntity;

@Component
public class CreditNoteHeaderConverter {

	public CreditNoteHeaderEntity creditNoteHeaderDtoToCreditNoteHeaderEntity(CreditNoteHeaderDto creditNoteHeaderDto) {

		CreditNoteHeaderEntity creditNoteHeaderEntity = new CreditNoteHeaderEntity();

		creditNoteHeaderEntity.setId(creditNoteHeaderDto.getId());
		creditNoteHeaderEntity.setFolio(creditNoteHeaderDto.getFolio());
		creditNoteHeaderEntity.setCreditNoteCode(creditNoteHeaderDto.getCreditNoteCode());
		creditNoteHeaderEntity.setTotalAmount(creditNoteHeaderDto.getTotalAmount());
		creditNoteHeaderEntity.setUsedAmount(creditNoteHeaderDto.getUsedAmountM());
		creditNoteHeaderEntity.setPendingAmount(creditNoteHeaderDto.getPendingAmount());
		creditNoteHeaderEntity.setCreationDate(creditNoteHeaderDto.getCreationDate());
		creditNoteHeaderEntity.setClientId(creditNoteHeaderDto.getClientId());
		creditNoteHeaderEntity.setClientNumber(creditNoteHeaderDto.getClientNumber());
		creditNoteHeaderEntity.setSaleEmployeeId(creditNoteHeaderDto.getSaleEmployeeId());
		creditNoteHeaderEntity.setCurrency(creditNoteHeaderDto.getCurrency());
		creditNoteHeaderEntity.setExchangeRate(creditNoteHeaderDto.getExchangeRate());
		creditNoteHeaderEntity.setPrinted(creditNoteHeaderDto.getIsPrinted());

		return creditNoteHeaderEntity;

	}

	public CreditNoteHeaderDto creditNoteHearderEntityToCreditNoteHeaderDto(
			CreditNoteHeaderEntity creditNoteHeaderEntity) {

		CreditNoteHeaderDto creditNoteHeaderDto = new CreditNoteHeaderDto();

		creditNoteHeaderDto.setId(creditNoteHeaderEntity.getId());
		creditNoteHeaderDto.setFolio(creditNoteHeaderEntity.getFolio());
		creditNoteHeaderDto.setCreditNoteCode(creditNoteHeaderEntity.getCreditNoteCode());
		creditNoteHeaderDto.setTotalAmount(creditNoteHeaderEntity.getTotalAmount());
		creditNoteHeaderDto.setUsedAmountM(creditNoteHeaderEntity.getUsedAmount());
		creditNoteHeaderDto.setPendingAmount(creditNoteHeaderEntity.getPendingAmount());
		creditNoteHeaderDto.setCreationDate(creditNoteHeaderEntity.getCreationDate());
		creditNoteHeaderDto.setClientId(creditNoteHeaderEntity.getClientId());
		creditNoteHeaderDto.setClientNumber(creditNoteHeaderEntity.getClientNumber());
		creditNoteHeaderDto.setSaleEmployeeId(creditNoteHeaderEntity.getSaleEmployeeId());
		creditNoteHeaderDto.setCurrency(creditNoteHeaderEntity.getCurrency());
		creditNoteHeaderDto.setExchangeRate(creditNoteHeaderEntity.getExchangeRate());
		creditNoteHeaderDto.setCreditNoteId(creditNoteHeaderEntity.getCreditNote().getId());
		creditNoteHeaderDto.setIsPrinted(creditNoteHeaderEntity.isPrinted());

		return creditNoteHeaderDto;
	}

	public List<CreditNoteHeaderEntity> creditNoteHeaderDtoListToCreditNoteHeaderEntityList(
			List<CreditNoteHeaderDto> creditNoteHeaderDtoList) {
		List<CreditNoteHeaderEntity> creditNoteHeaderEntities = new ArrayList<>();
		for (CreditNoteHeaderDto creditNoteHeaderDto : creditNoteHeaderDtoList) {
			creditNoteHeaderEntities.add(creditNoteHeaderDtoToCreditNoteHeaderEntity(creditNoteHeaderDto));
		}
		return creditNoteHeaderEntities;

	}

	public List<CreditNoteHeaderDto> creditNoteHearderEntityListToCreditNoteHeaderDtoList(
			List<CreditNoteHeaderEntity> creditNoteHeaderEntityList) {
		List<CreditNoteHeaderDto> creditNoteHeaderDtos = new ArrayList<>();
		for (CreditNoteHeaderEntity creditNoteHeaderEntity : creditNoteHeaderEntityList) {
			creditNoteHeaderDtos.add(creditNoteHearderEntityToCreditNoteHeaderDto(creditNoteHeaderEntity));
		}
		return creditNoteHeaderDtos;
	}
}
