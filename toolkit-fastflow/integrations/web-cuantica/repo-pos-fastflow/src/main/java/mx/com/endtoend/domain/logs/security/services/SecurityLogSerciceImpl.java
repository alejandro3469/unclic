package mx.com.endtoend.domain.logs.security.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.logs.security.dto.EmployeeSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.RoleSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.UserSummaryLogDto;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogPersistencePort;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;

public class SecurityLogSerciceImpl implements SecurityLogServicePort {

	private SecurityLogPersistencePort securityLogPersistencePort;

	public SecurityLogSerciceImpl(SecurityLogPersistencePort securityLogPersistencePort) {
		this.securityLogPersistencePort = securityLogPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(SecurityLogSerciceImpl.class);

	@Override
	public Boolean generateRoleChangeLog(String userLogged, RoleDto roleSaved, RoleDto roleUpdated) {

		try {
			LOG.info("generateRoleChangeLog()");
			RoleSummaryLogDto roleSummaryLogDto = new RoleSummaryLogDto();
			roleSummaryLogDto.setUser(userLogged);
			roleSummaryLogDto.setRoleName(roleSaved.getName());
			roleSummaryLogDto.setUpdatedDate(new Date());
			roleSummaryLogDto.setRoleId(roleSaved.getId());
			roleSummaryLogDto.setRoleSaved(roleSaved);
			roleSummaryLogDto.setRoleUpdated(roleUpdated);
			securityLogPersistencePort.saveRoleLog(roleSummaryLogDto);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}

	}

	@Override
	public Boolean generateUserChangeLog(String userLogged, UserDto userSaved, UserDto userUpdated) {

		try {
			LOG.info("generateUserChangeLog()");
			UserSummaryLogDto userSummaryLogDto = new UserSummaryLogDto();
			userSummaryLogDto.setUser(userLogged);
			userSummaryLogDto.setUpdatedDate(new Date());
			userSummaryLogDto.setUserId(userSaved.getId());
			userSummaryLogDto.setUsername(userSaved.getEmail());
			userSummaryLogDto.setUserSaved(userSaved);
			userSummaryLogDto.setUserUpdated(userUpdated);
			securityLogPersistencePort.saveUserLog(userSummaryLogDto);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean generateUserConfigurationChangeLog(String userLogged, EmployeeDto employeeSaved,
			EmployeeDto employeeUpdated) {

		try {
			LOG.info("generateUserConfigurationChangeLog()");

			EmployeeSummaryLogDto employeeSummaryLog = new EmployeeSummaryLogDto();
			employeeSummaryLog.setUser(userLogged);
			employeeSummaryLog.setUpdatedDate(new Date());
			employeeSummaryLog.setUserId(employeeSaved.getUserId());
			employeeSummaryLog.setUsername(employeeSaved.getEmployeeEmail());
			employeeSummaryLog.setEmployeeSaved(employeeSaved);
			employeeSummaryLog.setEmployeeUpdated(employeeUpdated);
			securityLogPersistencePort.saveUserConfigurationLog(employeeSummaryLog);
			return true;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

}
