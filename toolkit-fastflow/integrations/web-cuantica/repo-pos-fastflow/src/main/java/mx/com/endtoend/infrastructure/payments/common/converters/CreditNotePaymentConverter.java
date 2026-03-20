package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditNotePaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.CreditNotePaymentDto;

@Component
public class CreditNotePaymentConverter {

	public CreditNotePaymentEntity creditNotePaymentDtoToCreditNotePaymentEntity(
			CreditNotePaymentDto creditNotePaymentDto, Long paymentId) {

		CreditNotePaymentEntity creditNotePaymentEntity = new CreditNotePaymentEntity();

		creditNotePaymentEntity.setId(creditNotePaymentDto.getId());
		creditNotePaymentEntity.setPaymentId(paymentId);
		creditNotePaymentEntity.setCurrency(creditNotePaymentDto.getCurrency());
		creditNotePaymentEntity.setExchangeRate(creditNotePaymentDto.getExchangeRate());
		creditNotePaymentEntity.setFolio(creditNotePaymentDto.getFolio());
		creditNotePaymentEntity.setCreditNoteCode(creditNotePaymentDto.getCreditNoteCode());
		creditNotePaymentEntity.setAmountApplied(creditNotePaymentDto.getAmountApplied());
		creditNotePaymentEntity.setLine(creditNotePaymentDto.getLine());

		return creditNotePaymentEntity;
	}

	public CreditNotePaymentDto creditNotePaymentEntityToCreditNotePaymentDto(
			CreditNotePaymentEntity creditNotePaymentEntity) {

		CreditNotePaymentDto creditNotePaymentDto = new CreditNotePaymentDto();

		creditNotePaymentDto.setId(creditNotePaymentEntity.getId());
		creditNotePaymentDto.setPaymentId(creditNotePaymentEntity.getPaymentId());
		creditNotePaymentDto.setCurrency(creditNotePaymentEntity.getCurrency());
		creditNotePaymentDto.setExchangeRate(creditNotePaymentEntity.getExchangeRate());
		creditNotePaymentDto.setFolio(creditNotePaymentEntity.getFolio());
		creditNotePaymentDto.setCreditNoteCode(creditNotePaymentEntity.getCreditNoteCode());
		creditNotePaymentDto.setAmountApplied(creditNotePaymentEntity.getAmountApplied());
		creditNotePaymentDto.setLine(creditNotePaymentEntity.getLine());

		return creditNotePaymentDto;
	}

	public List<CreditNotePaymentEntity> creditNotePaymentDtoListToCreditNotePaymentEntityList(
			List<CreditNotePaymentDto> creditNotePaymentDtoList, Long paymentId) {
		List<CreditNotePaymentEntity> creditNotePaymentEntities = new ArrayList<>();
		for (CreditNotePaymentDto creditNotePaymentDto : creditNotePaymentDtoList) {
			creditNotePaymentEntities
					.add(creditNotePaymentDtoToCreditNotePaymentEntity(creditNotePaymentDto, paymentId));
		}
		return creditNotePaymentEntities;
	}

	public List<CreditNotePaymentDto> creditNotePaymentEntityListToCreditNotePaymentDtoList(
			List<CreditNotePaymentEntity> creditNotePaymentEntityList) {
		List<CreditNotePaymentDto> creditNotePaymentDtos = new ArrayList<>();
		for (CreditNotePaymentEntity creditNotePaymentEntity : creditNotePaymentEntityList) {
			creditNotePaymentDtos.add(creditNotePaymentEntityToCreditNotePaymentDto(creditNotePaymentEntity));
		}
		return creditNotePaymentDtos;
	}
}
