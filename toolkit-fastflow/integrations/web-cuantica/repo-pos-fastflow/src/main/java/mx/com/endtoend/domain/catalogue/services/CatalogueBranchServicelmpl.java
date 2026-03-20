package mx.com.endtoend.domain.catalogue.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.bussiness.branch.CatalogueBranchFactory;
import mx.com.endtoend.domain.catalogue.bussiness.branch.CatalogueBranchInterface;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueBranchServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueBranchPersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueBranchServicelmpl implements CatalogueBranchServicePort {

	private CatalogueBranchPersistencePort catalogueBranchPersistencePort;

	public CatalogueBranchServicelmpl(CatalogueBranchPersistencePort catalogueBranchPersistencePort) {
		this.catalogueBranchPersistencePort = catalogueBranchPersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueBranchServicelmpl.class);

	private CatalogueBranchFactory branchFactory = new CatalogueBranchFactory();

	@Override
	public ResponseModel updateBranchAddressByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort,
			String method, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT updateBranchAddressByCompanyCode() ", idOperation));
		CatalogueBranchInterface catalogueBranch = branchFactory.getImplementationByCode(method,
				catalogueOracleServicePort);
		if (catalogueBranch == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		ResponseModel responseModel = catalogueBranch.updateBranchAddressByCompanyCode(catalogueBranchPersistencePort,
				companyCode, idOperation);
		return responseModel;
	}

}
