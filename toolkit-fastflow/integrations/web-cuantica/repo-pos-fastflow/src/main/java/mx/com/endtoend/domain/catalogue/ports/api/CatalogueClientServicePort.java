package mx.com.endtoend.domain.catalogue.ports.api;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueClientServicePort {

	ResponseModel getCfdiByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getRegimeFiscalCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getClientTypeCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getContectMethodCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel getWorkTypeByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation);

	ResponseModel updateCatalogueCompanyCodeAndCatalogueType(CatalogueJdeServicePort catalogueOracleServicePort,
			String method, String companyCode, String catalogueType, String idOperation);

}
