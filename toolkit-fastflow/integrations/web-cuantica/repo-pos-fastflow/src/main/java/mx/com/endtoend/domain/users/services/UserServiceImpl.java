package mx.com.endtoend.domain.users.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.api.UserServicePort;
import mx.com.endtoend.domain.users.ports.spi.UserPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * Clase con la lógica de negocio del módulo de usuarios.
 * 
 * @author ddcasas
 *
 */

public class UserServiceImpl implements UserServicePort {

	private UserPersistencePort userPersistencePort;

	public UserServiceImpl(UserPersistencePort userPersistencePort) {
		this.userPersistencePort = userPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	/**
	 * Método para la creación de usuarios en el sistema.
	 * 
	 * @param user        módelo de datos de la información de usuario
	 * @param idOperation traza de identificación de la operación
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel createUser(UserDto user, String idOperation) {

		LOG.info(String.format("%s INIT createUser()", idOperation));
		user.setIsConfigurationComplete(false);

		LOG.info(String.format("%s START VALIDATION TO SAVE NEW USER, DATA: %s", idOperation, user.toString()));
		boolean existsByUserNumber = (boolean) userPersistencePort
				.existsByUserNumber(user.getUserNumber(), idOperation)
				.getData();

		if (existsByUserNumber) {
			LOG.warn(String.format("%s THE USER-NUMBER ALREADY EXISTS", idOperation));
			throw new ValidationError("USER-NUMBER ALREADY EXISTS");
		}

		boolean existsByEmail = (boolean) userPersistencePort.existEmail(user.getEmail(), idOperation).getData();
		if (existsByEmail) {
			LOG.warn(String.format("%s THE EMAIL ALREADY EXISTS", idOperation));
			throw new ValidationError("EMAIL ALREADY EXISTS");
		}
		ResponseModel responseFromPersistencePort = userPersistencePort.create(user, idOperation);
		return responseFromPersistencePort;

	}

	/**
	 * Metodo para la actualización de los datos de los usuarios.
	 * 
	 * @param user        módelo de datos de la información de usuario
	 * @param idOperation traza de identificación de la operación
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel updateUser(SecurityLogServicePort securityLogServicePort, String userLogged, UserDto user,
			String idOperation) {

		LOG.info(String.format("%s INIT updateUser()", idOperation));

		LOG.info(String.format("%s START VALIDATION TO UPDATE USER, DATA: %s", idOperation, user.toString()));
		boolean existsByUserNumberAndIdNot = (boolean) userPersistencePort.existsByUserNumberAndIdNot(
				user.getUserNumber(), user.getId(), idOperation).getData();

		if (existsByUserNumberAndIdNot) {
			LOG.warn(String.format("%s THE USERNUMBER ALREADY EXISTS", idOperation));
			throw new ValidationError("USER-NUMBER ALREADY EXISTS");
		}

		boolean existByEmailAndIdNot = (boolean) userPersistencePort
				.existEmailAndIdNot(user.getEmail(), user.getId(), idOperation).getData();
		if (existByEmailAndIdNot) {
			LOG.warn(String.format("%s THE EMAIL ALREADY EXISTS", idOperation));
			throw new ValidationError("EMAIL ALREADY EXISTS");
		}
		UserDto userSaved = (UserDto) userPersistencePort.findById(user.getId(), idOperation).getData();
		ResponseModel responseFromPersistencePort = userPersistencePort.update(user, idOperation);
		securityLogServicePort.generateUserChangeLog(userLogged, userSaved, user);
		
		return responseFromPersistencePort;
	}

	/**
	 * Método para habilitar/deshabilitar usuarios por id.
	 * 
	 * @param id          identificador de usuario
	 * @param enable      indicador de habilitación/deshabilitación
	 * @param idOperation identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel enableById(Long id, boolean enable, String idOperation) {

		LOG.info(String.format("%s INIT enableById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s , enable: %b]", idOperation, id.toString(), enable));

		LOG.info(String.format("%s CHANGE STATUS TO USER %d", idOperation, id));

		ResponseModel responseFromPersistencePort = userPersistencePort.enableById(id, enable, idOperation);

		UserDto userDto = (UserDto) responseFromPersistencePort.getData();

		if (userDto != null) {

			LOG.info(String.format("%s USER WITH ID: %d IS %s ", idOperation, id, (enable ? "ENABLE " : "DISABLE")));
			return new ResponseModel(true);

		} else {

			LOG.info(String.format("%s ERROR IN OPERATION: enableById", idOperation));
			throw new GlobalError();
		}
	}

	/**
	 * Método para obtener un usuario por id
	 * 
	 * @param id          identificador de usuario
	 * @param idOperation identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel findById(Long id, String idOperation) {

		LOG.info(String.format("%s INIT findById()", idOperation));
		LOG.info(String.format("%s PARAMS: [id: %s ]", idOperation, id.toString()));

		LOG.info(String.format("%s FIND USER BY ID %d ", idOperation, id));
		ResponseModel responseFromPersistencePort = userPersistencePort.findById(id, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	/**
	 * Método para obtener un usuario por número de identificación
	 * 
	 * @param userNumber  número de identificador
	 * @param idOperation identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 * 
	 */
	@Override
	public ResponseModel findByUserNumber(Long userNumber, String idOperation) {

		LOG.info(String.format("%s INIT findByUserNumber()", idOperation));
		LOG.info(String.format("%s PARAMS: [userNumber: %s ]", idOperation, userNumber.toString()));

		LOG.info(String.format("%s FIND USER BY USERNUMBER %d ", idOperation, userNumber));
		ResponseModel responseFromPersistencePort = userPersistencePort.findByUserNumber(userNumber, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}

	/**
	 * Método para obtener lista de usuarios activos y código de sucursal
	 * 
	 * @param branchCode  código de sucursal
	 * @param enable      indicador de estado del usuario
	 * @param idOperation identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel findAllByBranchAndEnable(String branchCode, boolean enable, String idOperation) {

		LOG.info(String.format("%s INIT findAllByBranchAndEnable()", idOperation));
		LOG.info(String.format("%s PARAMS: [branchCode: %s , enable: %b ]", idOperation, branchCode, enable));

		LOG.info(String.format("%s FIND ALL USER BY ENABLE %b ", idOperation, enable));
		ResponseModel responseFromPersistencePort = userPersistencePort.findAllByEnableAndBranchCode(enable, branchCode,
				idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;
	}

	/**
	 * Método para obtener la lista de usuarios mediante un filtro de búsqueda, la
	 * respuesta esta seccionada por página retornando la cantidad de registros
	 * solicitados por página
	 * 
	 * @param pageNumber                número de página solicitada
	 * @param rows                      número de registros solicitados
	 * @param genericSerchParamsUserDto objeto que contiene los filtros de búsqueda
	 * @param idOperation               identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel findUserLitsByParamsAndPage(int pageNumber, int rows,
			GenericSerchParamsUserDto genericSerchParamsUserDto, String idOperation) {

		LOG.info(String.format("%s INIT findUserLitsByParamsAndPage()", idOperation));
		LOG.info(String.format("%s PARAMS: [pageNumber: %d , rows: %d , genericSerchParamsUserDto: %s ]", idOperation,
				pageNumber, rows, genericSerchParamsUserDto.toString()));

		ResponseModel responseFromPersistencePort = userPersistencePort.findAllByParamsAndPage(pageNumber, rows,
				genericSerchParamsUserDto, idOperation);

		LOG.info(String.format("%s RESULT FROM PERSISTENCE PORT: %d", idOperation,
				responseFromPersistencePort.getResponseCode()));

		return responseFromPersistencePort;

	}

	/**
	 * Método para obtener los datos de los usuarios al momento de logearse en el
	 * sistema por el email con el que fueron registrados
	 * 
	 * @param email       correo electronico con el que el usuario fue registrado
	 * @param idOperation identificador de traza
	 * 
	 * @return ResponseModel objeto con el código de estado de la operación y datos
	 *         generados por el sistema
	 */
	@Override
	public ResponseModel findUserByEmailToLogin(String email, String idOperation) {

		LOG.info(String.format("%s INIT findUserByEmailToLogin()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s]", idOperation, email));

		LOG.info(String.format("%s FIND USER BY EMAIL TO LOGIN %s ", idOperation, email));

		UserDto user = (UserDto) userPersistencePort.findByEmailToLogin(email, idOperation).getData();

		if (user != null) {

			LOG.info(String.format("%s USER FOUND: ", idOperation));
			return new ResponseModel(user);

		} else {

			LOG.info(String.format("%s ERROR IN OPERATION: findUserByEmailToLogin", idOperation));

			throw new GlobalError();
		}

	}

	/**
	 * Método que actualiza el estado de la sesión de usuario por nombre de usuario
	 * y código de sucursal.
	 * 
	 * @param username    nombre de usuairo
	 * @param branchCode  código de sucursal
	 * @param status      indicador para habilitar/deshabilitar estado de sesión
	 * @param idOperation identificador de traza
	 * 
	 * @returne ResponseModel objeto con el código de estado de la operación y datos
	 *          generados por el sistema
	 */
	@Override
	public ResponseModel changeStatusSessionByUsernameAndBranchCode(String email, String branchCode, boolean status,
			String idOperation) {

		LOG.info(String.format("%s INIT changeStatusSessionByUsernameAndBranchCode()", idOperation));
		LOG.info(String.format("%s PARAMS: [email: %s , branchCode: %s , status: %b ]", idOperation, email, branchCode,
				status));

		LOG.info(String.format("%s CHANGE STATUS SESSION BY EMAIL: %s AND BRANCH: %s AND SET: %b", idOperation, email,
				branchCode, status));

		ResponseModel responseModel = userPersistencePort.updateStatusActiveByUserNameAndBranchCode(email, branchCode,
				status);

		return responseModel;
	}

}