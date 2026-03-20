package mx.com.endtoend.infrastructure.roles.common.converters;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;

@Component
public class RoleConverter {
	
	@Autowired
	private PermissionConverter permissionConverter;

	public RoleEntity roleDtoToRoleEntity(RoleDto roleDto) {
		
		RoleEntity roleEntity = new RoleEntity();
		
		roleEntity.setId(roleDto.getId());
		roleEntity.setName(roleDto.getName());
		roleEntity.setDescription(roleDto.getDescription());
		roleEntity.setCompanyKey(roleDto.getCompanyCode());
		roleEntity.setEnabled(roleDto.getEnabled());
		
		
		return roleEntity;
	}
	
	public RoleDto roleEntityToRoleDto (RoleEntity roleEntity) {
		
		RoleDto roleDto = new RoleDto();
		
		roleDto.setId(roleEntity.getId());
		roleDto.setName(roleEntity.getName());
		roleDto.setDescription(roleEntity.getDescription());
		roleDto.setCompanyCode(roleEntity.getCompanyKey());
		roleDto.setEnabled(roleEntity.isEnabled());
		List<PermissionEntity> permissionDto = new ArrayList<>(roleEntity.getPermissions());
		roleDto.setPermissions(permissionConverter.permissionEntityListToPermissionDtoList(permissionDto));
		
		return roleDto;
	}
	
	public List<RoleEntity> roleDtoListToRoleEntityList(List<RoleDto> roleDtoList){
		
		List<RoleEntity> roleEntityList = new ArrayList<RoleEntity>();
		
		for (RoleDto roleDto : roleDtoList) {
			roleEntityList.add(roleDtoToRoleEntity(roleDto));
		}
		
		return roleEntityList;
	}
	
	public List<RoleDto> roleEntityListToRoleDtoList(List<RoleEntity> roleEntityList){
		
		List<RoleDto> roleDtoList = new ArrayList<RoleDto>();
		
		for (RoleEntity roleEntity : roleEntityList) {
			roleDtoList.add(roleEntityToRoleDto(roleEntity));
		}
		
		return roleDtoList;	
	}
	
}
