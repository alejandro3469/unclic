package mx.com.endtoend.domain.debug.ports.api;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface DebugServicePort {

	ResponseModel getDebugStatusByCompanyCodeAndModule(String companyCode, String module);

	void saveLog(String module, String companyCode, String log, String idOperation, boolean debucg);
}
