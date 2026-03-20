package mx.com.endtoend.infrastructure.users.common.converters;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.java.Log;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.roles.common.converters.RoleConverter;
import mx.com.endtoend.infrastructure.roles.common.entities.RoleEntity;

@Log
@Component
public class UserConverter {

	@Autowired
	private BranchConverter branchConverter;

	@Autowired
	private RoleConverter roleConverter;

	public UserDto userEntityToUserDto(UserEntity userEntity, boolean excludePassword) {

		UserDto userDto = new UserDto();

		userDto.setId(userEntity.getId());
		userDto.setUserNumber(userEntity.getUserNumber());
		userDto.setName(userEntity.getName());
		userDto.setFirstSurname(userEntity.getFirstSurname());
		userDto.setSecondSurname(userEntity.getSecondSurname());
		userDto.setEmail(userEntity.getEmail());
		userDto.setIsSessionActive(userEntity.isSessionActive());
		userDto.setEnabled(userEntity.isEnabled());
		userDto.setIsConfigurationComplete(userEntity.isConfigurationComplete());

		if (!excludePassword) {

			userDto.setPassword(userEntity.getPassword());
		}

		userDto.setBranch(branchConverter.branchEntityToBranchDto(userEntity.getBranch()));

		try {

			List<RoleEntity> roleEntities = new ArrayList<>(userEntity.getRoles());
			userDto.setRoles(roleConverter.roleEntityListToRoleDtoList(roleEntities));

		} catch (Exception e) {

			log.info(e.toString());
		}

		return userDto;
	}

	public UserEntity userDtoToUserEntity(UserDto userDto, boolean excludePassword) {

		UserEntity userEntity = new UserEntity();

		userEntity.setId(userDto.getId());
		userEntity.setUserNumber(userDto.getUserNumber());
		userEntity.setName(userDto.getName());
		userEntity.setFirstSurname(userDto.getFirstSurname());
		userEntity.setSecondSurname(userDto.getSecondSurname());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setSessionActive(userDto.getIsSessionActive());
		userEntity.setEnabled(userDto.isEnabled());
		userEntity.setConfigurationComplete(userDto.getIsConfigurationComplete());

		if (!excludePassword) {
			
			userEntity.setPassword(userDto.getPassword());
		}
		
		userEntity.setBranch(branchConverter.branchDtoToBranchEntity(userDto.getBranch()));
		
		return userEntity;
	}

	public List<UserEntity> userDtoListToUserEntityList(List<UserDto> userDtoList, boolean excludePassword) {

		List<UserEntity> userEntityList = new ArrayList<UserEntity>();

		for (UserDto userDto : userDtoList) {
			userEntityList.add(userDtoToUserEntity(userDto, excludePassword));
		}

		return userEntityList;
	}

	public List<UserDto> userEntityListToUserDtoList(List<UserEntity> userEntityList, boolean excludePassword) {

		List<UserDto> userDtoList = new ArrayList<UserDto>();

		for (UserEntity userEntity : userEntityList) {
			userDtoList.add(userEntityToUserDto(userEntity, excludePassword));
		}

		return userDtoList;
	}

}
