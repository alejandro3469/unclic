package mx.com.endtoend.application.userConfigurations;

import static mx.com.endtoend.genericCommonsFileds.utilities.ResponseLog.generateIdOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.api.UserConfigurationServicePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la configuración de los datos operacionales de las compañias
 * y sus empleados
 * 
 * @author ddcasas
 *
 */

@RestController
@RequestMapping("/user-config")
public class UserConfigurationController {

	@Autowired
	private UserConfigurationServicePort userConfigurationServicePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private UserPersistencePort userPersistencePort;

	@Autowired
	private SecurityLogServicePort securityLogServicePort;

	private static String module = "USER_CONFIGURATIONS";
	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(UserConfigurationController.class);

	/**
	 * Método para configurar los datos operacionales de los empleados del sistema.
	 * 
	 * @param employeeDto
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */

	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createUserConfiguration(@RequestBody EmployeeDto employeeDto,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT createUserConfiguration()", idOperation));
		LOG.info(String.format("%s PARAMS: [ EmployeeDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				employeeDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new ValidationError("INCOMPLETE-CONFIGURATION");
		}
		ResponseModel responseModel = userConfigurationServicePort.createUserConfiguration(userPersistencePort,
				employeeDto, companyCode, method.getCode(), idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para actualizar los datos operacionales de los empleados
	 * 
	 * @param employeeDto
	 * @param companyCode
	 * @param id
	 * @param branchCode
	 * @return
	 */
	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateUserConfiguration(@RequestBody EmployeeDto employeeDto,
			@PathVariable String companyCode, @PathVariable Long id, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT updateUserConfiguration()", idOperation));
		LOG.info(String.format("%s PARAMS: [ EmployeeDto: %s , companyCode: %s , branchCode: %s ]", idOperation,
				employeeDto.toString(), companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		employeeDto.setId(id);
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel responseModel = userConfigurationServicePort.updateUserConfiguration(securityLogServicePort,
				userLogged, userPersistencePort, employeeDto, companyCode, method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Métdo para ver el deatlle de los datos operacionales configurdados al
	 * empleado por su ID del sistema.
	 * 
	 * @param companyCode
	 * @param userId
	 * @param userNumber
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewUserConfigurationByIdAndCompanyCode(@PathVariable String companyCode,
			@PathVariable Long id, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewUserConfigurationByIdAndCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s , userId: %s , branchCode: %s ]", idOperation, companyCode,
				id.toString(), branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = userConfigurationServicePort.getUserConfigurationByUserId(id, companyCode,
				method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para la obtención de la lsta de precios por código de compañia.
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/prices/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewPriceTypeListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);

		LOG.info(String.format("%s INIT viewPriceTypeListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s, branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort.getPriceTypeList(companyCode, method.getCode(),
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para la obtención de las ventas por código de compañia.
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/sale/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewSaleTypeListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewSaleTypeListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s, branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort.getSaleTypeList(companyCode, method.getCode(),
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para la obtención de las ventas por código de compañia.
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/credit-note/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewCreditNoteListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewCreditNoteListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s, branchCode: %s ]", idOperation, companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort.getCreditNoteTypeList(companyCode, method.getCode(),
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para la obtención de los roles operativos por código de compañía.
	 * 
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/role-job/{companyCode}/{branchCode}")
	public ResponseEntity<?> viewRoleJobTypeListByCompanyCode(@PathVariable String companyCode,
			@PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT viewRoleJobTypeListByCompanyCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [ companyCode: %s, branchCode: %s ]", idOperation, companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort.getRoleJobTypeList(companyCode, method.getCode(),
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método que recupera la lista de almacenes configurados a un empleado por su
	 * email.
	 * 
	 * @param email
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/user-warehouses/{email}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getWarehousesByEmailAndBrancheCode(@PathVariable String email,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getWarehousesByUsernameAndBrancheCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s , companyCode: %s , branchCode: %s]", idOperation, email,
				companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = userConfigurationServicePort.getUserWarehouseList(companyCode, branchCode, email,
				method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para recupear la lista de precios configurados a un usuario por su
	 * email.
	 * 
	 * @param email
	 * @param companyCode
	 * @param branchCode
	 * @return
	 */
	@GetMapping("/user-prices/{email}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getPricesByEmailAndBrancheCode(@PathVariable String email,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getPricesByUsernameAndBrancheCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s , companyCode: %s , branchCode: %s]", idOperation, email,
				companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {

			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = userConfigurationServicePort.getUserPiceList(companyCode, branchCode, email,
				method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para recuperar las ordenes configuradas a un usuario por su email.
	 * 
	 * @param email
	 * @param companyCode
	 * @return
	 */
	@GetMapping("/user-orders/{email}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getOrdersByEmailAndBrancheCode(@PathVariable String email,
			@PathVariable String companyCode, @PathVariable String branchCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getOrdersByUsernameAndBrancheCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s , companyCode: %s , branchCode: %s]", idOperation, email,
				companyCode, branchCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = userConfigurationServicePort.getUserOrderList(companyCode, branchCode, email,
				method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método para recuperar las ordenes configuradas a un usuario por su email.
	 * 
	 * @param email
	 * @param companyCode
	 * @return
	 */
	@GetMapping("/user-credit-note/{email}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getCreditNoteByEmailAndBrancheCode(@PathVariable String email,
			@PathVariable String companyCode, @PathVariable String branchCode) {
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getCreditNoteByEmailAndBrancheCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s , companyCode: %s , branchCode: %s]", idOperation, email,
				companyCode, branchCode));
		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort.getUserCreditNoteList(companyCode, branchCode, email,
				method.getCode(), idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método que recupera la lista de empleados que serán asignados a un empleado
	 * de nivel supervisión por rol operativo y código de sucursal.
	 * 
	 * @param operativeRole
	 * @param branchCode
	 * @param companyCode
	 * @return
	 */
	@GetMapping("/employee/staff/{operativeRole}/{branchCode}/{companyCode}")
	public ResponseEntity<?> getEmployeeStaffListByOperationalRoleAndBranchCodeAndCompanyCode(
			@PathVariable String operativeRole, @PathVariable String branchCode, @PathVariable String companyCode) {

		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getEmployeeStaffListByOperationalRoleAndBranchCodeAndCompanyCode()",
				idOperation));
		LOG.info(String.format("%s PARAMS: [ operativeRole: %s , branchCode: %s , companyCode: %s ]", idOperation,
				operativeRole, branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();

		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}

		ResponseModel responseModel = userConfigurationServicePort
				.getEmployeeStaffLitsByOperativeRoleAndBranchCodeAndCompanyCode(companyCode, branchCode, operativeRole,
						method.getCode(), idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Método que recupera la lista de empleados para ser asignados como supervisor
	 * de un equipo de trabajo por rol operativo y código de sucursal.
	 * 
	 * @param operativeRole
	 * @param branchCode
	 * @param companyCode
	 * @return
	 */
	@GetMapping("/employee/boss/{operativeRole}/{branchCode}/{companyCode}")
	public ResponseEntity<?> getEmployeeBossLitsByRoleTypeAndBranchCodeAndCompanyCode(
			@PathVariable String operativeRole, @PathVariable String branchCode, @PathVariable String companyCode) {
		
		idOperation = generateIdOperation(companyCode, branchCode, module);
		LOG.info(String.format("%s INIT getEmployeeBossLitsByRoleTypeAndBranchCodeAndCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ operativeRole: %s , branchCode: %s , companyCode: %s ]", idOperation,
				operativeRole, branchCode, companyCode));

		MethodDto method = (MethodDto) companyServicePort
				.findMethodByCompanyCodeAndModule(companyCode, module, idOperation).getData();
		if (method == null) {
			LOG.info(String.format("%s ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", idOperation, companyCode));
			throw new GlobalError();
		}
		ResponseModel responseModel = userConfigurationServicePort
				.getEmployeeBossLitsByOperativeRoleAndBranchCodeAndCompanyCode(companyCode, branchCode, operativeRole,
						method.getCode(), idOperation);

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}