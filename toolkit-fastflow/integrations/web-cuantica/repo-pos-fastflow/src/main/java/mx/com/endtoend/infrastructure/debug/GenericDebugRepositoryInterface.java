package mx.com.endtoend.infrastructure.debug;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericDebugRepositoryInterface {

	ResponseModel getDebugStatusByCompanyCodeAndModule(String module);

	void saveLog(DebugDto debugDto);
}
