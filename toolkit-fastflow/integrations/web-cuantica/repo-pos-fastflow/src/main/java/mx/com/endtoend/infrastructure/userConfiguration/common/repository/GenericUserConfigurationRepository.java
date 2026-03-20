package mx.com.endtoend.infrastructure.userConfiguration.common.repository;

import java.util.List;

import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.RoleJobTypeDto;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

public interface GenericUserConfigurationRepository {

	EmployeeDto createUserConfiguration(EmployeeDto employeeDto, String idOperation);

	EmployeeDto updateUserConfiguration(EmployeeDto employeeDto, String idOperation);

	EmployeeDto findUserConfigurationByUserId(Long userId, String idOperation);

	List<PriceTypeDto> findAllPriceTypes(String idOperation);

	List<SaleTypeDto> findAllSaleTypes(String idOperation);

	List<CreditNoteTypeDto> findAllCreditNotes(String idOperation);
	
	List<RoleJobTypeDto> findAllRoleJobTypes(String idOperation);

	boolean existsUserByIdAndUserNumber(Long id, Long userNumber, String idOperation);

	boolean existUseConfigurationByUserIdAndUserNumber(Long userId, Long userNumber, String idOperation);

	EmployeeDto findUserConfigurationByEmailAndBranchCode(String email, String branchCode, String idOperation);

	boolean updateUserConfigurationStatusByUserId(Long userId, String idOperation);

	List<EmployeeDto> findEmployeeByOperativeRoleAndBranchCode(List<String> operativeRole, String branchCode,
			String idOperation);

	List<EmployeeDto> findEmployeeByOperativeRoleList(List<String> operativeRole, String idOperation);
}
