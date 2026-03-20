package mx.com.endtoend.infrastructure.accountingRecord.common.converters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.infrastructure.accountingRecord.common.entities.AccountingRecordEntity;

@Component
public class AccountingRecordConverter {

	public AccountingRecordEntity accountingRecordDtoToAccountingRecordEntity(AccountingRecordDto accountingRecordDto) {

		AccountingRecordEntity accountingRecordEntity = new AccountingRecordEntity();

		accountingRecordEntity.setId(accountingRecordDto.getId());
		accountingRecordEntity.setDate(accountingRecordDto.getDate());
		accountingRecordEntity.setEmployeeId(accountingRecordDto.getEmployeeId());
		accountingRecordEntity.setBranchCode(accountingRecordDto.getBranchCode());
		accountingRecordEntity.setOpeningId(accountingRecordDto.getOpeningId());
		accountingRecordEntity.setTransactionId(accountingRecordDto.getTransactionId());
		accountingRecordEntity.setAccountingConcept(accountingRecordDto.getAccountingConcept());
		accountingRecordEntity.setMovementType(accountingRecordDto.getMovementType());
                accountingRecordEntity.setAmountApplied(accountingRecordDto.getAmountApplied());
		accountingRecordEntity.setMovementConcept(accountingRecordDto.getMovementConcept());

		return accountingRecordEntity;
	}

	public AccountingRecordDto accountingRecordEntityToAccountingRecordDto(
			AccountingRecordEntity accountingRecordEntity) {

		AccountingRecordDto accountingRecordDto = new AccountingRecordDto();

		accountingRecordDto.setId(accountingRecordEntity.getId());
		accountingRecordDto.setDate(accountingRecordEntity.getDate());
		accountingRecordDto.setEmployeeId(accountingRecordEntity.getEmployeeId());
		accountingRecordDto.setBranchCode(accountingRecordEntity.getBranchCode());
		accountingRecordDto.setOpeningId(accountingRecordEntity.getOpeningId());
		accountingRecordDto.setTransactionId(accountingRecordEntity.getTransactionId());
		accountingRecordDto.setAccountingConcept(accountingRecordEntity.getAccountingConcept());
		accountingRecordDto.setMovementType(accountingRecordEntity.getMovementType());
                accountingRecordDto.setAmountApplied(accountingRecordEntity.getAmountApplied());
		accountingRecordDto.setMovementConcept(accountingRecordEntity.getMovementConcept());

		return accountingRecordDto;
	}

	public List<AccountingRecordEntity> accountingRecordDtoListToAccountingRecordEntityList(
			List<AccountingRecordDto> accountingRecordDtoList) {
		List<AccountingRecordEntity> accountingRecordEntityList = new ArrayList<>();
		for (AccountingRecordDto accountingRecordDto : accountingRecordDtoList) {
			accountingRecordEntityList.add(accountingRecordDtoToAccountingRecordEntity(accountingRecordDto));
		}
		return accountingRecordEntityList;
	}

	public List<AccountingRecordDto> accountingRecordEntityListToAccountingRecordDtoList(
			List<AccountingRecordEntity> accountingRecordEntityList) {
		List<AccountingRecordDto> accountingRecordDtoList = new ArrayList<>();
		for (AccountingRecordEntity accountingRecordEntity : accountingRecordEntityList) {
			accountingRecordDtoList.add(accountingRecordEntityToAccountingRecordDto(accountingRecordEntity));
		}
		return accountingRecordDtoList;
	}
}
