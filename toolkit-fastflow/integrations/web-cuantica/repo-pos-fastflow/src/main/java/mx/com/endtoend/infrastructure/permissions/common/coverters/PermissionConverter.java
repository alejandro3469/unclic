package mx.com.endtoend.infrastructure.permissions.common.coverters;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.infrastructure.commons.constants.PermissionEnum;

@Component
public class PermissionConverter {
	
	public PermissionEntity permissionDtoToPermissionEntity(PermissionDto permissionDto) {
		
		PermissionEntity permissionEntity = new PermissionEntity();
		
		permissionEntity.setId(permissionDto.getId());
		permissionEntity.setName(PermissionEnum.valueOf(permissionDto.getName()));
		permissionEntity.setModule(permissionDto.getModule());	
		permissionEntity.setType(permissionDto.getType());
	
		return permissionEntity;
	}

	
	public PermissionDto permissionEntityToPermissionDto(PermissionEntity permissionEntity) {
		
		PermissionDto permissionDto = new PermissionDto();
		
		permissionDto.setId(permissionEntity.getId());
		permissionDto.setName(permissionEntity.getName().toString());
		permissionDto.setModule(permissionEntity.getModule());
		permissionDto.setType(permissionEntity.getType());
		
		return permissionDto;
	}
	
	public List<PermissionEntity> permissionDtoListToPermissionEntityList(List<PermissionDto> permissionDtoList){
		
		List<PermissionEntity> permissionEntityList = new ArrayList<PermissionEntity>();
		
		for (PermissionDto permissionDto : permissionDtoList) {
			permissionEntityList.add(permissionDtoToPermissionEntity(permissionDto));
		}
		
		return permissionEntityList;
	}

	public List<PermissionDto> permissionEntityListToPermissionDtoList(List<PermissionEntity> permissionEntityList){
		
		List<PermissionDto> permissionDtoList = new ArrayList<PermissionDto>();
		
		for (PermissionEntity permissionEntity : permissionEntityList) {
			permissionDtoList.add(permissionEntityToPermissionDto(permissionEntity));
		}
		
		return permissionDtoList;
	}
}
