package mx.com.endtoend.infrastructure.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.domain.debug.ports.spi.DebugPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class DebugJpaAdapter implements DebugPersistencePort {

	@Autowired
	private DebugRepositoryFactory debugRepositoryFactory;

	@Override
	public ResponseModel getDebugStatusByCompanyCodeAndModule(String companyCode, String module) {
		GenericDebugRepositoryInterface repository = debugRepositoryFactory.getRepositopry(companyCode);
		return repository.getDebugStatusByCompanyCodeAndModule(module);
	}

	@Override
	public void saveLog(String companyCode, DebugDto debugDto) {
		GenericDebugRepositoryInterface repository = debugRepositoryFactory.getRepositopry(companyCode);
		repository.saveLog(debugDto);
	}

}
