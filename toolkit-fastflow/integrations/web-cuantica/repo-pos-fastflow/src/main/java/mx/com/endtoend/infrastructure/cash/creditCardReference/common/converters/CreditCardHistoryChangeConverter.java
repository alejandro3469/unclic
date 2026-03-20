package mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardHistoryChangeEntity;

@Component
public class CreditCardHistoryChangeConverter {

	public CreditCardHistoryChangeEntity creditCardHistoryDtoToCreditCardHistoryEntity(
			CreditCardHistoryChangeDto creditCardHistoryChangeDto) {

		CreditCardHistoryChangeEntity creditCardHistoryChangeEntity = new CreditCardHistoryChangeEntity();

		creditCardHistoryChangeEntity.setId(creditCardHistoryChangeDto.getId());
		creditCardHistoryChangeEntity.setCreditCardId(creditCardHistoryChangeDto.getCreditCardId());
		creditCardHistoryChangeEntity.setBankingInstitution(creditCardHistoryChangeDto.getBankingInstitution());
		creditCardHistoryChangeEntity.setType(creditCardHistoryChangeDto.getType());
		creditCardHistoryChangeEntity.setPeriod(creditCardHistoryChangeDto.getPeriod());
		creditCardHistoryChangeEntity.setCommission(creditCardHistoryChangeDto.getCommission().doubleValue());
		creditCardHistoryChangeEntity.setEnable(creditCardHistoryChangeDto.getIsEnable());
		creditCardHistoryChangeEntity.setUpdatedDate(creditCardHistoryChangeDto.getUpdatedDate());
		creditCardHistoryChangeEntity.setModifiedBy(creditCardHistoryChangeDto.getModifiedBy());

		return creditCardHistoryChangeEntity;
	}

	public CreditCardHistoryChangeDto creditCardHistoryEntityToCreditCardHistoryDto(
			CreditCardHistoryChangeEntity creditCardHistoryChangeEntity) {

		CreditCardHistoryChangeDto creditCardHistoryChangeDto = new CreditCardHistoryChangeDto();

		creditCardHistoryChangeDto.setId(creditCardHistoryChangeEntity.getId());
		creditCardHistoryChangeDto.setCreditCardId(creditCardHistoryChangeEntity.getCreditCardId());
		creditCardHistoryChangeDto.setBankingInstitution(creditCardHistoryChangeEntity.getBankingInstitution());
		creditCardHistoryChangeDto.setType(creditCardHistoryChangeEntity.getType());
		creditCardHistoryChangeDto.setPeriod(creditCardHistoryChangeEntity.getPeriod());
		creditCardHistoryChangeDto.setCommission(BigDecimal.valueOf(creditCardHistoryChangeEntity.getCommission()));
		creditCardHistoryChangeDto.setIsEnable(creditCardHistoryChangeEntity.isEnable());
		creditCardHistoryChangeDto.setUpdatedDate(creditCardHistoryChangeEntity.getUpdatedDate());
		creditCardHistoryChangeDto.setModifiedBy(creditCardHistoryChangeEntity.getModifiedBy());

		return creditCardHistoryChangeDto;
	}

	public List<CreditCardHistoryChangeEntity> creditCardHistoryDtoListToCreditCardHistoryEntityList(
			List<CreditCardHistoryChangeDto> creditCardHistoryChangeDtoList) {
		List<CreditCardHistoryChangeEntity> cardHistoryChangeEntities = new ArrayList<>();
		for (CreditCardHistoryChangeDto creditCardHistoryChangeDto : creditCardHistoryChangeDtoList) {
			cardHistoryChangeEntities.add(creditCardHistoryDtoToCreditCardHistoryEntity(creditCardHistoryChangeDto));
		}
		return cardHistoryChangeEntities;
	}

	public List<CreditCardHistoryChangeDto> creditCardHistoryEntityListToCreditCardHistoryDtoList(
			List<CreditCardHistoryChangeEntity> creditCardHistoryChangeEntityList) {
		List<CreditCardHistoryChangeDto> cardHistoryChangeDtos = new ArrayList<>();
		for (CreditCardHistoryChangeEntity creditCardHistoryChangeEntity : creditCardHistoryChangeEntityList) {
			cardHistoryChangeDtos.add(creditCardHistoryEntityToCreditCardHistoryDto(creditCardHistoryChangeEntity));
		}
		return cardHistoryChangeDtos;
	}
}
