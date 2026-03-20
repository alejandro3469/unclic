package mx.com.endtoend.infrastructure.validStatus.demo;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.repository.F0005FDemoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.repositories.EmployeeDemoRepository;

/**
 * Clase encargada de realizar consultas para validar la conexión hacia las BDs
 * de la compañía Demo
 * 
 * @author sistema
 *
 */

@ConditionalOnProperty(name = "app.demo.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class DemoConnectionStatus extends BaseConexionService implements DemoPersistencePort {

	@Autowired
	private EmployeeDemoRepository employeeDemoRepository;

	@Autowired(required = false)
	private F0005FDemoRepository f0005DemoRepository;

	@Override
	public boolean MySQLConnectionConnection() {
		return validarConexion(() -> employeeDemoRepository.validConnection(), "MySQL");
	}

	@Override
	public boolean OracleConnectionConnection() {
		if (f0005DemoRepository == null) {
			return false;
		}
		return validarConexion(() -> f0005DemoRepository.validConnection(), "Oracle");
	}

}
