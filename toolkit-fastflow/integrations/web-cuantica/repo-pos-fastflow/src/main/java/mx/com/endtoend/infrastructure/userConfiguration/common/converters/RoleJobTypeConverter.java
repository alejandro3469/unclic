package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.RoleJobTypeDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.RoleJobTypeEntity;

@Component
public class RoleJobTypeConverter {

	public RoleJobTypeDto roleJobTypeEntityToRoleJobTypeDto(RoleJobTypeEntity roleJobTypeEntity) {
		
		RoleJobTypeDto roleJobTypeDto = new RoleJobTypeDto();
		
		roleJobTypeDto.setId(roleJobTypeEntity.getId());
		roleJobTypeDto.setCode(roleJobTypeEntity.getCode());
		roleJobTypeDto.setName(roleJobTypeEntity.getName());
		
		return roleJobTypeDto;
	}
	
	public RoleJobTypeEntity roleJobTypeDtoToRoleJobTypeEntity(RoleJobTypeDto roleJobTypeDto) {
		
		RoleJobTypeEntity roleJobTypeEntity = new RoleJobTypeEntity();
		
		roleJobTypeEntity.setId(roleJobTypeDto.getId());
		roleJobTypeEntity.setCode(roleJobTypeDto.getCode());
		roleJobTypeEntity.setName(roleJobTypeDto.getName());
		
		return roleJobTypeEntity;
	} 
	
	public List<RoleJobTypeDto> roleJobTypeEntityListToRoleJobTypeDtoList(List<RoleJobTypeEntity> roleJobTypeEntityList){
		List<RoleJobTypeDto> roleJobTypeDtoList = new ArrayList<RoleJobTypeDto>();
		for (RoleJobTypeEntity roleJobTypeEntity : roleJobTypeEntityList) {
			roleJobTypeDtoList.add(roleJobTypeEntityToRoleJobTypeDto(roleJobTypeEntity));
		}
		return roleJobTypeDtoList; 
	}
	
	public List<RoleJobTypeEntity> roleJobTypeDtoListToRoleJobTypeEntityList(List<RoleJobTypeDto> roleJobTypeDtoList){
		List<RoleJobTypeEntity> roleJobTypeEntityList = new ArrayList<RoleJobTypeEntity>();
		for (RoleJobTypeDto roleJobTypeDto : roleJobTypeDtoList) {
			roleJobTypeEntityList.add(roleJobTypeDtoToRoleJobTypeEntity(roleJobTypeDto));
		}
		return roleJobTypeEntityList; 
	}

}
