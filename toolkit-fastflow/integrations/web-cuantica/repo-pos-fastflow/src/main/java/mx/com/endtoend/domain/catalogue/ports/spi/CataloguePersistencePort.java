package mx.com.endtoend.domain.catalogue.ports.spi;

import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CataloguePersistencePort {

	ResponseModel getClientTypeByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getCoordinateByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getDelegationByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getFlatByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getHowToContactByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getStateByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getCountryByCompanyCode(String companyCode, String method, String idOperation);

//	ResponseModel getAddressColony(String companyCode, String method, String cp, String idOperation);
//
//	ResponseModel getAddress(String companyCode, String method, String colony, String cp, String idOperation);
//
//	ResponseModel getAddressDelegation(String companyCode, String method, String state, String idOperation);

	ResponseModel getStatus(String companyCode, Long id, String method, String idOperation);

	void createStatus(String companyCode, StatusDto statusDto, String method, String idOperation);

	boolean existsStatus(StatusDto statusDto, String companyCode, String method, String idOperation);

	ResponseModel getCfdi(String companyCode, String method, String idOperation);

	ResponseModel getFiscalRegimeCatalogeByCompanyCode(String companyCode, String method, String idOperation);
	
	ResponseModel getStatusListByCompanyCode(String companyCode, String method, String idOperation);

	ResponseModel getCategoryListByCompanyCodeAndCategoryCode(String companyCode,String method, String categoryCode,
			String idOperation);
	
//	void updateCatalogsByCompanyCode(String companyCode, String method, String idOperation);
}
