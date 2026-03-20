package mx.com.endtoend.infrastructure.validStatus.zapata;

import mx.com.endtoend.infrastructure.validStatus.common.service.BaseConexionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0005FcarRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.repositories.EmployeeCZapataRepository;

@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaZapata extends BaseConexionService implements FerreteriaZapataPersistencePort {

	@Autowired
	private EmployeeCZapataRepository employeeFraguaRepository;

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
