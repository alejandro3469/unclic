package mx.com.endtoend.domain.articles.ports.api;

import mx.com.endtoend.domain.articles.dto.CustomArticleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface CustomArticleServicePort {

	ResponseModel createCustomArticle(CustomArticleDto customArticleDto, String companyCode, String branchCode,
			String method, String idOperation);

	ResponseModel updateCustomArticleById(CustomArticleDto customArticleDto, String companyCode, String branchCode,
			String method, String idOperation);

	ResponseModel enableCustomArticleById(String companyCode, String branchCode, String idOperation, String method,
			boolean enable, Long id);

	ResponseModel findCustomArticleById(Long id, String companyCode, String branchCode, String method,
			String idOperation);

	ResponseModel findAllCustomArticleByEnable(String companyCode, String branchCode, String method, String idOperation,
			boolean enable);

	ResponseModel findAllActiveBySaleType(String companyCode, String saleType, String method, String idOperation);
}
