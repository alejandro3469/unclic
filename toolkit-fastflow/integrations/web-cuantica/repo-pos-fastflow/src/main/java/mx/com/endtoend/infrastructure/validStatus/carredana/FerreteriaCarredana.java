package mx.com.endtoend.infrastructure.validStatus.carredana;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0005FcarRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.repositories.EmployeeCarredanaRepository;

/**
 * Clase encargada de realizar consultas para validar la conexión hacia las BDs
 * de la compañía Carredana
 * 
 * @author ddcasas
 *
 */
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaCarredana extends BaseConexionService implements FerreteriaCarredanaPersistencePort {

	@Autowired
	private EmployeeCarredanaRepository employeeFraguaRepository;

	@Autowired(required = false)
	private F0005FcarRepository f0005Repository;


	@Override
	public boolean MySQLConnectionConnection() {
		return validarConexion(() -> employeeFraguaRepository.validConnection(), "MySQL");
	}

	@Override
	public boolean OracleConnectionConnection() {
		if (f0005Repository == null) {
			return false;
		}
		return validarConexion(() -> f0005Repository.validConnection(), "Oracle");
	}

}
