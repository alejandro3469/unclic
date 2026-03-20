package mx.com.endtoend.domain.roles.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;

/**
 * Clase para validación de datos operativos en la gestion de roles de acceso al
 * sistema
 * 
 * @author ddcasas
 */
public class RoleBusinessValidator {

	private final static Logger LOG = LoggerFactory.getLogger(RoleBusinessValidator.class);

	public String validOperativeDataToCreate(RoleDto roleDto, RolePersistencePort rolePersistencePort) {
		LOG.info("INIT validOperativeDataToCreate()");
		String validations = "";

		validations = validRequiredFields(roleDto, validations);
		if (!validations.isEmpty())
			return validations;

		boolean existsByNameAndCompany = rolePersistencePort.existsByNameAndCompany(roleDto.getName(),
				roleDto.getCompanyCode());
		validations += existsByNameAndCompany ? " -ROL FOR COMPANY ALREADY EXISTS" : "";
		
		validations += validOperativePermissionList(roleDto.getPermissions(), validations);

		return validations;
	}

	public String validOperativeDataToUpdate(RoleDto roleDto, RolePersistencePort rolePersistencePort) {
		LOG.info("INIT validOperativeDataToUpdate()");
		String validations = "";

		validations = validRequiredFields(roleDto, validations);
		if (!validations.isEmpty())
			return validations;

		boolean existsByNameAndCompanyAndIdNot = rolePersistencePort.existsByNameAndCompanyAndIdNot(roleDto.getName(),
				roleDto.getCompanyCode(), roleDto.getId());
		validations += existsByNameAndCompanyAndIdNot ? " -ROL FOR COMPANY ALREADY EXISTS" : "";
		
		validations += validOperativePermissionList(roleDto.getPermissions(), validations);

		return validations;
	}

	/**
	 * Método para validación de los datos de entrada. No se permiten valores nulos
	 * o cadenas de texto vacias como validación inicla
	 * 
	 * @param roleDto
	 * @param validations
	 * @return String
	 */
	private String validRequiredFields(RoleDto roleDto, String validations) {
		validations += roleDto.getName() == null ? " -ROL NAME IS REQUIRED" : "";
		validations += roleDto.getName() != null ? roleDto.getName().isEmpty() ? " -ROL NAME IS REQUIRED" : "" : "";

		validations += roleDto.getDescription() == null ? " -ROL DESCRIPTION IS REQUIRED" : "";
		validations += roleDto.getDescription() != null
				? roleDto.getDescription().isEmpty() ? " -ROL DESCRIPTION IS REQUIRED" : ""
				: "";

		validations += roleDto.getCompanyCode() == null ? " -COMPANY CODE IS REQUIRED" : "";
		validations += roleDto.getCompanyCode() != null
				? roleDto.getCompanyCode().isEmpty() ? " -COMPANY CODE IS REQUIRED" : ""
				: "";

		validations += roleDto.getPermissions() == null ? " -PERMISSION LIST IS REQUIRED" : "";
		validations += roleDto.getPermissions() != null
				? roleDto.getPermissions().size() <= 0 ? " -PERMISSION LIST IS REQUIRED" : ""
				: "";
		return validations;
	}

	/**
	 * Método que evalua que la lista de permisos pertenescan a un mismo rol
	 * operacional.
	 * 
	 * @param permissionList
	 * @param validations
	 * @return
	 */
	private String validOperativePermissionList(List<PermissionDto> permissionList, String validations) {

		List<PermissionDto> permisionOperationalList = permissionList.stream()
				.filter(p -> p.getType().equals("OPERATIONAL")).collect(Collectors.toList());

		List<PermissionDto> permisionManagementList = permissionList.stream()
				.filter(p -> p.getType().equals("MANAGEMENT")).collect(Collectors.toList());

		validations += permisionOperationalList.size() > 0 && permisionManagementList.size() > 0
				? " -LIST OF INVALID PERMITS BY MIXED OPERATIONAL ROLE"
				: "";

		return validations;
	}
}
