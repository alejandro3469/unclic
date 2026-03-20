package mx.com.endtoend.domain.company.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.company.ports.spi.CompanyPersistencePort;
import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.domain.roles.dto.RoleDto;
import mx.com.endtoend.domain.roles.ports.spi.RolePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.constants.CompanyCodes;

/**
 * 
 * @author labucio, ddcasas
 *
 */

public class CompanyServicelmpl implements CompanyServicePort {

	private CompanyPersistencePort companyPersistencePort;

	public CompanyServicelmpl(CompanyPersistencePort companyPersistencePort) {
		this.companyPersistencePort = companyPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CompanyServicelmpl.class);

	private CompanyValidation companyValidation = new CompanyValidation();

	@Override
	public ResponseModel saveCompany(CompanyDto companyDto, String idOperation) {
		LOG.info(String.format("%s INIT VALID OPERATIVE DATA", idOperation));
		String validations = companyValidation.validOperativeDataToCreate(companyPersistencePort, companyDto,
				idOperation);
		if (!validations.isEmpty())
			throw new ValidationError(validations);
		ResponseModel responseCompanyCreated = companyPersistencePort.saveToModifyCompany(companyDto, idOperation);
		CompanyDto companyCreated = (CompanyDto) responseCompanyCreated.getData();
		companyDto.setId(companyCreated.getId());
		companyPersistencePort.savePermissionToCompany(companyDto, idOperation);
		return responseCompanyCreated;

	}

	@Override
	public ResponseModel modifyCompany(CompanyDto companyDto, RolePersistencePort rolePersistencePort,
			String idOperation) {
		LOG.info(String.format("%s INIT VALID OPERATIVE DATA", idOperation));
		String validations = companyValidation.validOperativeDataToUpdate(companyPersistencePort, companyDto,
				idOperation);
		if (!validations.isEmpty())
			throw new ValidationError(validations);

		ResponseModel getCompanySaved = companyPersistencePort.findById(companyDto.getId(), idOperation);
		CompanyDto companySaved = (CompanyDto) getCompanySaved.getData();

		ResponseModel responseFromPersistencePort = companyPersistencePort.saveToModifyCompany(companyDto, idOperation);
		companyPersistencePort.updatePermissionCompanyList(companyDto, idOperation);

		updateRoleCompanyList(rolePersistencePort, companySaved, companyDto);

		return responseFromPersistencePort;
	}

	/**
	 * Método para actualizar la lista de permisos en los roles de cada compañía.
	 * Por cada permiso que se retire de la compañía se retirará tambien de los
	 * roles configurados.
	 * 
	 * @param rolePersistencePort Puerto de la capa de persistencia para el control
	 *                            de roles/accesos
	 * @param companySaved        Objeto del estado previo a la actualización de la
	 *                            compañía
	 * @param companyDto          Objeto del estado nuevo de la compañía
	 */
	private void updateRoleCompanyList(RolePersistencePort rolePersistencePort, CompanyDto companySaved,
			CompanyDto companyDto) {

		List<PermissionDto> permissionDeletedList = companySaved.getPermissions().stream()
				.filter(ps -> !companyDto.getPermissions().contains(ps))
				.collect(Collectors.toList());
		
		List<RoleDto> roleEnableList = rolePersistencePort.findAllByEnabledAndCompany(true, companySaved.getCode());
		roleEnableList.stream().forEach((role) -> {
			List<PermissionDto> updatePermssionList = role.getPermissions().stream()
					.filter(pr -> !permissionDeletedList.contains(pr))
					.collect(Collectors.toList());
			role.setPermissions(updatePermssionList);
			rolePersistencePort.update(role);
			
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel companyList(String idOperation, String companyCode) {
		ResponseModel responseFromPersistencePort = companyPersistencePort.companyList(idOperation);
		List<CompanyDto> companyList = new ArrayList<>();
		if (!companyCode.equals(CompanyCodes.ETE.toString())) {
			for (CompanyDto companyDto : (List<CompanyDto>) responseFromPersistencePort.getData()) {
				if (companyDto.getCode().equals(companyCode))
					companyList.add(companyDto);
			}
		} else {
			companyList = (List<CompanyDto>) responseFromPersistencePort.getData();
		}
		return new ResponseModel(companyList);
	}

	@Override
	public ResponseModel findCompanyByCode(String companyCode, String idOperation) {
		ResponseModel responseFromPersistencePort = companyPersistencePort.findByCode(companyCode, idOperation);
		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel findAllMethodByModule(String module, String idOperation) {
		ResponseModel responseFromPersistencePort = companyPersistencePort.findAllByModule(module, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel findById(Long id, String idOperation) {
		ResponseModel responseFromPersistencePort = companyPersistencePort.findById(id, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel findMethodByCompanyCodeAndModule(String companyCode, String module, String idOperation) {
		ResponseModel responseFromPersistencePort = companyPersistencePort.findByCompanyCodeAndModule(companyCode,
				module, idOperation);
		return responseFromPersistencePort;
	}

}
