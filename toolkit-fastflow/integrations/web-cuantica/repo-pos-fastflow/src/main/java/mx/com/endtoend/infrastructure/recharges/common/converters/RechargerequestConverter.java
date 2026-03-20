package mx.com.endtoend.infrastructure.recharges.common.converters;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.infrastructure.recharges.calzada.entities.RechargeRequestEntity;

@Component
public class RechargerequestConverter {

	public RechargeRequestEntity rechargeRequestDtoToRechargeRequestEntity(RechargeRequestDto rechargeRequestDto) {

		RechargeRequestEntity rechargeRequestEntity = new RechargeRequestEntity();

		rechargeRequestEntity.setId(null);
		rechargeRequestEntity.setOrderNumber(rechargeRequestDto.getOrderNumber());
		rechargeRequestEntity.setOrderCode(rechargeRequestDto.getOrderCode());
		rechargeRequestEntity.setPhoneNumber(rechargeRequestDto.getPhoneNumber());
		rechargeRequestEntity.setCompanyPhone(rechargeRequestDto.getCompanyPhone());
		rechargeRequestEntity.setAmount(rechargeRequestDto.getAmount().doubleValue());
		rechargeRequestEntity.setAuthCode(rechargeRequestDto.getAuthCode());
		rechargeRequestEntity.setReference(rechargeRequestDto.getReference());

		return rechargeRequestEntity;
	}

	public RechargeRequestDto rechargeRequestEntityToRechargeRequestDto(RechargeRequestEntity rechargeRequestEntity) {

		RechargeRequestDto rechargeRequestDto = new RechargeRequestDto();

		rechargeRequestDto.setOrderNumber(rechargeRequestEntity.getOrderNumber());
		rechargeRequestDto.setOrderCode(rechargeRequestEntity.getOrderCode());
		rechargeRequestDto.setPhoneNumber(rechargeRequestEntity.getPhoneNumber());
		rechargeRequestDto.setCompanyPhone(rechargeRequestEntity.getCompanyPhone());
		rechargeRequestDto.setAmount(BigDecimal.valueOf(rechargeRequestEntity.getAmount()));
		rechargeRequestDto.setAuthCode(rechargeRequestEntity.getAuthCode());
		rechargeRequestDto.setReference(rechargeRequestEntity.getReference());

		return rechargeRequestDto;
	}
}
