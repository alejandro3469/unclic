package mx.com.endtoend.domain.userConfigurations.factory;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface UserConfigurationInterface {

	ResponseModel createUserConfiguration(UserPersistencePort userPersistencePort,
			UserConfigurationPersistencePort repository, EmployeeDto employeeDto, String companyCode,
			String idOperation);

	ResponseModel updateUserConfiguration(SecurityLogServicePort securityLogServicePort, String userLogged,
			UserPersistencePort userPersistencePort, UserConfigurationPersistencePort repository,
			EmployeeDto employeeDto, String companyCode, String idOperation);

	ResponseModel getUserConfigurationByUserIdAndCompanyCode(UserConfigurationPersistencePort repository, Long userid,
			String companyCode, String idOperation);

	ResponseModel getAllPricesByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation);

	ResponseModel getAllSalesByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation);

	ResponseModel getAllCreditNoteByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation);

	ResponseModel getAllRoleJobByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation);

	ResponseModel getUserWarehouseListByEmailAndBranch(UserConfigurationPersistencePort repository, String companyCode,
			String branchCode, String email, String idOperation);

	ResponseModel getUserPriceListByEmailAndBranch(UserConfigurationPersistencePort repository, String companyCode,
			String branchCode, String email, String idOperation);

	ResponseModel getUserOrderListByEmailAndBranch(UserConfigurationPersistencePort repository, String companyCode,
			String branchCode, String email, String idOperation);

	ResponseModel getUserCreditNoteListByEmailAndBranch(UserConfigurationPersistencePort repository, String companyCode,
			String branchCode, String email, String idOperation);

	ResponseModel getEmployeeStaffListByOperationalRoleAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String operationalRole, String idOperation);

	ResponseModel getEmployeeBossListByOperationalRoleAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String operationalRole, String idOperation);
}