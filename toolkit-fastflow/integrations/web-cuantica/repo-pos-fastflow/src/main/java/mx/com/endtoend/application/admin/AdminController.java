package mx.com.endtoend.application.admin;

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
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;;

/**
 * Controlador de ETE para la gestión de datos operacionales del sistema y alta
 * de usuarios administradores de las compañias que ocupen el sistema
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/ete/users")
public class AdminController {

	@Autowired
	private UserServicePort userServicePort;

	@Autowired
	private SecurityLogServicePort securityLogServicePort;

	private String module = "USER-ADMINISTRATION";

	public String idOperation = "";

	private final Logger LOG = LoggerFactory.getLogger(AdminController.class);

	/**
	 * EndPoint para la creación de un usuarios administrador de compañia.
	 * 
	 * @param userDto modelo de datos para los registros de usuarios en el sistema.
	 *
	 * @return responseModel modelo de respuesta genérico que contiene la
	 *         información generada por el sistema y el código con el resultado de
	 *         la operación
	 */
	@PostMapping("/create")
	public ResponseEntity<?> createUserAdmin(@RequestBody UserDto userDto) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT createUserAdmin()", idOperation));
		LOG.info(String.format("%s PARAMS: [userDto: %s ]", idOperation, userDto.toString()));

		ResponseModel responseModel = userServicePort.createUser(userDto, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

	/**
	 * EndPoint para la actualización de usuarios por id.
	 * 
	 * @param userDto modelo de datos para la actualización de datos del usuario
	 * @param id      identificafor de usuario
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateUserAdmin(@RequestBody UserDto userDto, @PathVariable Long id) {
		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT updateUserAdmin()", idOperation));
		LOG.info(String.format("%s PARAMS: [userDto: %s , id: %s ]", idOperation, userDto.toString(), id.toString()));
		userDto.setId(id);
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel responseModel = userServicePort.updateUser(securityLogServicePort, userLogged, userDto,
				idOperation);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la habilitación/deshabilitación de los usuarios por id
	 * 
	 * @param id     identificador de usuario
	 * @param status indicador para habilitar o deshabilitar el estado del usuario
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@PutMapping("/enabled/{status:enable|disable}/{id}")
	public ResponseEntity<?> enableUserAdmin(@PathVariable(name = "id", required = true) Long id,
			@PathVariable(name = "status", required = true) String status) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT enableUserAdmin()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , status: %s ]", idOperation, id.toString(), status));

		boolean enabled = status.equals("enable") ? true : false;

		ResponseModel responseModel = userServicePort.enableById(id, enabled, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para ver el detalle de un usuario por id
	 * 
	 * @param id identificador de usuario
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/{id}")
	public ResponseEntity<?> findUserAdminById(@PathVariable Long id) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT findUserAdminById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

		ResponseModel responseModel = userServicePort.findById(id, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización de los usuarios activos en el sistema por
	 * código de sucursal
	 * 
	 * @param branch código de sucursal
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/active/{branch}")
	public ResponseEntity<?> findAllActiveUsersAdmin(@PathVariable String branch) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT findAllActiveUsersAdmin()", idOperation));
		LOG.info(String.format("%s PARAMS: [branch: %s ]", idOperation, branch));

		ResponseModel responseModel = userServicePort.findAllByBranchAndEnable(branch, true, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * Endpoint para la visualización de los usuarios inactivos por código de
	 * sucursal
	 * 
	 * @param branch código de sucursal
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/inactive/{branch}")
	public ResponseEntity<?> findAllInactiveUsersAdmin(@PathVariable String branch) {

		idOperation = generateIdOperation(module);

		LOG.info(String.format("%s INIT findAllInactiveUsersAdmin()", idOperation));
		LOG.info(String.format("%s PARAMS: [branch: %s ]", idOperation, branch));

		ResponseModel responseModel = userServicePort.findAllByBranchAndEnable(branch, false, idOperation);

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
	@PostMapping("/find-by-params/{pageNumber}/{rows}")
	public ResponseEntity<?> getUserListByParamsAndPage(@PathVariable int pageNumber, @PathVariable int rows,
			@RequestBody GenericSerchParamsUserDto genericSerchParamsUserDto) {

		idOperation = generateIdOperation(module);
		LOG.info(String.format("%s INIT getUserListByParamsAndPage()", idOperation));
		LOG.info(String.format("%s PARAMS: [pageNumber: %d , rows: %d , genericSerchParamsUserDto: %s ]", idOperation,
				pageNumber, rows, genericSerchParamsUserDto.toString()));

		ResponseModel responseModel = userServicePort.findUserLitsByParamsAndPage(pageNumber, rows,
				genericSerchParamsUserDto, idOperation);

		LOG.info(String.format("%s OPERATION RESULT: %d", idOperation, responseModel.getResponseCode()));

		return new ResponseEntity<>(responseModel, HttpStatus.OK);

	}

}
