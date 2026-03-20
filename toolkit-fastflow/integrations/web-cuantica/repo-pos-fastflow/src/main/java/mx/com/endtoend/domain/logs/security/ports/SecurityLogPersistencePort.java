package mx.com.endtoend.domain.logs.security.ports;

import mx.com.endtoend.domain.logs.security.dto.EmployeeSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.RoleSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.UserSummaryLogDto;

public interface SecurityLogPersistencePort {

	void saveRoleLog(RoleSummaryLogDto roleSummaryLogDto);

	void saveUserLog(UserSummaryLogDto userSummaryLogDto);

	void saveUserConfigurationLog(EmployeeSummaryLogDto employeeSummaryLog);

}
