package mx.com.endtoend.domain.articles.factory;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CustomArticleInterface {

	ResponseModel createCustomArticle(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation);

	ResponseModel updateCustomArticle(CustomArticlePersistencePort customArticlePersistencePort,
			CustomArticleDto customArticleDto, String companyCode, String branchCode, String idOperation);

	ResponseModel enabledCustomArticleById(CustomArticlePersistencePort customArticlePersistencePort, boolean enable,
			Long id, String companyCode, String branchCode, String idOperation);

	ResponseModel getCustomArticleListByEnable(CustomArticlePersistencePort customArticlePersistencePort,
			boolean enable, String companyCode, String branchCode, String idOperation);

	ResponseModel getCustomArticleById(CustomArticlePersistencePort customArticlePersistencePort, Long id,
			String companyCode, String branchCode, String idOperation);

	ResponseModel findAllActiveBySaleType(CustomArticlePersistencePort customArticlePersistencePort, String companyCode,
			String saleType, String idOperation);

}
