package mx.com.endtoend.domain.debug.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.domain.debug.ports.spi.DebugPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class DebugServiceImpl implements DebugServicePort {

	@Value("${eureka.instance.instance-id}")
	private String idInstance;

	@Autowired
	private DebugPersistencePort debugPersistencePort;

	@Override
	public ResponseModel getDebugStatusByCompanyCodeAndModule(String companyCode, String module) {
		boolean active = false;
		try {
			active = (boolean) debugPersistencePort.getDebugStatusByCompanyCodeAndModule(companyCode, module).getData();
		} catch (Exception e) {
			active = false;
		}

		return new ResponseModel(active);
	}

	@Override
	public void saveLog(String module, String companyCode, String log, String idOperation, boolean debug) {
		if (debug) {
			DebugDto debugDto = new DebugDto(module, idInstance, companyCode, log, idOperation);
			debugPersistencePort.saveLog(companyCode, debugDto);
		}
	}

}
