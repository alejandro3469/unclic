package mx.com.endtoend.domain.catalogue.ports.api;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueServicePort {

	ResponseModel getClientTypeByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getCoordinateByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getDelegationByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getFlatByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getHowToContactByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getStateByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getWorkTypeByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getCountryByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel updateCatalogsByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getAddressColony(String CompanyCode, String method,String cp,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getAddress(String CompanyCode, String method,String colony,String cp,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getAddressDelegation(String CompanyCode, String method,String state,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getStatus(String companyCode, Long id, String method, String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel createStatus(String companyCode, StatusDto statusDto, String method, String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getStatusListByCompanyCode(String companyCode, String method, String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getCfdi(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getFiscalRegimeCatalogeByCompanyCode(String CompanyCode, String method,String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
	
	ResponseModel getCategoryListByCode(String companyCode, String branchCode, String method, String categoryCode, String idOperation, CatalogueJdeServicePort catalogueOracleServicePort);
}
