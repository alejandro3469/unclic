package mx.com.endtoend.domain.roles.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.roles.dto.RoleDto;

public interface RolePersistencePort {

	RoleDto create(RoleDto roleDto);
	
	RoleDto findById(Long id);
	
	RoleDto findByNameAndCompany(String name, String companyKey);
	
	List<RoleDto> findAllByEnabledAndCompany(boolean enabled, String companyKey);

	boolean existsByNameAndCompany(String name, String companyKey);
	
	boolean existsByNameAndCompanyAndIdNot(String name, String companyKey, Long id);
	
	RoleDto enableById(Long id,boolean enabled);
	
	RoleDto update(RoleDto roleDto);
	
	List<RoleDto> findAllByUserId(Long id);
}
