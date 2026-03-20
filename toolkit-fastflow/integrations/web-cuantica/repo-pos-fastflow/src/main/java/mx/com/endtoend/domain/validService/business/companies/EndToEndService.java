package mx.com.endtoend.domain.validService.business.companies;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.validService.business.StatusConnectionInterface;
import mx.com.endtoend.domain.validService.dto.ConnectionDBStatus;
import mx.com.endtoend.domain.validService.dto.ConnectionStatus;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.validStatus.ete.EndToEndPersistencePort;

@Service
public class EndToEndService implements StatusConnectionInterface {

	@Autowired
	private EndToEndPersistencePort endToEndPersistencePort;

	@Override
	public ConnectionStatus getDBStatus() {

		ConnectionStatus connectionStatus = new ConnectionStatus();
		List<ConnectionDBStatus> connectionDBStatusList = new ArrayList<>();
		ConnectionDBStatus connectionDBStatus = new ConnectionDBStatus();
		
		connectionDBStatus.setDatabase("MYSQL");
		boolean status = endToEndPersistencePort.MySQLConnectionConnection();
		connectionDBStatus.setStatus(status ? "ACTIVE" : "INACTIVE");
		
		connectionDBStatusList.add(connectionDBStatus);
		
		connectionStatus.setCompanyCode(CompanyCodes.ETE.toString());
		connectionStatus.setConectionStatus(connectionDBStatusList);

		return connectionStatus;
	}

}
