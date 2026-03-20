package mx.com.endtoend.domain.catalogue.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.catalogue.bussiness.article.CatalogueArticleFactory;
import mx.com.endtoend.domain.catalogue.bussiness.article.CatalogueArticleInterface;
import mx.com.endtoend.domain.catalogue.ports.api.CatalogueArticleServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class CatalogueArticleServiceImpl implements CatalogueArticleServicePort {

	private CatalogueArticlePersistencePort catalogueArticlePersistencePort;

	public CatalogueArticleServiceImpl(CatalogueArticlePersistencePort catalogueArticlePersistencePort) {
		this.catalogueArticlePersistencePort = catalogueArticlePersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueArticleServiceImpl.class);

	private CatalogueArticleFactory articleFactory = new CatalogueArticleFactory();

	@Override
	public ResponseModel getBrandByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getBrandByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueArticleInterface catalogueArticle = articleFactory.getImplementatioByCode(method,
				catalogueOracleServicePort);

		if (catalogueArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueArticle.getBrandByCompanyCode(catalogueArticlePersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getCategoryByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getCategoryByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueArticleInterface catalogueArticle = articleFactory.getImplementatioByCode(method,
				catalogueOracleServicePort);

		if (catalogueArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueArticle.getCategoryByCompanyCode(catalogueArticlePersistencePort,
				companyCode, idOperation);

		return responseModel;

	}

	@Override
	public ResponseModel getDivisionByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {

		LOG.info(String.format("%s INIT getDivisionByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueArticleInterface catalogueArticle = articleFactory.getImplementatioByCode(method,
				catalogueOracleServicePort);

		if (catalogueArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueArticle.getDivisionByCompanyCode(catalogueArticlePersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel getFamilyByCompanyCode(CatalogueJdeServicePort catalogueOracleServicePort, String method,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT getFamilyByCompanyCode() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s ]", idOperation, method, companyCode));

		CatalogueArticleInterface catalogueArticle = articleFactory.getImplementatioByCode(method,
				catalogueOracleServicePort);

		if (catalogueArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueArticle.getFamilyByCompanyCode(catalogueArticlePersistencePort,
				companyCode, idOperation);

		return responseModel;
	}

	@Override
	public ResponseModel updateCatalogueByCompanyCodeAndCatalogueType(
			CatalogueJdeServicePort catalogueOracleServicePort, String method, String companyCode, String catalogueType,
			String idOperation) {

		LOG.info(String.format("%s INIT updateCatalogueByCompanyCodeAndCatalogueType() ", idOperation));
		LOG.info(String.format("%s PARAMS: [ method: %s , companyCode: %s , catalogueType: %s ]", idOperation, method,
				companyCode, catalogueType));

		CatalogueArticleInterface catalogueArticle = articleFactory.getImplementatioByCode(method,
				catalogueOracleServicePort);

		if (catalogueArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseModel = catalogueArticle.updateCatalogueByCompanyCodeAndCatalogueType(
				catalogueArticlePersistencePort, companyCode, catalogueType, idOperation);

		return responseModel;
	}

}
