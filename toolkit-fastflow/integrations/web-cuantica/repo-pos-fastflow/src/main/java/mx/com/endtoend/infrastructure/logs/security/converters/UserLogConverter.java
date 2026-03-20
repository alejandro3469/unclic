package mx.com.endtoend.infrastructure.logs.security.converters;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.logs.security.dto.UserSummaryLogDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.logs.security.entities.UserFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserPreviousStateEntity;

@Component
public class UserLogConverter {

	public UserLogEntity userSummaryToEntity(UserSummaryLogDto userSummaryLogDto) {

		UserLogEntity userLogEntity = new UserLogEntity();

		userLogEntity.setUser(userSummaryLogDto.getUser());
		userLogEntity.setUpdatedDate(userSummaryLogDto.getUpdatedDate());
		userLogEntity.setUserId(userSummaryLogDto.getUserId());
		userLogEntity.setUsername(userSummaryLogDto.getUsername());

		return userLogEntity;

	}

	public UserPreviousStateEntity userSavedToEntity(UserDto userSaved, Long userSummaryId) {

		UserPreviousStateEntity userPreviousStateEntity = new UserPreviousStateEntity();

		userPreviousStateEntity.setUserSummaryId(userSummaryId);
		userPreviousStateEntity.setUserNumber(userSaved.getUserNumber());
		userPreviousStateEntity.setName(userSaved.getName());
		userPreviousStateEntity.setFirstSurname(userSaved.getFirstSurname());
		userPreviousStateEntity.setSecondSurname(userSaved.getSecondSurname());
		userPreviousStateEntity.setEmail(userSaved.getEmail());
		userPreviousStateEntity.setPassword("********");
		userPreviousStateEntity.setBranch(userSaved.getBranch().getCode() + userSaved.getBranch().getName());
		userPreviousStateEntity.setEnabled(userSaved.isEnabled());
		userPreviousStateEntity.setSessionActive(userSaved.getIsSessionActive());
		userPreviousStateEntity.setRoles(userSaved.getRoles().stream()
				.map(rol -> "-Name: " + rol.getName() + " -ID: " + rol.getId()).collect(Collectors.joining(",")));
		userPreviousStateEntity.setIsConfigurationComplete(userSaved.getIsConfigurationComplete());

		return userPreviousStateEntity;
	}

	public UserFinalStateEntity userUpdatedToEntity(UserDto userUpdated, Long userSummaryId) {

		UserFinalStateEntity userFinalStateEntity = new UserFinalStateEntity();

		userFinalStateEntity.setUserSummaryId(userSummaryId);
		userFinalStateEntity.setUserNumber(userUpdated.getUserNumber());
		userFinalStateEntity.setName(userUpdated.getName());
		userFinalStateEntity.setFirstSurname(userUpdated.getFirstSurname());
		userFinalStateEntity.setSecondSurname(userUpdated.getSecondSurname());
		userFinalStateEntity.setEmail(userUpdated.getEmail());
		userFinalStateEntity.setPassword("********");
		userFinalStateEntity.setBranch(userUpdated.getBranch().getCode() + userUpdated.getBranch().getName());
		userFinalStateEntity.setEnabled(userUpdated.isEnabled());
		userFinalStateEntity.setSessionActive(userUpdated.getIsSessionActive());
		userFinalStateEntity.setRoles(userUpdated.getRoles().stream()
				.map(rol -> "-Name: " + rol.getName() + " -ID: " + rol.getId()).collect(Collectors.joining(",")));
		userFinalStateEntity.setIsConfigurationComplete(userUpdated.getIsConfigurationComplete());

		return userFinalStateEntity;
	}

}
