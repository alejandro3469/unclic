package mx.com.endtoend.domain.roles.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.roles.dto.PermissionDto;
import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.roles.ports.spi.PermissionPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class PermissionServiceImpl implements PermissionServicePort {

	private PermissionPersistencePort permissionPersistencePort;

	private final static Logger LOG = LoggerFactory.getLogger(PermissionServiceImpl.class);

	public PermissionServiceImpl(PermissionPersistencePort permissionPersistencePort) {
		this.permissionPersistencePort = permissionPersistencePort;
	}

	@Override
	public PermissionDto create(PermissionDto permissionDto) {

		LOG.info(String.format("INIT create()"));

		LOG.info(String.format("PARAMS: [permissionDto: %s ]", permissionDto.toString()));

		permissionDto = permissionPersistencePort.create(permissionDto);

		if (permissionDto != null) {
			return permissionDto;
		} else {
			return null;
		}
	}

	@Override
	public PermissionDto findByName(String name) {

		LOG.info(String.format("INIT findByName()"));

		LOG.info(String.format("PARAMS: [name: %s ]", name));

		PermissionDto permissionDto = permissionPersistencePort.findByName(name);

		if (permissionDto != null) {
			return permissionDto;
		} else {
			return null;
		}
	}

	@Override
	public List<PermissionDto> findAllByUserId(Long id) {

		LOG.info(String.format("INIT findAllByUserId()"));

		LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));

		List<PermissionDto> permissionDtoList = permissionPersistencePort.findAllByUserId(id);
		return permissionDtoList;
	}

	@Override
	public ResponseModel findAllByRoleId(Long id) {

		LOG.info(String.format("INIT findAllByRoleId()"));

		LOG.info(String.format("PARAMS: [id: %s ]", id.toString()));

		ResponseModel responseModel = new ResponseModel();
		List<PermissionDto> permissionDtoList = permissionPersistencePort.findAllByRoleId(id);
		if (permissionDtoList != null) {
			responseModel = new ResponseModel(permissionDtoList);
		} else {
			throw new GlobalError();
		}
		return responseModel;
	}

	@Override
	public ResponseModel findAllByClient() {
		LOG.info(String.format("INIT findAllByClient()"));
		ResponseModel responseModel = new ResponseModel();
		List<PermissionDto> permissionDtoList = permissionPersistencePort.findAllExceptModule("ETE");
		if (permissionDtoList != null) {
			responseModel = new ResponseModel(permissionDtoList);
		} else {
			List<PermissionDto> permissionDtoListEmpty = new ArrayList<>();
			responseModel = new ResponseModel(permissionDtoListEmpty);
		}
		return responseModel;
	}

	@Override
	public ResponseModel findAllByAdmin() {
		LOG.info(String.format("INIT findAllByAdmin()"));
		ResponseModel responseModel = new ResponseModel();
		List<PermissionDto> permissionDtoList = permissionPersistencePort.findAllByModule("ETE");
		if (permissionDtoList != null) {
			responseModel = new ResponseModel(permissionDtoList);
		} else {
			List<PermissionDto> permissionDtoListEmpty = new ArrayList<>();
			responseModel = new ResponseModel(permissionDtoListEmpty);
		}
		return responseModel;
	}

	@Override
	public ResponseModel findAllByCompanyCode(String companyCode) {
		LOG.info(String.format("INIT findAllByCompanyCode()"));
		List<PermissionDto> permissionDtoList = permissionPersistencePort.findAllByCompanyCode(companyCode);
		if (permissionDtoList == null)
			return new ResponseModel(new ArrayList<>());
		return new ResponseModel(permissionDtoList);
	}

}