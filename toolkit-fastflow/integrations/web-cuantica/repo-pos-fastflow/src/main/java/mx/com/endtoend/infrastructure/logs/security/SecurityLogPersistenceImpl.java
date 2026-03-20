package mx.com.endtoend.infrastructure.logs.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import mx.com.endtoend.domain.logs.security.dto.EmployeeSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.RoleSummaryLogDto;
import mx.com.endtoend.domain.logs.security.dto.UserSummaryLogDto;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogPersistencePort;
import mx.com.endtoend.infrastructure.logs.security.converters.EmployeeLogConverter;
import mx.com.endtoend.infrastructure.logs.security.converters.RoleLogConverter;
import mx.com.endtoend.infrastructure.logs.security.converters.UserLogConverter;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeeFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeeLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.EmployeePreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.PermissionFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.PermissionPreviosStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RoleFinalStatusEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RoleLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RolePreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserConfigurationFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserConfigurationPrevStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.UserPreviousStateEntity;
import mx.com.endtoend.infrastructure.logs.security.repositories.EmployeeFinalStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.EmployeeLogRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.EmployeePreviousStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.PermissionFinalStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.PermissionPreviosStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.RoleFinalStatusRepoitory;
import mx.com.endtoend.infrastructure.logs.security.repositories.RoleLogRepoitory;
import mx.com.endtoend.infrastructure.logs.security.repositories.RolePreviousStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.UserConfigurationFinalStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.UserConfigurationPrevStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.UserFinalStateRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.UserLogRepository;
import mx.com.endtoend.infrastructure.logs.security.repositories.UserPreviousStateRepository;

public class SecurityLogPersistenceImpl implements SecurityLogPersistencePort {

	@Autowired
	private RoleLogConverter roleLogConverter;

	@Autowired
	private RoleLogRepoitory roleLogRepoitory;

	@Autowired
	private RolePreviousStateRepository rolePreviousStateRepository;

	@Autowired
	private PermissionPreviosStateRepository permissionPreviosStateRepository;

	@Autowired
	private RoleFinalStatusRepoitory roleFinalStatusRepoitory;

	@Autowired
	private PermissionFinalStateRepository permissionFinalStateRepository;

	@Autowired
	private UserLogConverter userLogConverter;

	@Autowired
	private UserLogRepository userLogRepository;

	@Autowired
	private UserPreviousStateRepository userPreviousStateRepository;

	@Autowired
	private UserFinalStateRepository userFinalStateRepository;

	@Autowired
	private EmployeeLogConverter employeeLogConverter;

	@Autowired
	private EmployeeLogRepository employeeLogRepository;

	@Autowired
	private EmployeePreviousStateRepository employeePreviousStateRepository;

	@Autowired
	private EmployeeFinalStateRepository employeeFinalStateRepository;

	@Autowired
	private UserConfigurationPrevStateRepository userConfigurationPrevStateRepository;

	@Autowired
	private UserConfigurationFinalStateRepository userConfigurationFinalStateRepository;

	@Async
	@Transactional
	@Override
	public void saveRoleLog(RoleSummaryLogDto roleSummaryLogDto) {

		RoleLogEntity roleLogEntity = roleLogConverter.roleSummarLogToEntity(roleSummaryLogDto);
		roleLogEntity = roleLogRepoitory.save(roleLogEntity);

		Long roleLogId = roleLogEntity.getId();

		RolePreviousStateEntity rolePreviousStateEntity = roleLogConverter
				.roleSavedToEntity(roleSummaryLogDto.getRoleSaved(), roleLogId);
		rolePreviousStateEntity = rolePreviousStateRepository.save(rolePreviousStateEntity);

		Long rolePreviousStateId = rolePreviousStateEntity.getId();

		List<PermissionPreviosStateEntity> permissionPreviosStateEntities = roleSummaryLogDto.getRoleSaved()
				.getPermissions().stream()
				.map(permissionDto -> roleLogConverter.premissionSavedToEntity(permissionDto, rolePreviousStateId))
				.collect(Collectors.toList());
		permissionPreviosStateRepository.saveAll(permissionPreviosStateEntities);

		RoleFinalStatusEntity roleFinalStatusEntity = roleLogConverter
				.roleUpdatedToEntity(roleSummaryLogDto.getRoleUpdated(), roleLogEntity.getId());
		roleFinalStatusEntity = roleFinalStatusRepoitory.save(roleFinalStatusEntity);
		Long roleFinalStatusId = roleFinalStatusEntity.getId();

		List<PermissionFinalStateEntity> permissionFinalStateEntities = roleSummaryLogDto.getRoleUpdated()
				.getPermissions().stream()
				.map(permissionDto -> roleLogConverter.premissionUpdatedToEntity(permissionDto, roleFinalStatusId))
				.collect(Collectors.toList());
		permissionFinalStateRepository.saveAll(permissionFinalStateEntities);
	}

	@Async
	@Transactional
	@Override
	public void saveUserLog(UserSummaryLogDto userSummaryLogDto) {

		UserLogEntity userLogEntity = userLogConverter.userSummaryToEntity(userSummaryLogDto);
		userLogEntity = userLogRepository.save(userLogEntity);
		Long userLogId = userLogEntity.getId();

		UserPreviousStateEntity userPreviousStateEntity = userLogConverter
				.userSavedToEntity(userSummaryLogDto.getUserSaved(), userLogId);
		userPreviousStateRepository.save(userPreviousStateEntity);

		UserFinalStateEntity userFinalStateEntity = userLogConverter
				.userUpdatedToEntity(userSummaryLogDto.getUserUpdated(), userLogId);
		userFinalStateRepository.save(userFinalStateEntity);

	}

	@Transactional
	@Override
	public void saveUserConfigurationLog(EmployeeSummaryLogDto employeeSummaryLog) {

		EmployeeLogEntity employeeLogEntity = employeeLogConverter.employeeSummaryToEntity(employeeSummaryLog);
		employeeLogEntity = employeeLogRepository.save(employeeLogEntity);
		Long employeeSummaryId = employeeLogEntity.getId();

		EmployeePreviousStateEntity employeePreviousStateEntity = employeeLogConverter
				.employeeSavedSummaryToEntity(employeeSummaryLog.getEmployeeSaved(), employeeSummaryId);
		employeePreviousStateEntity = employeePreviousStateRepository.save(employeePreviousStateEntity);
		Long employeePrevId = employeePreviousStateEntity.getId();

		UserConfigurationPrevStateEntity userConfigurationPrevStateEntity = employeeLogConverter
				.userConfigurationSavedToEntity(employeeSummaryLog.getEmployeeSaved().getUserConfiguration(),
						employeePrevId);
		userConfigurationPrevStateRepository.save(userConfigurationPrevStateEntity);

		EmployeeFinalStateEntity employeeFinalStateEntity = employeeLogConverter
				.employeeUpdatedSummaryToEntity(employeeSummaryLog.getEmployeeUpdated(), employeeSummaryId);
		employeeFinalStateEntity = employeeFinalStateRepository.save(employeeFinalStateEntity);
		Long employeeFinalId = employeeFinalStateEntity.getId();

		UserConfigurationFinalStateEntity configurationFinalStateEntity = employeeLogConverter
				.userConfigurationFinalToEntity(employeeSummaryLog.getEmployeeUpdated().getUserConfiguration(),
						employeeFinalId);
		userConfigurationFinalStateRepository.save(configurationFinalStateEntity);

	}

}
