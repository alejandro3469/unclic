package mx.com.endtoend.infrastructure.payments.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.payments.common.entities.CheckPaymentEntity;
import mx.com.endtoend.smart.bussiness.model.payments.CheckPaymentDto;

@Component
public class CheckPaymentConverter {

	public CheckPaymentEntity checkPaymentDtoToCheckPaymentEntity(CheckPaymentDto checkPaymentDto, Long paymentId) {

		CheckPaymentEntity checkPaymentEntity = new CheckPaymentEntity();
		
		checkPaymentEntity.setId(checkPaymentDto.getId());
		checkPaymentEntity.setLine(checkPaymentDto.getLine());
		checkPaymentEntity.setPaymentId(paymentId);
		checkPaymentEntity.setCurrency(checkPaymentDto.getCurrency());
		checkPaymentEntity.setExchangeRate(checkPaymentDto.getExchangeRate());
		checkPaymentEntity.setBankingInstitution(checkPaymentDto.getBankingInstitution());
		checkPaymentEntity.setCheckNumber(checkPaymentDto.getCheckNumber());
		checkPaymentEntity.setAmountApplied(checkPaymentDto.getAmountApplied());
		
		return checkPaymentEntity;
	}

	public CheckPaymentDto checkPaymentEntityToCheckPaymentDto(CheckPaymentEntity checkPaymentEntity) {

		CheckPaymentDto checkPaymentDto = new CheckPaymentDto();
		
		checkPaymentDto.setId(checkPaymentEntity.getId());
		checkPaymentDto.setLine(checkPaymentEntity.getLine());
		checkPaymentDto.setPaymentId(checkPaymentEntity.getPaymentId());
		checkPaymentDto.setCurrency(checkPaymentEntity.getCurrency());
		checkPaymentDto.setExchangeRate(checkPaymentEntity.getExchangeRate());
		checkPaymentDto.setBankingInstitution(checkPaymentEntity.getBankingInstitution());
		checkPaymentDto.setCheckNumber(checkPaymentEntity.getCheckNumber());
		checkPaymentDto.setAmountApplied(checkPaymentEntity.getAmountApplied());
		
		return checkPaymentDto;
	}

	public List<CheckPaymentEntity> checkPaymentDtoToCheckPaymentEntity(List<CheckPaymentDto> checkPaymentDtoList,
			Long paymentId) {
		List<CheckPaymentEntity> checkPaymentEntities = new ArrayList<>();
		for (CheckPaymentDto checkPaymentDto : checkPaymentDtoList) {
			checkPaymentEntities.add(checkPaymentDtoToCheckPaymentEntity(checkPaymentDto, paymentId));
		}
		return checkPaymentEntities;
	}

	public List<CheckPaymentDto> checkPaymentEntityToCheckPaymentDto(List<CheckPaymentEntity> checkPaymentEntityList) {
		List<CheckPaymentDto> checkPaymentDtos = new ArrayList<>();
		for (CheckPaymentEntity checkPaymentEntity : checkPaymentEntityList) {
			checkPaymentDtos.add(checkPaymentEntityToCheckPaymentDto(checkPaymentEntity));
		}
		return checkPaymentDtos;
	}
}
