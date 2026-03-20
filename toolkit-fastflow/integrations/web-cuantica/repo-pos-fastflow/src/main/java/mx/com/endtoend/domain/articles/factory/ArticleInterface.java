package mx.com.endtoend.domain.articles.factory;

import java.math.BigDecimal;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.articles.ports.spi.ArticlePersistencePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ArticleInterface {

	ResponseModel findArticlesByParamsAndCompanyCode(ArticlePersistencePort articlePersistencePort,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String branchCode,
			String idOperation, boolean debug);

	ResponseModel findArticleByBarcodeAndCompanyCode(ArticlePersistencePort articlePersistencePort,
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String companyCode, String branchCode,
			String idOperation, boolean debug);

	ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(ArticlePersistencePort articlePersistencePort,
			BigDecimal articleNumber, String companyCode, String idOperation);
}
