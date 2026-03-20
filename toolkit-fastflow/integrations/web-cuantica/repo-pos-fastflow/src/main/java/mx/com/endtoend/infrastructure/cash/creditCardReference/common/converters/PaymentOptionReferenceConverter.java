package mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.PaymentOptionEntity;

@Component
public class PaymentOptionReferenceConverter {

	public PaymentOptionEntity paymentOptionDtoToPaymentOptionEntity(PaymentOptionDto paymentOptionDto) {

		PaymentOptionEntity paymentOptionEntity = new PaymentOptionEntity();

		paymentOptionEntity.setId(paymentOptionDto.getId());
		paymentOptionEntity.setPeriod(paymentOptionDto.getPeriod());
		paymentOptionEntity.setCommission(paymentOptionDto.getCommission());
		paymentOptionEntity.setEnable(paymentOptionDto.getIsEnable());

		return paymentOptionEntity;
	}

	public PaymentOptionDto paymentOptionEntityToPaymentOptionDto(PaymentOptionEntity paymentOptionEntity) {

		PaymentOptionDto paymentOptionDto = new PaymentOptionDto();

		paymentOptionDto.setId(paymentOptionEntity.getId());
		paymentOptionDto.setPeriod(paymentOptionEntity.getPeriod());
		paymentOptionDto.setCommission(paymentOptionEntity.getCommission());
		paymentOptionDto.setIsEnable(paymentOptionEntity.isEnable());

		return paymentOptionDto;
	}

	public List<PaymentOptionEntity> paymentOptionDtoListToPaymentOptionEntityList(
			List<PaymentOptionDto> paymentOptionDtoList) {
		List<PaymentOptionEntity> paymentOptionEntities = new ArrayList<>();
		for (PaymentOptionDto paymentOptionDto : paymentOptionDtoList) {
			paymentOptionEntities.add(paymentOptionDtoToPaymentOptionEntity(paymentOptionDto));
		}
		return paymentOptionEntities;
	}

	public List<PaymentOptionDto> paymentOptionEntityListToPaymentOptionDtoList(
			List<PaymentOptionEntity> paymentOptionEntityList) {
		List<PaymentOptionDto> paymentOptionDtos = new ArrayList<>();
		for (PaymentOptionEntity paymentOptionEntity : paymentOptionEntityList) {
			paymentOptionDtos.add(paymentOptionEntityToPaymentOptionDto(paymentOptionEntity));
		}
		return paymentOptionDtos;
	}
}
