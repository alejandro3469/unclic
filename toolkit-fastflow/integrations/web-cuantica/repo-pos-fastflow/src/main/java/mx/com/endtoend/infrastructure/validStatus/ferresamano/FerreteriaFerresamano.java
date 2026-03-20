package mx.com.endtoend.infrastructure.validStatus.ferresamano;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.repository.F0005FFerresamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.repositories.EmployeeFSamanoRepository;

/**
 * Clase encargada de realizar consultas para validar la conexión hacia las BDs
 * de la compañía Ferresamano
 * 
 * @author sistema
 *
 */
@ConditionalOnProperty(name = "app.ferresamano.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaFerresamano extends BaseConexionService implements FerreteriaFerresamanoPersistencePort {

	@Autowired
	private EmployeeFSamanoRepository employeeFSamanoRepository;

	@Autowired(required = false)
	private F0005FFerresamanoRepository f0005Repository;

	@Override
	public boolean MySQLConnectionConnection() {
		return validarConexion(() -> employeeFSamanoRepository.validConnection(), "MySQL");
	}

	@Override
	public boolean OracleConnectionConnection() {
		if (f0005Repository == null) {
			return false;
		}
		return validarConexion(() -> f0005Repository.validConnection(), "Oracle");
	}

}

