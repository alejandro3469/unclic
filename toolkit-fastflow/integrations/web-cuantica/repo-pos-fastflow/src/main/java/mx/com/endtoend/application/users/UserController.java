package mx.com.endtoend.application.users;

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

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.api.UserServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la administración de los usuaios del sistema por parte de
 * los administradores de cada compañía
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServicePort userServicePort;

	@Autowired
	private SecurityLogServicePort securityLogServicePort;

	private String module = "USER";

	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(UserController.class);

	/**
	 * EndPoint para la creación de usuarios en el sistema por los usuarios
	 * administradores de cada compañia.
	 * 
	 * @param userDto     módelo de datos para la creación de usuarios
	 * @param companyCode código de compañia
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PostMapping("/create/{companyCode}/{branchCode}")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto, @PathVariable String companyCode) {

		idOperation = generateIdOperation(companyCode, module);

		LOG.info(String.format("%s INIT createUser()", idOperation));
		LOG.info(String.format("%s PARAMS: [userDto: %s , companyCode: %s ]", idOperation, userDto.toString(),
				companyCode));

		ResponseModel responseModel = userServicePort.createUser(userDto, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la actualización de usuarios del sistema por los
	 * administradores de cada compañia
	 * 
	 * @param userDto módelo de datos para la creación de usuarios
	 * @param id      identificador de usuario
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PutMapping("/update/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> updateById(@RequestBody UserDto userDto, @PathVariable Long id) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT updateById()", idOperation));
		LOG.info(String.format("%s PARAMS: [userDto: %s , id: %s ]", idOperation, userDto.toString(), id.toString()));
		userDto.setId(id);
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel responseModel = userServicePort.updateUser(securityLogServicePort, userLogged, userDto,
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para habilitar/deshabilitar el estado de un usuario por id
	 * 
	 * @param id     identificador de usuario
	 * @param status indicador para habilitar o deshabilitar el estado del usuario
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@PutMapping("/enabled/{id}/{status:enable|disable}/{companyCode}/{branchCode}")
	public ResponseEntity<?> enabledById(@PathVariable(name = "id", required = true) Long id,
			@PathVariable(name = "status", required = true) String status) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT enabledById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , status: %s ]", idOperation, id.toString(), status));

		boolean enabled = status.equals("enable") ? true : false;

		ResponseModel responseModel = userServicePort.enableById(id, enabled, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización del detalle de usuario por id
	 * 
	 * @param id identificador de usuario
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/{id}/{companyCode}/{branchCode}")
	public ResponseEntity<?> findById(@PathVariable Long id) {

		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT findById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

		ResponseModel responseModel = userServicePort.findById(id, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización de la lista de usuarios activos por código de
	 * sucursal
	 * 
	 * @param branch código de sucursal
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/active/{companyCode}/{branchCode}")
	public ResponseEntity<?> findAllByBranchAndActive(@PathVariable String branchCode) {

		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT findAllByBranchAndActive()", idOperation));
		LOG.info(String.format("%s PARAMS: [branch: %s ]", idOperation, branchCode));

		ResponseModel responseModel = userServicePort.findAllByBranchAndEnable(branchCode, true, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización de la lista de usuarios inactivos por código
	 * de sucursal
	 * 
	 * @param branch código de sucursal
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/inactive/{companyCode}/{branchCode}")
	public ResponseEntity<?> findAllByBranchAndInactive(@PathVariable String branchCode) {

		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT findAllByBranchAndInactive()", idOperation));
		LOG.info(String.format("%s PARAMS: [branch: %s ]", idOperation, branchCode));

		ResponseModel responseModel = userServicePort.findAllByBranchAndEnable(branchCode, false, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para recuperar la lista de usuarios por un filtro de datos paginados
	 * y ordenados por número de empleado descendiente
	 * 
	 * @param pageNumber                número de la página
	 * @param rows                      número de registros solicitados por página
	 * @param genericSerchParamsUserDto filtro de datos para la búsqueda
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@PostMapping("/find-by-params/{pageNumber}/{rows}/{companyCode}/{branchCode}")
	public ResponseEntity<?> getUserListByParamsAndPage(@PathVariable int pageNumber, @PathVariable int rows,
			@RequestBody GenericSerchParamsUserDto genericSerchParamsUserDto) {

		idOperation = generateIdOperation("COMPANY", module);
		LOG.info(String.format("%s INIT getUserListByParamsAndPage()", idOperation));
		LOG.info(String.format("%s PARAMS: [pageNumber: %d , rows: %d , genericSerchParamsUserDto: %s ]", idOperation,
				pageNumber, rows, genericSerchParamsUserDto.toString()));

		ResponseModel responseModel = userServicePort.findUserLitsByParamsAndPage(pageNumber, rows,
				genericSerchParamsUserDto, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * EndPoint para la actualización del estado de actividad de las sesiones de los
	 * usuarios por email y código de sucursal
	 * 
	 * @param status     indicador para determinar el estado de la sesión, activa o
	 *                   inactiva
	 * @param email      correo electrónico del usuario
	 * @param branchCode código de compañia
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@PutMapping("/session-status/{status:enable|disable}/{email}/{companyCode}/{branchCode}")
	public ResponseEntity<?> changeSessionStatus(@PathVariable(name = "status", required = true) String status,
			@PathVariable String email, @PathVariable String branchCode) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT changeSessionStatus()", idOperation));
		LOG.info(String.format("%s PARAMS: [status: %s , email: %s , branchCode: %s ]", idOperation, status, email,
				branchCode));

		boolean active = status.equals("enable") ? true : false;

		ResponseModel responseModel = userServicePort.changeStatusSessionByUsernameAndBranchCode(email, branchCode,
				active, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

}
