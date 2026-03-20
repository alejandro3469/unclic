package mx.com.endtoend.domain.articles.ports.api;

import java.math.BigDecimal;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ArticleServicePort {

	ResponseModel findAticlesByParamsAndCompanyCode(DebugServicePort debugServicePort, boolean debug,
			GenericSerchParamsArticleDto params, String companyCode, String branchCode, String method,
			String idOperation);

	ResponseModel findArticleByScannerBarcode(DebugServicePort debugServicePort, boolean debug,
			GenericSerchParamsArticleDto params, String companyCode, String branchCode, String method,
			String idOperation);

	ResponseModel findConvertionFactorListByArticleNumberAndCompanyCode(DebugServicePort debugServicePort,
			boolean debug, BigDecimal articleNumber, String companyCode, String method, String idOperation);

}
