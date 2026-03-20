package mx.com.endtoend.infrastructure.cash.bankReference.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.infrastructure.cash.bankReference.common.entities.BankEntity;

@Component
public class BankConverter {

	public BankEntity bankDtoToBankEntity(BankDto bankDto) {

		BankEntity bankEntity = new BankEntity();

		bankEntity.setId(bankDto.getId());
		bankEntity.setCode(bankDto.getCode());
		bankEntity.setBankingInstitution(bankDto.getBankingInstitution());
		bankEntity.setUseType(bankDto.getUseType());
		bankEntity.setEnable(bankDto.getIsEnable());

		return bankEntity;
	}

	public BankDto bankEntityToBankDto(BankEntity bankEntity) {

		BankDto bankDto = new BankDto();

		bankDto.setId(bankEntity.getId());
		bankDto.setCode(bankEntity.getCode());
		bankDto.setBankingInstitution(bankEntity.getBankingInstitution());
		bankDto.setUseType(bankEntity.getUseType());
		bankDto.setIsEnable(bankEntity.isEnable());

		return bankDto;
	}

	public List<BankEntity> bankDtoListToBankEntityList(List<BankDto> bankDtoList) {
		List<BankEntity> bankEntities = new ArrayList<>();
		for (BankDto bankDto : bankDtoList) {
			bankEntities.add(bankDtoToBankEntity(bankDto));
		}
		return bankEntities;
	}

	public List<BankDto> bankEntityListToBankDtoList(List<BankEntity> bankEntityList) {
		List<BankDto> bankDtos = new ArrayList<>();
		for (BankEntity bankEntity : bankEntityList) {
			bankDtos.add(bankEntityToBankDto(bankEntity));
		}
		return bankDtos;
	}
}
