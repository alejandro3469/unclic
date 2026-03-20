package mx.com.endtoend.infrastructure.services.jde.catalogue.common.repository;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericCatalogueJdeRepository {

	ResponseModel getBrancgAddressByCode(String branchCode, String idOperation);

	ResponseModel getClientType(String idOperation);

	ResponseModel getFiscalRegimeCatalogue(String idOperation);

	ResponseModel getHowToContactByCompanyCode(String idOperation);

	ResponseModel getCfdi(String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String idOperation);

	ResponseModel getArticleBrand(String idOperation);

	ResponseModel getArticleCategory(String idOperation);

	ResponseModel getArticleFamily(String idOperation);

	ResponseModel getArticleDivision(String idOperation);

	ResponseModel getCoordinate(String idOperation);

	ResponseModel getFlat(String idOperation);

	ResponseModel getCountry(String idOperation);

	ResponseModel getState(String idOperation);

	ResponseModel getColonyByStateCode(String stateCode, String idOperation);

	ResponseModel getMunicipalityNameListByStateCode(String stateCode, String idOperation);

	ResponseModel getMunicipalityByNameList(List<String> municipalityNameList, String stateCode, String idOperation);

	ResponseModel getMunicipalityCpListBySatetCode(String stateCode, String idOperation);

	CatalogueEntity getCatalogByCode(CatalogueDto catalog, String idOperation);

	List<CatalogueDto> getCatalogueClientType();

	List<CatalogueDto> getCatalogueCoordinate();

	List<CatalogueDto> getCatalogueDelegation();

	List<CatalogueDto> getCatalogueFlat();

	List<CatalogueDto> getCatalogueHowToContact();

	List<CatalogueDto> getCatalogueState();

	List<CatalogueDto> getCatalogueWorkType();

	List<CatalogueDto> getCatalogueCountry();

	List<CatalogueDto> getCatalogueCfdi();

	List<CatalogueDto> getBrand();

	List<CatalogueDto> getCategory();

	List<CatalogueDto> getDivision();

	List<CatalogueDto> getFamily();

	List<CatalogueDto> getFiscalRegime();

	void catalogsSave(CatalogueEntity catalogs);

	ResponseModel getAddressColony(String type, String cp, String idOperation);

	ResponseModel getAddress(String type, String colony, String cp, String idOperation);

	ResponseModel getAddressDelegation(String type, String state, String idOperation);

	ResponseModel getCoordinateByCompanyCode(String type, String idOperation);

	ResponseModel getDelegationByCompanyCode(String type, String idOperation);

	ResponseModel getFlatByCompanyCode(String type, String idOperation);

	ResponseModel getStateByCompanyCodegetStateByCompanyCode(String type, String idOperation);

	ResponseModel getCountryByCompanyCode(String type, String idOperation);

	ResponseModel getStatus(Long id, String idOperation);

	ResponseModel getCategoryListByCode(String categoryCode, String idOperation);

	List<StatusDto> getStatusList(String idOperation);

	ResponseModel getStateByCompanyCode(String type, String idOperation);

}
