package mx.com.endtoend.domain.articles.ports.spi;

import java.math.BigDecimal;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CustomArticlePersistencePort {

	ResponseModel createCustomArticle(CustomArticleDto customArticleDto, String companyCode, String brabchCode,
			String idOperation);

	ResponseModel updateCustomArticle(CustomArticleDto customArticleDto, String companyCode, String brabchCode,
			String idOperation);

	ResponseModel existsCustomArticleByName(String name, String companyCode, String brabchCode, String idOperation);

	ResponseModel existsCustomArticleByNameAndIdNot(String name, Long id, String companyCode, String brabchCode,
			String idOperation);

	ResponseModel existsCustomArticleByArticleNumber(BigDecimal articleNumber, String companyCode, String brabchCode,
			String idOperation);

	ResponseModel existsCustomArticleByArticleNumberAndIdNot(BigDecimal articleNumber, Long id, String companyCode,
			String brabchCode, String idOperation);

	ResponseModel enableById(Long id, boolean enable, String companyCode, String branchCode, String idOperation);

	ResponseModel findById(Long id, String companyCode, String branchCode, String idOperation);

	ResponseModel findAllByEnable(boolean enable, String companyCode, String branchCode, String idOperation);

	ResponseModel findAllActiveBySaleTypeAndCompanyCode(String saleType, String companyCode, String idOperation);

}
