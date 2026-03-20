package mx.com.endtoend.domain.articles.ports.spi;

import java.math.BigDecimal;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

/**
 * 
 * @author ddcasas
 *
 */

public interface ArticlePersistencePort {

	ResponseModel findArticlesByParamnsAndCompanyCode(GenericSerchParamsArticleDto genericSerchParamsArticleDto,
			String companyCode, String branchCode, String idOperation, boolean debug);

	ResponseModel findArticleByScannerAndCompanyCode(GenericSerchParamsArticleDto genericSerchParamsArticleDto,
			String barcode, String companyCode, String idOperation);

	ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(BigDecimal articleNumber, String companyCode,
			String idOperation);

}