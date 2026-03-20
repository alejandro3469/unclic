package mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardEntity;

@Component
public class CreditCardReferenceConverter {

	public CreditCardEntity creditCardDtoToCreditCardEntity(CreditCardDto creditCardDto) {

		CreditCardEntity creditCardEntity = new CreditCardEntity();

		creditCardEntity.setId(creditCardDto.getId());
		creditCardEntity.setBankingInstitution(creditCardDto.getBankingInstitution());
		creditCardEntity.setCode(creditCardDto.getCode());
		creditCardEntity.setType(creditCardDto.getType());
		creditCardEntity.setEnable(creditCardDto.getIsEnable());
		creditCardEntity.setMinimumAmount(creditCardDto.getMinimumAmount());
		creditCardEntity.setIsCommissionApply(creditCardDto.getIsCommissionApply());

		return creditCardEntity;
	}

	public CreditCardDto creditCardEntityToCreditCardDto(CreditCardEntity creditCardEntity) {

		CreditCardDto creditCardDto = new CreditCardDto();

		creditCardDto.setId(creditCardEntity.getId());
		creditCardDto.setBankingInstitution(creditCardEntity.getBankingInstitution());
		creditCardDto.setCode(creditCardEntity.getCode());
		creditCardDto.setType(creditCardEntity.getType());
		creditCardDto.setIsEnable(creditCardEntity.isEnable());
		creditCardDto.setMinimumAmount(creditCardEntity.getMinimumAmount());
		creditCardDto.setIsCommissionApply(creditCardEntity.getIsCommissionApply());

		return creditCardDto;
	}

	public List<CreditCardEntity> creditCardDtoListToCreditCardEntityList(List<CreditCardDto> creditCardDtoList) {
		List<CreditCardEntity> creditCardEntities = new ArrayList<>();
		for (CreditCardDto creditCardDto : creditCardDtoList) {
			creditCardEntities.add(creditCardDtoToCreditCardEntity(creditCardDto));
		}
		return creditCardEntities;
	}

	public List<CreditCardDto> creditCardEntityListToCreditCardDtoList(List<CreditCardEntity> creditCardEntityList) {
		List<CreditCardDto> creditCardDtos = new ArrayList<>();
		for (CreditCardEntity creditCardEntity : creditCardEntityList) {
			creditCardDtos.add(creditCardEntityToCreditCardDto(creditCardEntity));
		}
		return creditCardDtos;
	}
}
