package mx.com.endtoend.infrastructure.cash.bankReference.common.persistence;

import java.util.List;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;

public interface GenericBankConfigurationPersistence {

	BankDto createBank(BankDto bankDto, String idOperation);

	BankDto updateBank(BankDto bankDto, String idOperation);

	BankDto getBankById(Long id, String idOperation);

	List<BankDto> getBankListByEnable(boolean active, String idOperation);

	List<BankDto> getBankListByUseTypeAndEnable(String useType, boolean active, String idOperation);

	BankDto findBankByInstitution(String institutionName, String idOperation);

	BankDto findBankByInstitutionAndIdNot(BankDto bankDto, String idOperation);
}
