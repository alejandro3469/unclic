package mx.com.endtoend.domain.userConfigurations.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.OperationalLevelEnum;
import mx.com.endtoend.domain.userConfigurations.dto.CreditNoteTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.domain.userConfigurations.dto.WarehouseOptionsDto;
import mx.com.endtoend.domain.userConfigurations.ports.spi.UserConfigurationPersistencePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

/**
 * 
 * @author ddcasas
 *
 */

public class UserConfigurationGenericValidation {

	private static final String OPERATIONAL_ROLE = "OPERATIONAL";
	private final static Logger LOG = LoggerFactory.getLogger(UserConfigurationGenericValidation.class);

	/**
	 * Conjunto de validaciones para la creación de la configuración de los
	 * empleados
	 * 
	 * @param repository
	 * @param employeeDto
	 * @param companyCode
	 * @param method
	 * @param idOperation
	 * @return
	 */

	public String getValidationsMethodOne(UserPersistencePort userPersistencePort,
			UserConfigurationPersistencePort repository, EmployeeDto employeeDto, String companyCode,
			String idOperation) {

		LOG.info("{} START VALIDATION TO METHOD ONE IN USER-CONFIGURATION", idOperation);

		String userExist = userExists(repository, employeeDto.getUserId(), employeeDto.getUserNumber(), companyCode,
				idOperation);
		if (!userExist.isEmpty()) {
			return userExist;
		}

		UserDto user = (UserDto) userPersistencePort.findByUserNumber(employeeDto.getUserNumber(), idOperation)
				.getData();
		if (user == null || user.getRoles().isEmpty() || user.getRoles().get(0).getPermissions().isEmpty()) {
			return "USER NOT FOUND. CONTACT YOUR ADMINISTRATOR";
		}

		String operationalRole = user.getRoles().get(0).getPermissions().get(0).getType();
		String roleJob = employeeDto.getRoleJob().getCode();

		if (OPERATIONAL_ROLE.equalsIgnoreCase(operationalRole)) {
			String validationResult = "";
			String validOperativePermission = validIsOperationalRoleJob(roleJob);

			if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_III.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_II.toString())) {
				validationResult = validOperativePermission
						+ warehouseOptionNotNull(employeeDto.getUserConfiguration().getWarehouseOptions());
			} else if (roleJob.equalsIgnoreCase(OperationalLevelEnum.SUPERVISION_I.toString())
					|| roleJob.equalsIgnoreCase(OperationalLevelEnum.OPERATIONAL.toString())) {
				validationResult = salesTypeNotNull(employeeDto.getUserConfiguration().getSaleTypes())
						+ priceTypeNotNull(employeeDto.getUserConfiguration().getPriceTypes())
						+ warehouseOptionNotNull(employeeDto.getUserConfiguration().getWarehouseOptions())
						+ validOperativePermission;
			}

			return validationResult;
		} else {
			return validIsManagementRoleJobe(roleJob);
		}
	}

	private String validIsManagementRoleJobe(String code) {
		return code.equals(OperationalLevelEnum.MANAGEMENT.toString()) ? ""
				: "INVALID ROLE JOB, USER HAVE MANAGEMENT ACCESS ROLE";
	}

	private String validIsOperationalRoleJob(String code) {
		return code.equals(OperationalLevelEnum.MANAGEMENT.toString())
				? " INVALID ROLE JOB, USER HAVE OPERATIONAL ACCESS ROLE"
				: "";
	}

	/**
	 * Método que valida la existencia de un usuario por su id y su usernumber. Si
	 * el usuario existe retorna una cadena vacía, en caso contrario, retorna el
	 * mensaje USER-NOT-FOUN.
	 * 
	 * @param repository
	 * @param idUser
	 * @param userNumber
	 * @param companyCode
	 * @param method
	 * @param idOperation
	 * @return String
	 */

	public String userExists(UserConfigurationPersistencePort repository, Long idUser, Long userNumber,
			String companyCode, String idOperation) {
		boolean existUser = (boolean) repository
				.existsUserByIdAndUserNumber(idUser, userNumber, companyCode, idOperation).getData();
		return (existUser) ? "" : "USER-NOT-FOUND - ";
	}

	/**
	 * Método que vaida que la lista de ventas no sea nula o vacia, en caso
	 * contrario, devolvera una una cadena con la validación correspondiente.
	 * 
	 * @param saleTypeDtoList
	 * @return String
	 */
	public String salesTypeNotNull(List<SaleTypeDto> saleTypeDtoList) {
		String validation = "";
		if (saleTypeDtoList == null) {
			validation = "SALE-LIST-NULL - ";
		} else {
			if (saleTypeDtoList.isEmpty()) {
				validation = "SALE-LIST-EMPTY - ";
			}
		}
		return validation;
	}

	/**
	 * Método que valida que la lista de notas de crédito no sea nula o vacia
	 * 
	 * @param creditNoteTypes
	 * @return
	 */
	@SuppressWarnings("unused")
	private String creditNoteOptionNotNull(List<CreditNoteTypeDto> creditNoteTypes) {
		String validation = "";
		if (creditNoteTypes == null) {
			return "CREDIT-NOTE-LIST IS NULL";
		} else {
			if (creditNoteTypes.isEmpty()) {
				return "CREDIT-NOTE-LIST IS EMPTY";
			}
		}
		return validation;
	}

	/**
	 * Método que valida que la lista de precios no sea nula o vacia, si es así,
	 * devolvera una cadena con la validación correspondiente.
	 * 
	 * @param priceTypeDtoList
	 * @return String
	 */
	public String priceTypeNotNull(List<PriceTypeDto> priceTypeDtoList) {

		String validation = "";

		if (priceTypeDtoList == null) {
			validation = "PRICE-LIS-NULL - ";
		} else {
			if (priceTypeDtoList.isEmpty()) {
				validation = "PRICE-LIST-EMPTY - ";
			}
		}

		return validation;

	}

	/**
	 * Método que valida que la lista de almacenes no sea nula o vacía, si es así,
	 * devolvera una cadena con la validación correspondiente.
	 * 
	 * @param warehouseOptionsDtoList
	 * @return
	 */
	public String warehouseOptionNotNull(List<WarehouseOptionsDto> warehouseOptionsDtoList) {
		if (warehouseOptionsDtoList == null) {
			return "WAREHOUSE-LIST-NULL - ";
		} else if (warehouseOptionsDtoList.isEmpty()) {
			return "WAREHOUSE-LIST-EMPTY - ";
		} else {
			return "";
		}
	}

}