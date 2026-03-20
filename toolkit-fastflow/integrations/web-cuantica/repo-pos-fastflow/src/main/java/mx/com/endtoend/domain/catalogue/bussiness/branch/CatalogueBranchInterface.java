package mx.com.endtoend.domain.catalogue.bussiness.branch;

import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueBranchPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueBranchInterface {

	ResponseModel updateBranchAddressByCompanyCode(CatalogueBranchPersistencePort catalogueBranchPersistencePort,
			String companyCode, String idOperation);

}
