package mx.com.endtoend.domain.catalogue.bussiness;

import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueInterface {

	ResponseModel getClientTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getCoordinateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getDelegationByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getFlatByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getHowToContactByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getStateByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getWorkTypeByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getCountryByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel updateCatalogsByCompanyCode(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getAddressColony(String companyCode, CataloguePersistencePort catalogPersistencePort, String pc,
			String idOperation);

	ResponseModel getAddress(String companyCode, CataloguePersistencePort catalogPersistencePort, String colony,
			String cp, String idOperation);

	ResponseModel getAddressDelegation(String companyCode, CataloguePersistencePort catalogPersistencePort,
			String state, String idOperation);

	ResponseModel getStatus(String companyCode, Long id, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel createStatus(String companyCode, StatusDto statusDto, CataloguePersistencePort catalogPersistencePort,
			String idOperation);

	ResponseModel getCfdi(String companyCode, CataloguePersistencePort catalogPersistencePort, String idOperation);

	ResponseModel getFiscalRegimeCataloge(CataloguePersistencePort catalogPersistencePort, String companyCode,
			String idOperation);
	
	ResponseModel getStatusListByCompanyCode(CataloguePersistencePort cataloguePersistencePort,String companyCode,
			String idOperation);

	ResponseModel getCategoryListByCodeAndCompanyCode(CataloguePersistencePort catalogPersistencePort,
			String companyCode, String method, String categoryCode, String idOperation);
}
