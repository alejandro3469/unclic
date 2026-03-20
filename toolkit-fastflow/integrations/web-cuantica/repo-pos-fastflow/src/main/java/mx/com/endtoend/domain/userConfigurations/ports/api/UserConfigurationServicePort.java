package mx.com.endtoend.domain.userConfigurations.ports.api;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface UserConfigurationServicePort {

	ResponseModel createUserConfiguration(UserPersistencePort userPersistencePort, EmployeeDto employeeDto,
			String companyCode, String method, String idOperation);

	ResponseModel updateUserConfiguration(SecurityLogServicePort securityLogServicePort, String userLogged,
			UserPersistencePort userPersistencePort, EmployeeDto employeeDto, String companyCode, String method,
			String idOperation);

	ResponseModel getUserConfigurationByUserId(Long userId, String companyCode, String method, String idOperation);

	ResponseModel getSaleTypeList(String companyCode, String method, String idOperation);

	ResponseModel getCreditNoteTypeList(String companyCode, String method, String idOperation);

	ResponseModel getPriceTypeList(String companyCode, String method, String idOperation);

	ResponseModel getRoleJobTypeList(String companyCode, String method, String idOperation);

	ResponseModel getUserWarehouseList(String companyCode, String branchCode, String email, String method,
			String idOperation);

	ResponseModel getUserPiceList(String companyCode, String branchCode, String email, String method,
			String idOperation);

	ResponseModel getUserOrderList(String companyCode, String branchCode, String email, String method,
			String idOperation);

	ResponseModel getUserCreditNoteList(String companyCode, String branchCode, String email, String method,
			String idOperation);

	ResponseModel getEmployeeStaffLitsByOperativeRoleAndBranchCodeAndCompanyCode(String companyCode, String brancCode,
			String operationalRole, String method, String idOperation);

	ResponseModel getEmployeeBossLitsByOperativeRoleAndBranchCodeAndCompanyCode(String companyCode, String brancCode,
			String operationalRole, String method, String idOperation);
}
