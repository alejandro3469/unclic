package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.PaymentCashEntity;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentCashDto;

@Component
public class PaymentCashConverter {

	public PaymentCashEntity paymentCashDtoToPaymentCashEntity(PaymentCashDto paymentCashDto, Long paymentId) {

		PaymentCashEntity paymentCashEntity = new PaymentCashEntity();

		paymentCashEntity.setId(paymentCashDto.getId());
		paymentCashEntity.setPaymentId(paymentId);
		paymentCashEntity.setLine(paymentCashDto.getLine());
		paymentCashEntity.setCurrency(paymentCashDto.getCurrency());
		paymentCashEntity.setExchangeRate(paymentCashDto.getExchangeRate());
		paymentCashEntity.setAmountApplied(paymentCashDto.getAmountApplied());
		paymentCashEntity.setAmountReceived(paymentCashDto.getAmountReceived());

		return paymentCashEntity;
	}

	public PaymentCashDto paymentCashEntityToPaymentCashDto(PaymentCashEntity paymentCashEntity) {

		PaymentCashDto paymentCashDto = new PaymentCashDto();

		paymentCashDto.setId(paymentCashEntity.getId());
		paymentCashDto.setPaymentId(paymentCashEntity.getPaymentId());
		paymentCashDto.setLine(paymentCashEntity.getLine());
		paymentCashDto.setCurrency(paymentCashEntity.getCurrency());
		paymentCashDto.setExchangeRate(paymentCashEntity.getExchangeRate());
		paymentCashDto.setAmountApplied(paymentCashEntity.getAmountApplied());
		paymentCashDto.setAmountReceived(paymentCashEntity.getAmountReceived());

		return paymentCashDto;
	}

	public List<PaymentCashDto> paymentCashEntityListToPaymentCashDtoList(
			List<PaymentCashEntity> paymentCashEntityList) {
		List<PaymentCashDto> paymentCashDtoList = new ArrayList<>();
		for (PaymentCashEntity paymentCashEntity : paymentCashEntityList) {
			paymentCashDtoList.add(paymentCashEntityToPaymentCashDto(paymentCashEntity));
		}
		return paymentCashDtoList;
	}

	public List<PaymentCashEntity> paymentCashDtoListToPaymentCashEntityList(List<PaymentCashDto> paymentCashDtoList,
			Long paymentId) {
		List<PaymentCashEntity> paymentCashEntityList = new ArrayList<>();
		for (PaymentCashDto paymentCashDto : paymentCashDtoList) {
			paymentCashEntityList.add(paymentCashDtoToPaymentCashEntity(paymentCashDto, paymentId));
		}
		return paymentCashEntityList;
	}

}
