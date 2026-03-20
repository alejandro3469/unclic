package mx.com.endtoend.infrastructure.validStatus.ete;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;

/**
 * Clase encargada de realizar consultas para validar la conexión hacia las BDs
 * de la compañía ETE siendo el sistema principal
 * 
 * @author ddcasas
 *
 */

@Service
public class EndToEnd extends BaseConexionService implements EndToEndPersistencePort {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean MySQLConnectionConnection() {
		return validarConexion(() -> userRepository.validConnection(), "MySQL");
	}

	@Override
	public boolean OracleConnectionConnection() {
		return false;
	}
}
