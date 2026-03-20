package mx.com.endtoend.domain.catalogue.bussiness.article;

import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueArticlePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CatalogueArticleInterface {

	ResponseModel getBrandByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation);

	ResponseModel getCategoryByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation);

	ResponseModel getDivisionByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation);

	ResponseModel getFamilyByCompanyCode(CatalogueArticlePersistencePort catalogueArticlePersistencePort,
			String companyCode, String idOperation);

	ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
			CatalogueArticlePersistencePort catalogueArticlePersistencePort, String companyCode, String catalogueType,
			String idOperation);

}
