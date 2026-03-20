package mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueJdeServicePort {

	ResponseModel getBrancgAddressByCode(String branchCode, String companyCode, String idOperation);

	ResponseModel getClientTypeByCompanyCode(String companyCode, String idOperation);

	ResponseModel getFiscalRegimeCatalogeByCompanyCode(String companyCode, String idOperation);

	ResponseModel getHowToContactByCompanyCode(String companyCode, String idOperation);

	ResponseModel getCfdi(String companyCode, String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String companyCode, String idOperation);

	ResponseModel getArticleBrandByCompanyCode(String companyCode, String idOperation);

	ResponseModel getArticleCategoryByCompanyCode(String companyCode, String idOperation);

	ResponseModel getArticleFamilyByCompanyCode(String companyCode, String idOperation);

	ResponseModel getArticleDivisionByCompanyCode(String companyCode, String idOperation);

	ResponseModel getCoordinateByCompanyCode(String companyCode, String idOperation);

	ResponseModel getFlatByCompanyCode(String companyCode, String idOperation);

	ResponseModel getCountryByCompanyCode(String companyCode, String idOperation);

	ResponseModel getStateByCompanyCode(String companyCode, String idOperation);

	ResponseModel getDelegationByCompanyCode(String companyCode, String idOperation);

	ResponseModel getColonyByCompanyCodeAndStateCode(String companyCode, String stateCode, String idOperation);

	ResponseModel getMunicipalityNameListByStateCode(String companyCode, String stateCode, String idOperation);

	ResponseModel getMunicipalityByNameListAndCompanyCode(List<String> municipalityNameList, String stateCode,
			String companyCode, String idOperation);

	ResponseModel getMunicipalityCpListBySatetCodeAndCompanyCode(String stateCode, String companyCode,
			String idOperation);

	void updateCatalogsByCompanyCode(String companyCode, String idOperation);

	ResponseModel getAddressColony(String companyCode, String cp, String idOperation);

	ResponseModel getAddress(String companyCode, String colony, String cp, String idOperation);

	ResponseModel getAddressDelegation(String companyCode, String state, String idOperation);

	ResponseModel getStatusListByCompanyCode(String companyCode, String idOperation);

	ResponseModel getCategoryListByCompanyCodeAndCategoryCode(String companyCode, String categoryCode,
			String idOperation);
}
