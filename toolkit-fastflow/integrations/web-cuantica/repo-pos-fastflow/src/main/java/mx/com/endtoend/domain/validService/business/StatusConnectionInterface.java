package mx.com.endtoend.domain.validService.business;

import mx.com.endtoend.domain.validService.dto.ConnectionStatus;

public interface StatusConnectionInterface {

	ConnectionStatus getDBStatus();
}
