package mx.com.endtoend.infrastructure.catalogue.orders.common.service;

import java.util.List;

import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;


public interface GenericCatalogueService {
	
	ResponseModel getCatalogueByDrsyAndDrrt(String type, String idOperation);
	
//	ResponseModel getAddressColony(String type,String cp, String idOperation);
//	
//	ResponseModel getAddress(String type,String colony,String cp, String idOperation);
//	
//	ResponseModel getAddressDelegation(String type,String state, String idOperation);
	
//	List<CatalogueDto> getCatalogueClientType();
	
//	CatalogueEntity getCatalogByCode(CatalogueDto catalog, String idOperation);
	
//	void catalogsSave(CatalogueEntity catalogs);
	
//	List<CatalogueDto> getCatalogueCoordinate();
//	
//	List<CatalogueDto> getCatalogueDelegation();
//	
//	List<CatalogueDto> getCatalogueFlat();
//	
//	List<CatalogueDto> getCatalogueHowToContact();
//	
//	List<CatalogueDto> getCatalogueState();
//	
//	List<CatalogueDto> getCatalogueWorkType();
//	
//	List<CatalogueDto> getCatalogueCountry();
//	
//	List<CatalogueDto> getCatalogueCfdi();
//	
//	List<CatalogueDto> getBrand();
//	
//	List<CatalogueDto> getCategory();
//	
//	List<CatalogueDto> getDivision();
//	
//	List<CatalogueDto> getFamily();
//	
//	List<CatalogueDto> getFiscalRegime();
	
	ResponseModel getStatus(Long id, String idOperation);
	
	ResponseModel createStatus(StatusDto statusDto, String idOperation);
	
	boolean existsStatus(StatusDto statusDto, String idOperation);
	
	ResponseModel getCfdi(String type, String idOperation);
	
	ResponseModel getFiscalRegimeCatalogue(String type,String idOperation);
	
	ResponseModel getCategoryListByCode(String categoryCode, String idOperation);
	
	List<StatusDto> getStatusList(String idOperation);

}
