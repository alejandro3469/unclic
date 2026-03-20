package mx.com.endtoend.domain.logs.security.ports;

import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.users.dto.UserDto;

public interface SecurityLogServicePort {

	Boolean generateRoleChangeLog(String userLogged, RoleDto roleSaved, RoleDto roleUpdated);

	Boolean generateUserChangeLog(String userLogged, UserDto userSaved, UserDto userUpdated);

	Boolean generateUserConfigurationChangeLog(String userLogged, EmployeeDto employeeSaved, EmployeeDto employeeUpdated);

}
