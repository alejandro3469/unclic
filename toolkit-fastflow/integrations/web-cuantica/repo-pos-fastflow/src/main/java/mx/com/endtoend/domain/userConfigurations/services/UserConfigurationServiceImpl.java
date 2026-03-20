package mx.com.endtoend.domain.userConfigurations.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.factory.UserConfigurationFactory;
import mx.com.endtoend.domain.userConfigurations.factory.UserConfigurationInterface;
import mx.com.endtoend.domain.userConfigurations.ports.api.UserConfigurationServicePort;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * La capa se encarga de obtener la implementación concreta de la lógica de
 * negocio configurada a la compañia
 * 
 * @author ddcasas
 *
 */

public class UserConfigurationServiceImpl implements UserConfigurationServicePort {

	private UserConfigurationPersistencePort userConfigurationPersistencePort;

	public UserConfigurationServiceImpl(UserConfigurationPersistencePort userConfigurationPersistencePort) {
		this.userConfigurationPersistencePort = userConfigurationPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(UserConfigurationServiceImpl.class);

	UserConfigurationFactory factory = new UserConfigurationFactory();

	@Override
	public ResponseModel createUserConfiguration(UserPersistencePort userPersistencePort, EmployeeDto employeeDto,
			String companyCode, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.createUserConfiguration(userPersistencePort,
				userConfigurationPersistencePort, employeeDto, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateUserConfiguration(SecurityLogServicePort securityLogServicePort, String userLogged,
			UserPersistencePort userPersistencePort, EmployeeDto employeeDto, String companyCode, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.updateUserConfiguration(securityLogServicePort,
				userLogged, userPersistencePort, userConfigurationPersistencePort, employeeDto, companyCode,
				idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getUserConfigurationByUserId(Long userId, String companyCode, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getUserConfigurationByUserIdAndCompanyCode(
				userConfigurationPersistencePort, userId, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getSaleTypeList(String companyCode, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig
				.getAllSalesByCompanyCode(userConfigurationPersistencePort, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getCreditNoteTypeList(String companyCode, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig
				.getAllCreditNoteByCompanyCode(userConfigurationPersistencePort, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getPriceTypeList(String companyCode, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig
				.getAllPricesByCompanyCode(userConfigurationPersistencePort, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getRoleJobTypeList(String companyCode, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig
				.getAllRoleJobByCompanyCode(userConfigurationPersistencePort, companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getUserWarehouseList(String companyCode, String branchCode, String email, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getUserWarehouseListByEmailAndBranch(
				userConfigurationPersistencePort, companyCode, branchCode, email, idOperation);
		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getUserPiceList(String companyCode, String branchCode, String email, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getUserPriceListByEmailAndBranch(
				userConfigurationPersistencePort, companyCode, branchCode, email, idOperation);
		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getUserOrderList(String companyCode, String branchCode, String email, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getUserOrderListByEmailAndBranch(
				userConfigurationPersistencePort, companyCode, branchCode, email, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getUserCreditNoteList(String companyCode, String branchCode, String email, String method,
			String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getUserCreditNoteListByEmailAndBranch(
				userConfigurationPersistencePort, companyCode, branchCode, email, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getEmployeeStaffLitsByOperativeRoleAndBranchCodeAndCompanyCode(String companyCode,
			String brancCode, String operationalRole, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getEmployeeStaffListByOperationalRoleAndBranch(
				userConfigurationPersistencePort, companyCode, brancCode, operationalRole, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getEmployeeBossLitsByOperativeRoleAndBranchCodeAndCompanyCode(String companyCode,
			String brancCode, String operationalRole, String method, String idOperation) {
		UserConfigurationInterface userConfig = factory.getImplementation(method);
		if (userConfig == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseFromPersistencePort = userConfig.getEmployeeBossListByOperationalRoleAndBranch(
				userConfigurationPersistencePort, companyCode, brancCode, operationalRole, idOperation);
		return responseFromPersistencePort;
	}

}