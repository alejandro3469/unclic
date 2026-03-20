package mx.com.endtoend.domain.catalogue.ports.api;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueArticleServicePort {
	
	ResponseModel getBrandByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getCategoryByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getDivisionByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getFamilyByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(CatalogueJdeServicePort catalogueOracleServicePort,
			String method, String companyCode, String catalogueType, String idOperation);

}
