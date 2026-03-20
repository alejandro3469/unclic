package mx.com.endtoend.infrastructure.company.common.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.infrastructure.company.common.entities.PermissionCompanyEntity;

@Component
public class PermissionCompanyConverter {

	public PermissionCompanyEntity permissionCompanyDtoToEntity(PermissionDto permissionDto, Long companyId) {
		
		PermissionCompanyEntity permissionCompanyEntity = new PermissionCompanyEntity();
		
		permissionCompanyEntity.setCompanyId(companyId);
		permissionCompanyEntity.setPermissionId(permissionDto.getId());
		
		return permissionCompanyEntity;
	}

}
