package mx.com.endtoend.domain.articles.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.factory.CustomArticleFactory;
import mx.com.endtoend.domain.articles.factory.CustomArticleInterface;
import mx.com.endtoend.domain.articles.ports.api.CustomArticleServicePort;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */

public class CustomArticleServiceImpl implements CustomArticleServicePort {

	private CustomArticlePersistencePort customArticlePersistencePort;

	public CustomArticleServiceImpl(CustomArticlePersistencePort customArticlePersistencePort) {

		this.customArticlePersistencePort = customArticlePersistencePort;
	}

	private final static Logger LOG = LoggerFactory.getLogger(CustomArticleServiceImpl.class);

	CustomArticleFactory customArticleFactory = new CustomArticleFactory();

	@Override
	public ResponseModel createCustomArticle(CustomArticleDto customArticleDto, String companyCode, String branchCode,
			String method, String idOperation) {

		LOG.info(String.format("%s INIT createCustomArticle()", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.createCustomArticle(customArticlePersistencePort,
				customArticleDto, companyCode, branchCode, idOperation);

		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel updateCustomArticleById(CustomArticleDto customArticleDto, String companyCode,
			String branchCode, String method, String idOperation) {

		LOG.info(String.format("%s INIT updateCustomArticleById", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.updateCustomArticle(customArticlePersistencePort,
				customArticleDto, companyCode, branchCode, idOperation);

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel enableCustomArticleById(String companyCode, String branchCode, String idOperation,
			String method, boolean enable, Long id) {

		LOG.info(String.format("%s INIT enableCustomArticleById()", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.enabledCustomArticleById(customArticlePersistencePort,
				enable, id, companyCode, branchCode, idOperation);

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel findCustomArticleById(Long id, String companyCode, String branchCode, String method,
			String idOperation) {
		LOG.info(String.format("%s INIT findCustomArticleById()", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.getCustomArticleById(customArticlePersistencePort, id,
				companyCode, branchCode, idOperation);

		return responseFromPersistencePort;

	}

	@Override
	public ResponseModel findAllCustomArticleByEnable(String companyCode, String branchCode, String method,
			String idOperation, boolean enable) {

		LOG.info(String.format("%s INIT findAllCustomArticleByEnable()", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.getCustomArticleListByEnable(
				customArticlePersistencePort, enable, companyCode, branchCode, idOperation);

		return responseFromPersistencePort;
	}

	@Override
	public ResponseModel findAllActiveBySaleType(String companyCode, String saleType, String method,
			String idOperation) {

		LOG.info(String.format("%s INIT findAllActiveBySaleType()", idOperation));
		CustomArticleInterface customArticle = customArticleFactory.getImplementation(method);
		if (customArticle == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel responseFromPersistencePort = customArticle.findAllActiveBySaleType(customArticlePersistencePort,
				companyCode, saleType, idOperation);
		return responseFromPersistencePort;
	}

}
