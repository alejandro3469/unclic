package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.TransferPaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.TransferPaymentDto;

@Component
public class TransferPaymentConverter {

	public TransferPaymentEntity transferPaymentDtoToTransferPaymentEntity(TransferPaymentDto transferPaymentDto,
			Long paymentId) {

		TransferPaymentEntity transferPaymentEntity = new TransferPaymentEntity();

		transferPaymentEntity.setId(transferPaymentDto.getId());
		transferPaymentEntity.setPaymentId(paymentId);
		transferPaymentEntity.setCurrency(transferPaymentDto.getCurrency());
		transferPaymentEntity.setExchangeRate(transferPaymentDto.getExchangeRate());
		transferPaymentEntity.setBankingInstitution(transferPaymentDto.getBankingInstitution());
		transferPaymentEntity.setReferenceNumber(transferPaymentDto.getReferenceNumber());
		transferPaymentEntity.setTrackingNumber(transferPaymentDto.getTrackingNumber());
		transferPaymentEntity.setLine(transferPaymentDto.getLine());
		transferPaymentEntity.setAmountApplied(transferPaymentDto.getAmountApplied());

		return transferPaymentEntity;
	}

	public TransferPaymentDto transferPaymentEntityToTransferPaymentDto(TransferPaymentEntity transferPaymentEntity) {

		TransferPaymentDto transferPaymentDto = new TransferPaymentDto();

		transferPaymentDto.setId(transferPaymentEntity.getId());
		transferPaymentDto.setPaymentId(transferPaymentEntity.getPaymentId());
		transferPaymentDto.setCurrency(transferPaymentEntity.getCurrency());
		transferPaymentDto.setExchangeRate(transferPaymentEntity.getExchangeRate());
		transferPaymentDto.setBankingInstitution(transferPaymentEntity.getBankingInstitution());
		transferPaymentDto.setReferenceNumber(transferPaymentEntity.getReferenceNumber());
		transferPaymentDto.setTrackingNumber(transferPaymentEntity.getTrackingNumber());
		transferPaymentDto.setLine(transferPaymentEntity.getLine());
		transferPaymentDto.setAmountApplied(transferPaymentEntity.getAmountApplied());

		return transferPaymentDto;
	}

	public List<TransferPaymentEntity> transferPaymentDtoListToTransferPaymentEntityList(
			List<TransferPaymentDto> transferPaymentDtoList, Long paymentId) {
		List<TransferPaymentEntity> transferPaymentEntities = new ArrayList<>();
		for (TransferPaymentDto transferPaymentDto : transferPaymentDtoList) {
			transferPaymentEntities.add(transferPaymentDtoToTransferPaymentEntity(transferPaymentDto, paymentId));
		}
		return transferPaymentEntities;
	}

	public List<TransferPaymentDto> transferPaymentEntityListToTransferPaymentDtoList(
			List<TransferPaymentEntity> transferPaymentEntityList) {
		List<TransferPaymentDto> transferPaymentDtos = new ArrayList<>();
		for (TransferPaymentEntity transferPaymentEntity : transferPaymentEntityList) {
			transferPaymentDtos.add(transferPaymentEntityToTransferPaymentDto(transferPaymentEntity));
		}
		return transferPaymentDtos;
	}
}
