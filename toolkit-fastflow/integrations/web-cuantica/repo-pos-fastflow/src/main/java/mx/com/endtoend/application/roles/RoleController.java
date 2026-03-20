package mx.com.endtoend.application.roles;

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
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.roles.ports.api.RoleServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Controlador para la administración de los roles de acceso del sistema por
 * parte del personal de ETE y de los usuarios administradores de cada compañía.
 * 
 * @author ddcasas
 *
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleServicePort roleServicePort;

	@Autowired
	private PermissionServicePort permissionServicePort;

	@Autowired
	private SecurityLogServicePort securityLogServicePort;

	private final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	/**
	 * EndPoint para la creación de roles
	 * 
	 * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
	 *                uno de los endpoints del sistema y la descripción del rol
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PostMapping("/create/{company}/{branchCode}")
	public ResponseEntity<?> createByCompany(@RequestBody RoleDto roleDto) {
		LOG.info(String.format("INIT createByCompany()"));
		LOG.info(String.format("PARAMS: [roleDto: %s ]", roleDto.toString()));
		ResponseModel responseModel = roleServicePort.create(roleDto);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la actualización de roles
	 * 
	 * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
	 *                uno de los endpoints del sistema y la descripción del rol
	 * @param id      identificador del rol
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PutMapping("/update/{id}/{company}/{branchCode}")
	public ResponseEntity<?> update(@RequestBody RoleDto roleDto, @PathVariable Long id) {
		LOG.info(String.format("INIT update()"));
		LOG.info(String.format("PARAMS: [roleDto: %s , id: %s ]", roleDto.toString(), id.toString()));
		roleDto.setId(id);
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String userLogged = loggedInUser.getName();
		ResponseModel responseModel = roleServicePort.update(roleDto, userLogged, securityLogServicePort);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la habilitación o deshabilitación de los roles en el sistema
	 * 
	 * @param id     identificador del rol
	 * @param status indicador para habilitar o deshabilitar roles por id
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@PutMapping("/enabled/{id}/{status:enable|disable}/{company}/{branchCode}")
	public ResponseEntity<?> enabledById(@PathVariable(name = "id", required = true) Long id,
			@PathVariable(name = "status", required = true) String status) {
		LOG.info(String.format("INIT enabledById()"));
		LOG.info(String.format("PARAMS: [id: %s , status: %s]", id.toString(), status));
		boolean enabled = status.equals("enable") ? true : false;
		ResponseModel responseModel = roleServicePort.enableById(id, enabled);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para la visualización del detalle de un rol por id
	 * 
	 * @param id identificador del rol
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@GetMapping("/view/{id}/{company}/{branchCode}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		LOG.info(String.format("INIT findById()"));
		LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));
		ResponseModel responseModel = roleServicePort.findById(id);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar la lista de roles activos por código de compañía
	 * 
	 * @param company código de compañía
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@GetMapping("/view/active/{company}/{branchCode}")
	public ResponseEntity<?> findAllByCompanyAndActive(@PathVariable String company) {
		LOG.info(String.format("INIT findAllByCompanyAndActive()"));
		LOG.info(String.format("PARAMS: [company: %s ]", company));
		ResponseModel responseModel = roleServicePort.findAllByEnableAndCompany(true, company);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar la lista de roles inactivos por código de compañía
	 * 
	 * @param company código de compañía
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto
	 *         actualizado y el código de respuesta
	 */
	@GetMapping("/view/inactive/{company}/{branchCode}")
	public ResponseEntity<?> findAllByCompanyAndInactive(@PathVariable String company) {
		LOG.info(String.format("INIT findAllByCompanyAndInactive()"));
		LOG.info(String.format("PARAMS: [company: %s ]", company));
		ResponseModel responseModel = roleServicePort.findAllByEnableAndCompany(false, company);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar los permisos que pueden ser asignados por parte de
	 * los usuarios administradores y que estan relacionados a una compañía
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/permissions/company/{companyCode}")
	public ResponseEntity<?> findPermissionsToClient(@PathVariable String companyCode) {
		LOG.info(String.format("INIT findPermissionsToClient()"));
		LOG.info(String.format("PARAMS: [company: %s ]", companyCode));
		ResponseModel responseModel = permissionServicePort.findAllByCompanyCode(companyCode);
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}

	/**
	 * EndPoint para visualizar los permisos que pueden ser asignados por parte de
	 * el personal de ETE
	 * 
	 * @return ResponseModel objeto que contiene la información del objeto y el
	 *         código de respuesta
	 */
	@GetMapping("/view/permissions/admin/{company}/{branchCode}")
	public ResponseEntity<?> findPermissionsToAdmin() {
		LOG.info(String.format("INIT findPermissionsToAdmin()"));
		ResponseModel responseModel = permissionServicePort.findAllByAdmin();
		return new ResponseEntity<>(responseModel, HttpStatus.OK);
	}
}
