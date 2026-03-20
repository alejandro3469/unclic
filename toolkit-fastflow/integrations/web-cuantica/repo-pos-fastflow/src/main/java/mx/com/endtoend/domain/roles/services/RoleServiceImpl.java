package mx.com.endtoend.domain.roles.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.roles.ports.api.RoleServicePort;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase que implementa la interfaz RoleServicePort con la lógica de negocio del
 * módulo de Roles.
 * 
 * @author ddcasas
 *
 */
public class RoleServiceImpl implements RoleServicePort {

	private RolePersistencePort rolePersistencePort;

	public RoleServiceImpl(RolePersistencePort rolePersistencePort) {
		this.rolePersistencePort = rolePersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(RoleServiceImpl.class);

	private RoleBusinessValidator roleBusinessValidator = new RoleBusinessValidator();

	/**
	 * Método para la creación de roles en el sistema
	 * 
	 * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
	 *                uno de los endpoints del sistema y la descripción del rol
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel create(RoleDto roleDto) {
		String validations = roleBusinessValidator.validOperativeDataToCreate(roleDto, rolePersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError(validations);
		RoleDto roleCreated = rolePersistencePort.create(roleDto);
		return new ResponseModel(roleCreated);
	}

	/**
	 * Método para la actualización de roles.
	 * 
	 * @param roleDto objeto que contiene el conjunto de permisos de acceso a cada
	 *                uno de los endpoints del sistema y la descripción del rol
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel update(RoleDto roleDto, String userLogged, SecurityLogServicePort securityLogServicePort) {
		String validations = roleBusinessValidator.validOperativeDataToUpdate(roleDto, rolePersistencePort);
		if (!validations.isEmpty())
			throw new ValidationError(validations);
		RoleDto roleSaved = rolePersistencePort.findById(roleDto.getId());
		RoleDto roleUpdated = rolePersistencePort.update(roleDto);
		securityLogServicePort.generateRoleChangeLog(userLogged, roleSaved, roleUpdated);
		return new ResponseModel(roleUpdated);
	}

	/**
	 * Método para la habilitación/deshabilitación de roles en el sistema por
	 * identificador.
	 * 
	 * @param id     identificador de rol
	 * @param enable valor boolean para asiganar el estado del rol
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel enableById(Long id, boolean enable) {
		LOG.info(String.format("INIT enableById()"));
		RoleDto roleDto = rolePersistencePort.enableById(id, enable);
		if (roleDto == null) {
			LOG.info("ERROR AL " + (enable ? "HABILITAR " : "DESHABILITAR") + "EL ROL " + id);
			throw new GlobalError();
		}
		LOG.info("SE " + (enable ? "HABILITA " : "DESHABILITA ") + "EL ROL " + id);
		return new ResponseModel(roleDto);
	}

	/**
	 * Método para la búsqueda de roles por nombre y código de compañía.
	 * 
	 * @param name    nombre del rol
	 * @param company código de compañia
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel findByNameAndCompany(String name, String company) {

		LOG.info(String.format("INIT findByNameAndCompany()"));
		RoleDto roleDto = rolePersistencePort.findByNameAndCompany(name, company);
		if (roleDto == null) {
			LOG.info("ERROR AL RECUPEAR ROL " + name + " DE LA COMPAÑIA " + company);
			throw new GlobalError();
		}
		return new ResponseModel(roleDto);
	}

	/**
	 * Método para la búsqueda de roles por id
	 * 
	 * @param id identificador de rol
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel findById(Long id) {
		LOG.info(String.format("INIT findById()"));
		RoleDto roleDto = rolePersistencePort.findById(id);
		if (roleDto == null) {
			LOG.info("ERROR EN RECUPERAN DATOS DEL ROL: " + id);
			throw new GlobalError();
		}
		return new ResponseModel(roleDto);
	}

	/**
	 * Método para la búsqueda de una lista de roles asignados a un usuaior por su
	 * id.
	 * 
	 * @param id identificador de usuario
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel findAllByUserId(Long id) {
		LOG.info(String.format("INIT findAllByUserId()"));
		List<RoleDto> roleDtoList = rolePersistencePort.findAllByUserId(id);
		if (roleDtoList == null) {
			LOG.info("ERROR AL RECUPERAR LISTA DE ROLES PARA EL USUARIO: " + id);
			throw new GlobalError();
		}
		return new ResponseModel(roleDtoList);
	}

	/**
	 * Método para la búsqueda de todos los roles por estado (habilitados o
	 * deshabilitados) y código de compañia.
	 * 
	 * @param enable  indicador de estado del rol
	 * @param company código de compañía
	 * 
	 * @return ResponseModel, objeto que contiene la información del objeto con los
	 *         datos generados por el sistema y el código de respuesta
	 */
	@Override
	public ResponseModel findAllByEnableAndCompany(boolean enable, String company) {
		LOG.info(String.format("INIT findAllByEnableAndCompany()"));
		List<RoleDto> roleDtoList = rolePersistencePort.findAllByEnabledAndCompany(enable, company);
		if (roleDtoList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(roleDtoList);
	}

}
