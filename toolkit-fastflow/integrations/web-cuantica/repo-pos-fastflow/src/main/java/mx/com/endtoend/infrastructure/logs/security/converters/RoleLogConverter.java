package mx.com.endtoend.infrastructure.logs.security.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.logs.security.dto.RoleSummaryLogDto;
import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.infrastructure.logs.security.entities.PermissionFinalStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.PermissionPreviosStateEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RoleFinalStatusEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RoleLogEntity;
import mx.com.endtoend.infrastructure.logs.security.entities.RolePreviousStateEntity;

@Component
public class RoleLogConverter {

	public RoleLogEntity roleSummarLogToEntity(RoleSummaryLogDto reRoleSummaryLogDto) {

		RoleLogEntity roleLogEntity = new RoleLogEntity();

		roleLogEntity.setUser(reRoleSummaryLogDto.getUser());
		roleLogEntity.setUpdatedDate(reRoleSummaryLogDto.getUpdatedDate());
		roleLogEntity.setRoleName(reRoleSummaryLogDto.getRoleName());
		roleLogEntity.setRoleId(reRoleSummaryLogDto.getRoleId());

		return roleLogEntity;
	}

	public RolePreviousStateEntity roleSavedToEntity(RoleDto roleSaved, Long roleSummaryId) {

		RolePreviousStateEntity rolePreviousStateEntity = new RolePreviousStateEntity();

		rolePreviousStateEntity.setRoleSummaryId(roleSummaryId);
		rolePreviousStateEntity.setName(roleSaved.getName());
		rolePreviousStateEntity.setDescription(roleSaved.getDescription());
		rolePreviousStateEntity.setCompanyKey(roleSaved.getCompanyCode());
		rolePreviousStateEntity.setEnabled(roleSaved.getEnabled());

		return rolePreviousStateEntity;

	}

	public PermissionPreviosStateEntity premissionSavedToEntity(PermissionDto permissionDto, Long rolePrevId) {

		PermissionPreviosStateEntity permissionPreviosStateEntity = new PermissionPreviosStateEntity();

		permissionPreviosStateEntity.setRolePrevId(rolePrevId);
		permissionPreviosStateEntity.setName(permissionDto.getName());
		permissionPreviosStateEntity.setModule(permissionDto.getModule());
		permissionPreviosStateEntity.setType(permissionDto.getType());

		return permissionPreviosStateEntity;

	}

	public RoleFinalStatusEntity roleUpdatedToEntity(RoleDto roleUpdated, Long roleSummaryId) {

		RoleFinalStatusEntity roleFinalStatusEntity = new RoleFinalStatusEntity();

		roleFinalStatusEntity.setRoleSummaryId(roleSummaryId);
		roleFinalStatusEntity.setName(roleUpdated.getName());
		roleFinalStatusEntity.setDescription(roleUpdated.getDescription());
		roleFinalStatusEntity.setCompanyKey(roleUpdated.getCompanyCode());
		roleFinalStatusEntity.setEnabled(roleUpdated.getEnabled());

		return roleFinalStatusEntity;

	}
	
	public PermissionFinalStateEntity premissionUpdatedToEntity(PermissionDto permissionDto, Long roleFinalId) {

		PermissionFinalStateEntity permissionFinalStateEntity = new PermissionFinalStateEntity();

		permissionFinalStateEntity.setRoleFinalId(roleFinalId);
		permissionFinalStateEntity.setName(permissionDto.getName());
		permissionFinalStateEntity.setModule(permissionDto.getModule());
		permissionFinalStateEntity.setType(permissionDto.getType());

		return permissionFinalStateEntity;

	}

}
