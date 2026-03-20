package mx.com.endtoend.domain.roles.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.roles.dto.PermissionDto;

public interface PermissionPersistencePort {
	
	PermissionDto create(PermissionDto permissionDto);
	
	PermissionDto findById(Long id);
	
	PermissionDto findByName(String name);
	
	List<PermissionDto> findAll();
	
	List<PermissionDto> findAllByUserId(Long id);
	
	List<PermissionDto> findAllByRoleId(Long id);
	
	List<PermissionDto> findAllExceptModule(String module);
	
	List<PermissionDto> findAllByModule(String module);
	
	List<PermissionDto> findAllByCompanyCode(String companyCode);

}
