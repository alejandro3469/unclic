package mx.com.endtoend.domain.catalogue.bussiness.client;

import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueClientPersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueClientInterface {

	ResponseModel getCfdiByCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation);

	ResponseModel getRegimeFiscalCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation);

	ResponseModel getClientTypeCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation);

	ResponseModel getContectMethodCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation);

	ResponseModel getWorkTypeByCompanyCode(CatalogueClientPersistencePort catalogueClientPersistencePort,
			String companyCode, String idOperation);

	ResponseModel updateCatalogueCompanyCodeAndCatalogueType(
			CatalogueClientPersistencePort catalogueClientPersistencePort, String companyCode, String catalogueType,
			String idOperation);

}
