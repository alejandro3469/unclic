package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.PaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.PaymentDto;

@Component
public class PaymentConverter {

	public PaymentEntity paymentDtoToPaymentEntity(PaymentDto paymentDto) {

		PaymentEntity paymentEntitye = new PaymentEntity();

		paymentEntitye.setPaymentId(paymentDto.getPaymentId());
		paymentEntitye.setOpeningCashId(paymentDto.getOpeningCashId());
		paymentEntitye.setPaymentDate(paymentDto.getPaymentDate());
		paymentEntitye.setClientNumber(paymentDto.getClientNumber());
		paymentEntitye.setEmployeeEmail(paymentDto.getEmployeEmail());
		paymentEntitye.setUserId(paymentDto.getUserId());
		paymentEntitye.setUserNumber(paymentDto.getUserNumber());
		paymentEntitye.setPaymentState(paymentDto.getPaymentState());
		paymentEntitye.setOrderNumber(paymentDto.getOrderNumber());
		paymentEntitye.setOrderCode(paymentDto.getOrderCode());
		paymentEntitye.setOrderTotal(paymentDto.getOrderTotal());
		paymentEntitye.setPendingPayment(paymentDto.getPendingPayment());
		paymentEntitye.setPrinted(paymentDto.getIsPrinted());

		return paymentEntitye;
	}

	public PaymentDto paymentEntityToPaymentDto(PaymentEntity paymentEntity) {

		PaymentDto paymentDto = new PaymentDto();

		paymentDto.setPaymentId(paymentEntity.getPaymentId());
		paymentDto.setOpeningCashId(paymentEntity.getOpeningCashId());
		paymentDto.setPaymentDate(paymentEntity.getPaymentDate());
		paymentDto.setClientNumber(paymentEntity.getClientNumber());
		paymentDto.setEmployeEmail(paymentEntity.getEmployeeEmail());
		paymentDto.setUserId(paymentEntity.getUserId());
		paymentDto.setUserNumber(paymentEntity.getUserNumber());
		paymentDto.setPaymentState(paymentEntity.getPaymentState());
		paymentDto.setOrderNumber(paymentEntity.getOrderNumber());
		paymentDto.setOrderCode(paymentEntity.getOrderCode());
		paymentDto.setOrderTotal(paymentEntity.getOrderTotal());
		paymentDto.setPendingPayment(paymentEntity.getPendingPayment());
		paymentDto.setIsPrinted(paymentEntity.isPrinted());

		return paymentDto;
	}

	public List<PaymentDto> paymentEntityListToPaymentDtoList(List<PaymentEntity> paymentEntityList) {
		List<PaymentDto> paymentDtoList = new ArrayList<>();
		for (PaymentEntity paymentEntity : paymentEntityList) {
			paymentDtoList.add(paymentEntityToPaymentDto(paymentEntity));
		}
		return paymentDtoList;
	}

	public List<PaymentEntity> paymentDtoListToPaymentEntityList(List<PaymentDto> paymentDtoList) {
		List<PaymentEntity> paymentEntityList = new ArrayList<>();
		for (PaymentDto paymentDto : paymentDtoList) {
			paymentEntityList.add(paymentDtoToPaymentEntity(paymentDto));
		}
		return paymentEntityList;
	}

}
