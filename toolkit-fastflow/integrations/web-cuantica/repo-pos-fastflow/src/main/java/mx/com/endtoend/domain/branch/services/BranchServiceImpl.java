package mx.com.endtoend.domain.branch.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.api.BranchServicePort;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.ValidationError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */

public class BranchServiceImpl implements BranchServicePort {

	private BranchPersistencePort branchPersistencePort;

	public BranchServiceImpl(BranchPersistencePort branchPersistencePort) {
		this.branchPersistencePort = branchPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(BranchServiceImpl.class);

	@Override
	public ResponseModel createBranch(BranchDto branchDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT createBranch() ", idOperation));
		ResponseModel responseFromPersistencePort = new ResponseModel();
		LOG.info(String.format("%s VALID BRANCH NAME", idOperation));
		boolean existsByName = (boolean) branchPersistencePort
				.existsNameByCompanyCode(branchDto.getName(), companyCode, idOperation).getData();
		if (existsByName) {
			LOG.warn(String.format("%s THE NAME: %s BRANCH ALREADY EXISTS", idOperation, branchDto.getName()));
			throw new ValidationError("NAME ALREADY EXISTS");
		}
		LOG.info(String.format("%s VALID BRANCH CODE", idOperation));
		boolean existsByCode = (boolean) branchPersistencePort
				.existsCodeByCompanyCode(branchDto.getCode(), companyCode, idOperation).getData();
		if (existsByCode) {
			LOG.warn(String.format("%s THE CODE: %s BRANCH ALREADY EXISTS", idOperation, branchDto.getCode()));
			throw new ValidationError("CODE ALREADY EXISTS");
		}
		responseFromPersistencePort = branchPersistencePort.createBranch(branchDto, companyCode, idOperation);
		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel updateBranch(BranchDto branchDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateBranch() ", idOperation));
		ResponseModel responseFromPersistencePort = new ResponseModel();
		LOG.info(String.format("%s VALID BRANCH NAME", idOperation));
		boolean existsByNameAndIdNot = (boolean) branchPersistencePort
				.existsNameByCompanyCodeAndIdNot(branchDto.getName(), companyCode, branchDto.getId(), idOperation)
				.getData();
		if (existsByNameAndIdNot) {
			LOG.warn(String.format("%s THE NAME: %s BRANCH ALREADY EXISTS", idOperation, branchDto.getName()));
			throw new ValidationError("NAME ALREADY EXISTS");

		}
		LOG.info(String.format("%s VALID BRANCH CODE", idOperation));
		boolean existsByCodeAndIdNot = (boolean) branchPersistencePort
				.existsCodeByCompanyCodeAndIdNot(branchDto.getCode(), companyCode, branchDto.getId(), idOperation)
				.getData();
		if (existsByCodeAndIdNot) {
			LOG.warn(String.format("%s THE CODE: %s BRANCH ALREADY EXISTS", idOperation, branchDto.getCode()));
			throw new ValidationError("CODE ALREADY EXISTS");
		}
		responseFromPersistencePort = branchPersistencePort.updateBranch(branchDto, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getBranchListByCompanyCode() ", idOperation));
		ResponseModel responseFromPersistencePort = new ResponseModel();
		responseFromPersistencePort = branchPersistencePort.getBranchListByCompanyCode(companyCode, idOperation);
		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel findById(Long id, String idOperation) {
		LOG.info(String.format("%s INIT findById() ", idOperation));
		ResponseModel responseFromPersistencePort = new ResponseModel();
		responseFromPersistencePort = branchPersistencePort.getBranchById(id, idOperation);
		return responseFromPersistencePort;
	}

}