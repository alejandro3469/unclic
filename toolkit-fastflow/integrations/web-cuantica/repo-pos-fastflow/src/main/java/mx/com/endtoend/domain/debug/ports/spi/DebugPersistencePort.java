package mx.com.endtoend.domain.debug.ports.spi;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface DebugPersistencePort {

	ResponseModel getDebugStatusByCompanyCodeAndModule(String companyCode, String module);

	void saveLog(String companyCode, DebugDto debugDto);

}
