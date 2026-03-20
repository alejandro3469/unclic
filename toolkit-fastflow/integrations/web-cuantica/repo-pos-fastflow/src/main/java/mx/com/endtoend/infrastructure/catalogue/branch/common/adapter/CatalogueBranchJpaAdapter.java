package mx.com.endtoend.infrastructure.catalogue.branch.common.adapter;

import mx.com.endtoend.infrastructure.catalogue.branch.common.repository.GenericCatalogueBranchRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.common.factory.CatalogueBranchFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueBranchPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class CatalogueBranchJpaAdapter implements CatalogueBranchPersistencePort {

	@Autowired
	private CatalogueBranchFactory catalogueBranchFactory;

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueBranchJpaAdapter.class);

	@Override
	public ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getBranchListByCompanyCode()", idOperation));
		GenericCatalogueBranchRepository repository = catalogueBranchFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.getBranchListByCompanyCode(companyCode, idOperation);
		return responseModel;
	}

	@Override
	public ResponseModel updateBranchByCompanyCode(BranchDto branchDto, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateBranchByCompanyCode()", idOperation));
		GenericCatalogueBranchRepository repository = catalogueBranchFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = repository.updateBranchByCompanyCode(branchDto, companyCode, idOperation);
		return responseModel;
	}

}
