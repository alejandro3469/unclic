package mx.com.endtoend.domain.userConfigurations.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.WarehouseOptionsDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.userConfigurations.services.UserConfigurationGenericValidation;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.SemiFullFunction;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

/**
 * 
 * @author ddcasas
 *
 */

public class UserConfigurationMethodOne implements UserConfigurationInterface {

	UserConfigurationGenericValidation userConfigvalidations = new UserConfigurationGenericValidation();

	private final static Logger LOG = LoggerFactory.getLogger(UserConfigurationMethodOne.class);

	@Override
	public ResponseModel createUserConfiguration(UserPersistencePort userPersistencePort,
			UserConfigurationPersistencePort repository, EmployeeDto employeeDto, String companyCode,
			String idOperation) {

		String validations = userConfigvalidations.getValidationsMethodOne(userPersistencePort, repository, employeeDto,
				companyCode, idOperation);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		EmployeeDto employeeCreated = (EmployeeDto) repository.create(employeeDto, companyCode, idOperation).getData();
		if (employeeCreated == null)
			throw new GlobalError();

		LOG.info("%s UPDATE USER-CONFIGURATION STATUS", idOperation);
		boolean configurationUpdate = (boolean) repository
				.updateUserConfigurationStatusById(employeeCreated.getUserId(), companyCode, idOperation).getData();
		if (!configurationUpdate)
			throw new SemiFullFunction("CONFIGURATION-STATUS-INCOMPLETE", employeeCreated);

		return new ResponseModel(employeeCreated);
	}

	@Override
	public ResponseModel updateUserConfiguration(SecurityLogServicePort securityLogServicePort, String userLogged,
			UserPersistencePort userPersistencePort, UserConfigurationPersistencePort repository,
			EmployeeDto employeeDto, String companyCode, String idOperation) {

		String validations = userConfigvalidations.getValidationsMethodOne(userPersistencePort, repository, employeeDto,
				companyCode, idOperation);
		LOG.info("{} VALIDATION RESULT: %s ", idOperation, validations);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		EmployeeDto employeeSaved = (EmployeeDto) repository
				.findByCompanyCodeAndUserId(employeeDto.getUserId(), companyCode, idOperation).getData();

		EmployeeDto employeeUpdated = (EmployeeDto) repository.update(employeeDto, companyCode, idOperation).getData();
		if (employeeUpdated == null) {
			LOG.error("{} ERROR UPDATING USER-CONFIGURATION", idOperation);
			throw new GlobalError();
		}

		LOG.info("{} GENERATE LOGGING RECORDS", idOperation);
		securityLogServicePort.generateUserConfigurationChangeLog(userLogged, employeeSaved, employeeDto);

		LOG.info("{} UPDATE USER-CONFIGURATION STATUS", idOperation);
		boolean configurationUpdate = (boolean) repository
				.updateUserConfigurationStatusById(employeeUpdated.getUserId(), companyCode, idOperation).getData();

		if (!configurationUpdate) {
			LOG.warn("{} ERROR UPDATED CONFIGURATION STATUS ", idOperation);
			throw new SemiFullFunction("CONFIGURATION-STATUS-INCOMPLETE", employeeUpdated);
		}
		return new ResponseModel(employeeUpdated);
	}

	@Override
	public ResponseModel getUserConfigurationByUserIdAndCompanyCode(UserConfigurationPersistencePort repository,
			Long userid, String companyCode, String idOperation) {
		LOG.info("{} INIT getUserConfigurationByUserIdAndCompanyCode() ", idOperation);
		ResponseModel responseFromPersistencePort = repository.findByCompanyCodeAndUserId(userid, companyCode,
				idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getAllPricesByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation) {
		LOG.info("{} INIT getAllPricesByCompanyCode() ", idOperation);
		ResponseModel responseFromPersistencePort = repository.findAllPricestByCompanyCode(companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getAllSalesByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation) {
		LOG.info("{} INIT getAllSalesByCompanyCode() ", idOperation);
		ResponseModel responseFromPersistencePort = repository.findAllSalesByCompanyCode(companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getAllCreditNoteByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation) {
		LOG.info("{} INIT getAllCreditNoteByCompanyCode() ", idOperation);
		ResponseModel responseFromPersistencePort = repository.findAllCreditNoteByCompanyCode(companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getAllRoleJobByCompanyCode(UserConfigurationPersistencePort repository, String companyCode,
			String idOperation) {
		LOG.info("{} INIT getAllRoleJobByCompanyCode() ", idOperation);
		ResponseModel responseFromPersistencePort = repository.findAllRoleJobTypesByCompanyCode(companyCode,
				idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getUserWarehouseListByEmailAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String email, String idOperation) {

		LOG.info("{} INIT getUserWarehouseListByEmailAndBranch()", idOperation);
		ResponseModel responseFromPersistencePort = repository.findUserConfigurationByEmailAndBranchCode(email,
				companyCode, branchCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseFromPersistencePort.getData();
		List<WarehouseOptionsDto> warehouseOptions = new ArrayList<>();

		if (employeeDto != null) {
			warehouseOptions = employeeDto.getUserConfiguration().getWarehouseOptions();
			warehouseOptions.sort(Comparator.comparing(WarehouseOptionsDto::getWarehouseName));
		}

		return new ResponseModel(warehouseOptions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getUserPriceListByEmailAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String email, String idOperation) {

		LOG.info("{} INIT getUserPriceListByEmailAndBranch()", idOperation);

		ResponseModel responseFromPersistencePort = repository.findUserConfigurationByEmailAndBranchCode(email,
				companyCode, branchCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseFromPersistencePort.getData();
		List<PriceTypeDto> priceTypes = new ArrayList<>();

		if (employeeDto != null) {
			String roleJob = employeeDto.getRoleJob().getCode();

			if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_III.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_II.toString())) {

				priceTypes = employeeDto.getUserConfiguration().getPriceTypes();

			} else if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_I.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.OPERATIONAL.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.MANAGEMENT.toString())) {

				priceTypes = employeeDto.getUserConfiguration().getPriceTypes();
			}

			priceTypes.sort(Comparator.comparing(PriceTypeDto::getId));
		}

		return new ResponseModel(priceTypes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getUserOrderListByEmailAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String email, String idOperation) {

		LOG.info("{} INIT getUserOrderListByEmailAndBranch()", idOperation);

		ResponseModel responseFromPersistencePort = repository.findUserConfigurationByEmailAndBranchCode(email,
				companyCode, branchCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseFromPersistencePort.getData();
		List<SaleTypeDto> saleTypes = new ArrayList<>();

		if (employeeDto != null) {
			String roleJob = employeeDto.getRoleJob().getCode();
			if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_III.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_II.toString())) {

				saleTypes = employeeDto.getUserConfiguration().getSaleTypes();

			} else if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_I.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.OPERATIONAL.toString())
			        || roleJob.equalsIgnoreCase(OperationalLevelEnum.MANAGEMENT.toString())) {

				saleTypes = employeeDto.getUserConfiguration().getSaleTypes();
			}
			saleTypes.sort(Comparator.comparing(SaleTypeDto::getId));
		}

		return new ResponseModel(saleTypes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getUserCreditNoteListByEmailAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String email, String idOperation) {

		LOG.info("{} INIT getUserCreditNoteListByEmailAndBranch()", idOperation);
		ResponseModel responseFromPersistencePort = repository.findUserConfigurationByEmailAndBranchCode(email,
				companyCode, branchCode, idOperation);
		EmployeeDto employeeDto = (EmployeeDto) responseFromPersistencePort.getData();
		List<CreditNoteTypeDto> creditNoteTypes = new ArrayList<>();

		if (employeeDto != null) {
			String roleJob = employeeDto.getRoleJob().getCode();
			if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_III.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_II.toString())) {

				creditNoteTypes = employeeDto.getUserConfiguration().getCreditNoteTypes();

			} else if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_I.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.OPERATIONAL.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.MANAGEMENT.toString())) {

				creditNoteTypes = employeeDto.getUserConfiguration().getCreditNoteTypes();
			}
			creditNoteTypes.sort(Comparator.comparing(CreditNoteTypeDto::getId));
		}

		return new ResponseModel(creditNoteTypes);

	}

	@Override
	public ResponseModel getEmployeeStaffListByOperationalRoleAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String operationalRole, String idOperation) {

		LOG.info("{} INIT getEmployeeStaffListByOperationalRoleAndBranch() ", idOperation);
		ResponseModel responseFromPersistencePort = new ResponseModel();
		List<String> operativeList = getEmployeeStaffOperativeRole(operationalRole);

		if (operativeList == null) {
			LOG.warn("{} ERROR IN OPERATIONAL ROLE INPUT", idOperation);
			throw new ValidationError("INCORRECT OPERATIONAL ROLE");
		}

		if (operationalRole.equals(OperationalLevelEnum.SUPERVISION_III.toString())) {
			LOG.info("{} FIND BY OPERATIONAL ROLE LITS", idOperation);
			responseFromPersistencePort = repository.findEmployeeListByOperativeRol(companyCode, operativeList,
					idOperation);
		} else {
			LOG.info("{} FIND BY OPERATIONAL ROLE LITS AND BRANCH-CODE", idOperation);
			responseFromPersistencePort = repository.findEmployeeListByOperativeRolAndBranchCode(companyCode,
					branchCode, operativeList, idOperation);
		}

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel getEmployeeBossListByOperationalRoleAndBranch(UserConfigurationPersistencePort repository,
			String companyCode, String branchCode, String operationalRole, String idOperation) {

		LOG.info("{} INIT getEmployeeBossListByOperationalRoleAndBranch() ", idOperation);
		LOG.info("{} GET OPERATIONAL ROLE LIST BY SERCH EMPLOYEE LIST", idOperation);
		List<String> operativeList = getEmployeeBossOperativeRole(operationalRole);

		ResponseModel responseFromPersistencePort = new ResponseModel();
		if (operativeList == null) {
			LOG.warn("{} ERROR IN OPERATIONAL ROLE INPUT", idOperation);
			throw new ValidationError("INCORRECT OPERATIONAL ROLE");
		}

		if (operationalRole.equals(OperationalLevelEnum.SUPERVISION_II.toString())) {
			LOG.info("{} FIND BY OPERATIONAL ROLE LITS", idOperation);
			responseFromPersistencePort = repository.findEmployeeListByOperativeRol(companyCode, operativeList,
					idOperation);

		} else {
			LOG.info("{} FIND BY OPERATIONAL ROLE LITS AND BRANCH-CODE", idOperation);

			responseFromPersistencePort = repository.findEmployeeListByOperativeRolAndBranchCode(companyCode,
					branchCode, operativeList, idOperation);
		}

		return responseFromPersistencePort;

	}

	public List<String> getEmployeeStaffOperativeRole(String operativeRole) {
		LOG.info("INIT getEmployeeStaffOperativeRole() ");
		List<String> operativeRoleList = new ArrayList<String>();
		try {
			OperationalLevelEnum value = OperationalLevelEnum.valueOf(operativeRole);
			switch (value) {
			case OPERATIONAL:
				operativeRoleList = Arrays.asList("");
				break;
			case SUPERVISION_I:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.OPERATIONAL.toString());
				break;
			case SUPERVISION_II:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.OPERATIONAL.toString(),
						OperationalLevelEnum.SUPERVISION_I.toString());
				break;
			case SUPERVISION_III:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.SUPERVISION_II.toString());
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR QUOTE-ORDER MODULE");
			return null;
		}
		return operativeRoleList;
	}

	public List<String> getEmployeeBossOperativeRole(String operativeRole) {
		LOG.info("INIT getEmployeeBossOperativeRole() ");
		List<String> operativeRoleList = new ArrayList<String>();
		try {
			OperationalLevelEnum value = OperationalLevelEnum.valueOf(operativeRole);
			switch (value) {
			case OPERATIONAL:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.SUPERVISION_I.toString(),
						OperationalLevelEnum.SUPERVISION_II.toString());
				break;
			case SUPERVISION_I:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.SUPERVISION_II.toString());
				break;
			case SUPERVISION_II:
				operativeRoleList = Arrays.asList(OperationalLevelEnum.SUPERVISION_III.toString());
				break;
			case SUPERVISION_III:
				operativeRoleList = Arrays.asList("");
				break;
			default:
				break;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR QUOTE-ORDER MODULE");
			return null;
		}
		return operativeRoleList;
	}
}