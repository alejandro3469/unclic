package mx.com.endtoend.domain.reports.sales.articles.ports;

import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleReportDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportSaleArticlePersistencePort {

	ResponseModel generateSaleArticleReportByCompanyCode(ArticleSaleReportDto articleSaleReportDto, String format,
			String companyCode, String idOperation);

	ResponseModel findArticleListByParamsAndCompanyCode(SaleReportArticleParamsDto saleReportArticleParamsDto,
			String companyCode, String idOperation);

}
