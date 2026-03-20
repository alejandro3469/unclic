package mx.com.endtoend.domain.catalogue.ports.api;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueBranchServicePort {

	ResponseModel updateBranchAddressByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);
}
