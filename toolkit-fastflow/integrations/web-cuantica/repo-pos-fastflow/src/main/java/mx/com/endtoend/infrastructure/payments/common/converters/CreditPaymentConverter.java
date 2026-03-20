package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.CreditPaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.CreditPaymentDto;

@Component
public class CreditPaymentConverter {

	public CreditPaymentEntity creditPaymentDtoToCreditPaymentEntity(CreditPaymentDto creditPaymentDto,
			Long paymentId) {

		CreditPaymentEntity creditPaymentEntity = new CreditPaymentEntity();

		creditPaymentEntity.setId(creditPaymentDto.getId());
		creditPaymentEntity.setLine(creditPaymentDto.getLine());
		creditPaymentEntity.setPaymentId(paymentId);
		creditPaymentEntity.setCurrency(creditPaymentDto.getCurrency());
		creditPaymentEntity.setExchangeRate(creditPaymentDto.getExchangeRate());
		creditPaymentEntity.setReferenceId(creditPaymentDto.getReferenceId());
		creditPaymentEntity.setAmountApplied(creditPaymentDto.getAmountApplied());

		return creditPaymentEntity;

	}

	public CreditPaymentDto creditPaymentEntityToCreditPaymentDto(CreditPaymentEntity creditPaymentEntity) {

		CreditPaymentDto creditPaymentDto = new CreditPaymentDto();

		creditPaymentDto.setId(creditPaymentEntity.getId());
		creditPaymentDto.setLine(creditPaymentEntity.getLine());
		creditPaymentDto.setPaymentId(creditPaymentEntity.getPaymentId());
		creditPaymentDto.setCurrency(creditPaymentEntity.getCurrency());
		creditPaymentDto.setExchangeRate(creditPaymentEntity.getExchangeRate());
		creditPaymentDto.setReferenceId(creditPaymentEntity.getReferenceId());
		creditPaymentDto.setAmountApplied(creditPaymentEntity.getAmountApplied());

		return creditPaymentDto;
	}

	public List<CreditPaymentEntity> creditPaymentDtoListToCreditPaymentEntityList(
			List<CreditPaymentDto> creditPaymentDtoList, Long paymentId) {
		List<CreditPaymentEntity> creditPaymentEntityList = new ArrayList<>();
		for (CreditPaymentDto creditPaymentDto : creditPaymentDtoList) {
			creditPaymentEntityList.add(creditPaymentDtoToCreditPaymentEntity(creditPaymentDto, paymentId));
		}
		return creditPaymentEntityList;
	}

	public List<CreditPaymentDto> creditPaymentEntityToCreditPaymentDto(
			List<CreditPaymentEntity> creditPaymentEntityList) {
		List<CreditPaymentDto> creditPaymentDtoList = new ArrayList<>();
		for (CreditPaymentEntity creditPaymentEntity : creditPaymentEntityList) {
			creditPaymentDtoList.add(creditPaymentEntityToCreditPaymentDto(creditPaymentEntity));
		}
		return creditPaymentDtoList;
	}
}
