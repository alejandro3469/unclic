package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditCardPaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.CreditCardPaymentDto;

@Component
public class CreditCardPaymentConverter {

	public CreditCardPaymentEntity creditCardPaymentDtoToCreditCardPaymentEntity(
			CreditCardPaymentDto creditCardPaymentDto, Long paymentId) {
		
		CreditCardPaymentEntity creditCardPaymentEntity = new CreditCardPaymentEntity();

		creditCardPaymentEntity.setId(creditCardPaymentDto.getId());
		creditCardPaymentEntity.setPaymentId(paymentId);
		creditCardPaymentEntity.setBankingInstitution(creditCardPaymentDto.getBankingInstitution());
		creditCardPaymentEntity.setCode(creditCardPaymentDto.getCode());
		creditCardPaymentEntity.setPeriod(creditCardPaymentDto.getPeriod());
		creditCardPaymentEntity.setCommissionApplied(creditCardPaymentDto.getCommissionApplied());
		creditCardPaymentEntity.setLine(creditCardPaymentDto.getLine());
		creditCardPaymentEntity.setValidityDate(creditCardPaymentDto.getValidityDate());
		creditCardPaymentEntity.setCardNumber(creditCardPaymentDto.getCardNumber());
		creditCardPaymentEntity.setCurrency(creditCardPaymentDto.getCurrency());
		creditCardPaymentEntity.setExchangeRate(creditCardPaymentDto.getExchangeRate());
		creditCardPaymentEntity.setAmountApplied(creditCardPaymentDto.getAmountApplied());
		creditCardPaymentEntity.setCommission(creditCardPaymentDto.getCommission());
		creditCardPaymentEntity.setApplyComission(creditCardPaymentDto.getApplyComission());

		return creditCardPaymentEntity;
	}

	public CreditCardPaymentDto creditCardPaymentEntityToCreditCardPaymentDto(
			CreditCardPaymentEntity creditCardPaymentEntity) {

		CreditCardPaymentDto creditCardPaymentDto = new CreditCardPaymentDto();

		creditCardPaymentDto.setId(creditCardPaymentEntity.getId());
		creditCardPaymentDto.setPaymentId(creditCardPaymentEntity.getPaymentId());
		creditCardPaymentDto.setBankingInstitution(creditCardPaymentEntity.getBankingInstitution());
		creditCardPaymentDto.setCode(creditCardPaymentEntity.getCode());
		creditCardPaymentDto.setPeriod(creditCardPaymentEntity.getPeriod());
		creditCardPaymentDto.setCommissionApplied(creditCardPaymentEntity.getCommissionApplied());
		creditCardPaymentDto.setLine(creditCardPaymentEntity.getLine());
		creditCardPaymentDto.setValidityDate(creditCardPaymentEntity.getValidityDate());
		creditCardPaymentDto.setCardNumber(creditCardPaymentEntity.getCardNumber());
		creditCardPaymentDto.setCurrency(creditCardPaymentEntity.getCurrency());
		creditCardPaymentDto.setExchangeRate(creditCardPaymentEntity.getExchangeRate());
		creditCardPaymentDto.setAmountApplied(creditCardPaymentEntity.getAmountApplied());
		creditCardPaymentDto.setCommission(creditCardPaymentEntity.getCommission());
		creditCardPaymentDto.setApplyComission(creditCardPaymentEntity.getApplyComission());

		return creditCardPaymentDto;
	}

	public List<CreditCardPaymentEntity> creditCardPaymentDtoListToCreditCardPaymentEntityList(
			List<CreditCardPaymentDto> creditCardPaymentDtoList, Long paymentId) {
		List<CreditCardPaymentEntity> creditCardPaymentEntities = new ArrayList<>();
		for (CreditCardPaymentDto creditCardPaymentDto : creditCardPaymentDtoList) {
			creditCardPaymentEntities
					.add(creditCardPaymentDtoToCreditCardPaymentEntity(creditCardPaymentDto, paymentId));
		}
		return creditCardPaymentEntities;
	}

	public List<CreditCardPaymentDto> creditCardPaymentEntityListToCreditCardPaymentDtoList(
			List<CreditCardPaymentEntity> creditCardPaymentEntityList) {
		List<CreditCardPaymentDto> creditCardPaymentDtos = new ArrayList<>();
		for (CreditCardPaymentEntity creditCardPaymentEntity : creditCardPaymentEntityList) {
			creditCardPaymentDtos.add(creditCardPaymentEntityToCreditCardPaymentDto(creditCardPaymentEntity));
		}
		return creditCardPaymentDtos;
	}

}
