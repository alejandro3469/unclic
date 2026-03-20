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
import mx.com.endtoend.infrastructure.validStatus.zapata.FerreteriaZapataPersistencePort;

@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class FerreteriaZapataService implements StatusConnectionInterface {
	
	@Autowired(required = false)
	private FerreteriaZapataPersistencePort ferreteriaZapataPersistencePort;

	@Override
	public ConnectionStatus getDBStatus() {
		if (ferreteriaZapataPersistencePort == null) {
			ConnectionStatus connectionStatus = new ConnectionStatus();
			connectionStatus.setCompanyCode(CompanyCodes.CZAP.toString());
			connectionStatus.setConectionStatus(new ArrayList<>());
			return connectionStatus;
		}

		ConnectionStatus connectionStatus = new ConnectionStatus();
		List<ConnectionDBStatus> connectionDBStatusList = new ArrayList<>();

		ConnectionDBStatus connectionMySQL = new ConnectionDBStatus();
		connectionMySQL.setDatabase("MYSQL");
		boolean status = ferreteriaZapataPersistencePort.MySQLConnectionConnection();
		connectionMySQL.setStatus(status ? "ACTIVE" : "INACTIVE");

		ConnectionDBStatus connectionOracle = new ConnectionDBStatus();
		connectionOracle.setDatabase("ORACLE");
		boolean statusOracle = ferreteriaZapataPersistencePort.OracleConnectionConnection();
		connectionOracle.setStatus(statusOracle ? "ACTIVE" : "INACTIVE");

		connectionDBStatusList.add(connectionMySQL);
		connectionDBStatusList.add(connectionOracle);

		connectionStatus.setCompanyCode(CompanyCodes.CZAP.toString());
		connectionStatus.setConectionStatus(connectionDBStatusList);

		return connectionStatus;
	}

}
