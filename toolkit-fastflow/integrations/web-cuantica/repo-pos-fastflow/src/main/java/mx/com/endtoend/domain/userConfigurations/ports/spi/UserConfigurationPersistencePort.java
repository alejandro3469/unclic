package mx.com.endtoend.domain.userConfigurations.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface UserConfigurationPersistencePort {

	ResponseModel create(EmployeeDto employeeDto, String companyCode, String idOperation);

	ResponseModel update(EmployeeDto employeeDto, String companyCode, String idOperation);

	ResponseModel updateUserConfigurationStatusById(Long userId, String companyCode, String idOperation);

	ResponseModel findByCompanyCodeAndUserId(Long userId, String companyCode, String idOperation);

	ResponseModel findAllPricestByCompanyCode(String companyCode, String idOperation);

	ResponseModel findAllSalesByCompanyCode(String companyCode, String idOperation);
	
	ResponseModel findAllCreditNoteByCompanyCode(String companyCode, String idOperation);

	ResponseModel findAllRoleJobTypesByCompanyCode(String companyCode, String idOperation);

	ResponseModel existsUserByIdAndUserNumber(Long id, Long userNumber, String companyCode, String idOperation);

	ResponseModel existUserConfigurationByUserIdAndUserNumber(Long userId, Long userNumber, String companyCode,
			String idOperation);

	ResponseModel findUserConfigurationByEmailAndBranchCode(String email, String companyCode, String branchCode,
			String idOperation);

	ResponseModel findEmployeeListByOperativeRolAndBranchCode(String companyCode, String branchCode,
			List<String> operationalRole, String idOperation);

	ResponseModel findEmployeeListByOperativeRol(String companyCode, List<String> operationalRole, String idOperation);

}
