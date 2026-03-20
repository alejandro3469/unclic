package mx.com.endtoend.infrastructure.userConfiguration.common.adapter;

import java.util.List;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.userConfiguration.common.factory.UserConfigurationFactory;
import mx.com.endtoend.infrastructure.userConfiguration.common.repository.GenericUserConfigurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.RoleJobTypeDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase que obtiene la implementación de la clase concreta de la interfaz
 * GenericUserConfigurationRepository para cada una de las compañias
 * regisytadas.
 * 
 * @author ddcasas
 *
 */

public class UserConfigurationJpaAdapter implements UserConfigurationPersistencePort {

	@Autowired
	private UserConfigurationFactory userConfifurationFactory;

	private final Logger LOG = LoggerFactory.getLogger(UserConfigurationJpaAdapter.class);

	/**
	 * Método que obtiene el repositorio de un cliente por código de compañia para
	 * la creación de configuración de datos operacionales de empleados.
	 * 
	 * @param employeeDto
	 * @param companyCode
	 * @param method
	 * @param idOperation
	 * 
	 * @return ResponseModel
	 * 
	 */
	@Override
	public ResponseModel create(EmployeeDto employeeDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT create() ", idOperation));
		LOG.info(String.format("%s PARAMS [ employeeDto: %s , companyCode: %s ] ", idOperation, employeeDto.toString(),
				companyCode));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		EmployeeDto employeeDtoCreated = repository.createUserConfiguration(employeeDto, idOperation);
		return new ResponseModel(employeeDtoCreated);
	}

	@Override
	public ResponseModel update(EmployeeDto employeeDto, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT update() ", idOperation));
		LOG.info(String.format("%s PARAMS [ employeeDto: %s , companyCode: %s ] ", idOperation, employeeDto.toString(),
				companyCode));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		EmployeeDto employeeDtoUpdated = repository.updateUserConfiguration(employeeDto, idOperation);
		return new ResponseModel(employeeDtoUpdated);
	}

	@Transactional
	@Override
	public ResponseModel findByCompanyCodeAndUserId(Long userId, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findByCompanyCodeAndUserIdAndUserNumber() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ userId: %s , companyCode: %s ]", idOperation, userId.toString(),
				companyCode));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		EmployeeDto employeeDto = repository.findUserConfigurationByUserId(userId, idOperation);
		return new ResponseModel(employeeDto);
	}

	@Override
	public ResponseModel findAllPricestByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findAllPricestByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		List<PriceTypeDto> priceTypeDtoList = repository.findAllPriceTypes(idOperation);
		return new ResponseModel(priceTypeDtoList);
	}

	@Override
	public ResponseModel findAllSalesByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findAllSalesByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		List<SaleTypeDto> saleTypeDtoList = repository.findAllSaleTypes(idOperation);
		return new ResponseModel(saleTypeDtoList);
	}
	
	@Override
	public ResponseModel findAllCreditNoteByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findAllCreditNoteByCompanyCode() ", idOperation));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		List<CreditNoteTypeDto> saleTypeDtoList = repository.findAllCreditNotes(idOperation);
		return new ResponseModel(saleTypeDtoList);
	}

	@Override
	public ResponseModel findAllRoleJobTypesByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findAllRoleJobTypesByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s ]", idOperation, companyCode));

		if(Objects.equals(companyCode, "ETE")) {
			List<RoleJobTypeDto> roleJobTypeDtoList = new ArrayList<>();
			RoleJobTypeDto roleJobTypeDto = new RoleJobTypeDto();
			roleJobTypeDto.setId(10L);
			roleJobTypeDto.setCode("MANAGEMENT");
			roleJobTypeDto.setName("ADMINISTRADOR");

			roleJobTypeDtoList.add(roleJobTypeDto);
			return new ResponseModel(roleJobTypeDtoList);
		}

		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}
		List<RoleJobTypeDto> roleJobTypeDtoList = repository.findAllRoleJobTypes(idOperation);
		return new ResponseModel(roleJobTypeDtoList);
	}

	@Override
	public ResponseModel existsUserByIdAndUserNumber(Long id, Long userNumber, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT existsUserByIdAndUserNumber() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ id: %s , userNumber: %s , companyCode: %s ]", idOperation, id.toString(),
				userNumber.toString(), companyCode));

		LOG.info(String.format("%s GET CLIENT REPOSITORY ", idOperation));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		boolean result = repository.existsUserByIdAndUserNumber(id, userNumber, idOperation);

		return new ResponseModel(result);
	}

	@Override
	public ResponseModel existUserConfigurationByUserIdAndUserNumber(Long userId, Long userNumber, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT existUserConfigurationByUserIdAndUserNumber() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ userId: %s , userNumber: %s , companyCode: %s ]", idOperation,
				userId.toString(), userNumber.toString(), companyCode));

		LOG.info(String.format("%s GET CLIENT REPOSITORY", idOperation));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		boolean result = repository.existUseConfigurationByUserIdAndUserNumber(userId, userNumber, idOperation);

		return new ResponseModel(result);
	}

	@Override
	public ResponseModel findUserConfigurationByEmailAndBranchCode(String email, String companyCode, String branchCode,
			String idOperation) {

		LOG.info(String.format("%s INIT findUserConfigurationByEmailAndBranchCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ email: %s , companyCode: %s , branchCode: %s ]", idOperation, email,
				companyCode, branchCode));

		LOG.info(String.format("%s GET CLIENT REPOSITORY", idOperation));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		EmployeeDto employeeDto = repository.findUserConfigurationByEmailAndBranchCode(email, branchCode, idOperation);

		return new ResponseModel(employeeDto);

	}

	@Override
	public ResponseModel updateUserConfigurationStatusById(Long userId, String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT updateUserConfigurationStatusById() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ userId: %s , companyCode: %s ]", idOperation, userId.toString(),
				companyCode));

		LOG.info(String.format("%s GET CLIENT REPOSITORY  ", idOperation));

		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		boolean userConfigurationUpdated = repository.updateUserConfigurationStatusByUserId(userId, idOperation);

		return new ResponseModel(userConfigurationUpdated);

	}

	@Override
	public ResponseModel findEmployeeListByOperativeRolAndBranchCode(String companyCode, String branchCode,
			List<String> operationalRole, String idOperation) {

		LOG.info(String.format("%s INIT findEmployeeListByOperativeRolAndBranchCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , branchCode: %s , operationalRole: %s ]", idOperation,
				companyCode, branchCode, operationalRole.size()));

		LOG.info(String.format("%s FIND USER-CONFIGURATION USING  -- USR_CONF_ONE -- ", idOperation));
		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		List<EmployeeDto> employeeDtoList = repository.findEmployeeByOperativeRoleAndBranchCode(operationalRole,
				branchCode, idOperation);

		return new ResponseModel(employeeDtoList);

	}

	@Override
	public ResponseModel findEmployeeListByOperativeRol(String companyCode, List<String> operationalRole,
			String idOperation) {

		LOG.info(String.format("%s INIT findEmployeeListByOperativeRol() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , operationalRole: %s ]", idOperation, companyCode,
				operationalRole.size()));

		LOG.info(String.format("%s FIND USER-CONFIGURATION USING  -- USR_CONF_ONE -- ", idOperation));

		GenericUserConfigurationRepository repository = userConfifurationFactory.getClientRepository(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError();
		}

		List<EmployeeDto> employeeDtoList = repository.findEmployeeByOperativeRoleList(operationalRole, idOperation);

		return new ResponseModel(employeeDtoList);

	}

}