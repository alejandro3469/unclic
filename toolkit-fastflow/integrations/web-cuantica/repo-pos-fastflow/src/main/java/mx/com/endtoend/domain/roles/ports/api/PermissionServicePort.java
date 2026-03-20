package mx.com.endtoend.domain.roles.ports.api;

import java.util.List;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface PermissionServicePort {
	
	PermissionDto create(PermissionDto permissionDto);
	
	PermissionDto findByName(String name);
	
	List<PermissionDto> findAllByUserId(Long id);
	
	ResponseModel findAllByCompanyCode(String companyCode);
	
	ResponseModel findAllByRoleId(Long id);
	
	ResponseModel findAllByClient();
	
	ResponseModel findAllByAdmin();
	
}
