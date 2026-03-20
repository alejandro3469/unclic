package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.UserConfigurationDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.UserConfigurationEntity;

@Component
public class UserConfigurationConverter {

	public UserConfigurationDto userConfigurationEntityToUserConfigurationDto(UserConfigurationEntity userConfigurationEntity) {
		
		UserConfigurationDto userConfigurationDto = new UserConfigurationDto();
		
		userConfigurationDto.setId(userConfigurationEntity.getId());
		userConfigurationDto.setPercentageAuthorized(userConfigurationEntity.getPercentageAuthorized());
		userConfigurationDto.setAuthorizationCode(userConfigurationEntity.getAuthorizationCode());
		userConfigurationDto.setCreationDate(userConfigurationEntity.getCreationDate());
		userConfigurationDto.setUpdatedDate(userConfigurationEntity.getUpdatedDate());
		userConfigurationDto.setModifyBy(userConfigurationEntity.getModifyBy());
		
		return userConfigurationDto;
	}
	
	
	public UserConfigurationEntity userConfigurationDtoToUserConfigurationEntity(UserConfigurationDto userConfigurationDto) {
		
		UserConfigurationEntity userConfigurationEntity = new UserConfigurationEntity();
		
		userConfigurationEntity.setId(userConfigurationDto.getId());
		userConfigurationEntity.setPercentageAuthorized(userConfigurationDto.getPercentageAuthorized());
		userConfigurationEntity.setAuthorizationCode(userConfigurationDto.getAuthorizationCode());
		userConfigurationEntity.setCreationDate(userConfigurationDto.getCreationDate());
		userConfigurationEntity.setUpdatedDate(userConfigurationDto.getUpdatedDate());
		userConfigurationEntity.setModifyBy(userConfigurationDto.getModifyBy());
		
		
		return userConfigurationEntity;
	}
	
	public List<UserConfigurationDto> userConfigurationEntityListToUserConfigurationDtoList(List<UserConfigurationEntity> userConfigurationEntityList){
		List<UserConfigurationDto> userConfigurationDtoList = new ArrayList<UserConfigurationDto>();
		for (UserConfigurationEntity userConfigurationEntity : userConfigurationEntityList) {
			userConfigurationDtoList.add(userConfigurationEntityToUserConfigurationDto(userConfigurationEntity));
		}
		return userConfigurationDtoList;
	}
	
	public List<UserConfigurationEntity> userConfigurationDtoListToUserConfigurationEntityList(List<UserConfigurationDto> userConfigurationDtoList){
		List<UserConfigurationEntity> userConfigurationEntityList = new ArrayList<UserConfigurationEntity>();
		for (UserConfigurationDto userConfigurationDto : userConfigurationDtoList) {
			userConfigurationEntityList.add(userConfigurationDtoToUserConfigurationEntity(userConfigurationDto));
		}
		return userConfigurationEntityList;
	}
}

