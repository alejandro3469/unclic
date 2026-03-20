package mx.com.endtoend.infrastructure.validStatus.fcal;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.repository.F0005Repository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.repositories.EmployeeRepository;

/**
 * Clase encargada de realizar consultas para validar la conexión hacia las BDs
 * de la compañía Calzada
 * 
 * @author ddcasas
 *
 */

@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaCalzadaConnectionStatus extends BaseConexionService implements FerreteriaCalzadaPersistencePort {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired(required = false)
	private F0005Repository f0005Repository;

	@Override
	public boolean MySQLConnectionConnection() {
		return validarConexion(() -> employeeRepository.validConnection(), "MySQL");
	}

	@Override
	public boolean OracleConnectionConnection() {
		if (f0005Repository == null) {
			return false;
		}
		return validarConexion(() -> f0005Repository.validConnection(), "Oracle");
	}

}
