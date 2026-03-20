package mx.com.endtoend.domain.validService.business.companies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.validService.business.StatusConnectionInterface;
import mx.com.endtoend.domain.validService.dto.ConnectionDBStatus;
import mx.com.endtoend.domain.validService.dto.ConnectionStatus;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.validStatus.cfra.FerreteriaFraguaPersistencePort;

@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaFraguaService implements StatusConnectionInterface {

	@Autowired(required = false)
	private FerreteriaFraguaPersistencePort ferreteriaFraguaPersistencePort;

	@Override
	public ConnectionStatus getDBStatus() {
		if (ferreteriaFraguaPersistencePort == null) {
			ConnectionStatus connectionStatus = new ConnectionStatus();
			connectionStatus.setCompanyCode(CompanyCodes.CFRA.toString());
			connectionStatus.setConectionStatus(new ArrayList<>());
			return connectionStatus;
		}

		ConnectionStatus connectionStatus = new ConnectionStatus();
		List<ConnectionDBStatus> connectionDBStatusList = new ArrayList<>();

		ConnectionDBStatus connectionMySQL = new ConnectionDBStatus();
		connectionMySQL.setDatabase("MYSQL");
		boolean status = ferreteriaFraguaPersistencePort.MySQLConnectionConnection();
		connectionMySQL.setStatus(status ? "ACTIVE" : "INACTIVE");

		ConnectionDBStatus connectionOracle = new ConnectionDBStatus();
		connectionOracle.setDatabase("ORACLE");
		boolean statusOracle = ferreteriaFraguaPersistencePort.OracleConnectionConnection();
		connectionOracle.setStatus(statusOracle ? "ACTIVE" : "INACTIVE");

		connectionDBStatusList.add(connectionMySQL);
		connectionDBStatusList.add(connectionOracle);

		connectionStatus.setCompanyCode(CompanyCodes.CFRA.toString());
		connectionStatus.setConectionStatus(connectionDBStatusList);

		return connectionStatus;
	}

}
