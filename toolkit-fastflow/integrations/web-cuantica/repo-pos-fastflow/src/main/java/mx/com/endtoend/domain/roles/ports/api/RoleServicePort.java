package mx.com.endtoend.domain.roles.ports.api;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface RoleServicePort {

	ResponseModel create(RoleDto roleDto);

	ResponseModel update(RoleDto roleDto, String userLogged, SecurityLogServicePort securityLogServicePort);

	ResponseModel enableById(Long id, boolean enable);

	ResponseModel findByNameAndCompany(String name, String company);

	ResponseModel findById(Long id);

	ResponseModel findAllByUserId(Long id);

	ResponseModel findAllByEnableAndCompany(boolean enable, String company);
}
