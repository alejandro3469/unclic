package mx.com.endtoend.infrastructure.reports.sales.articles.common.repository;

import java.util.List;

import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleReportDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SummaryArticleSaleDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface GenericSaleArticleRepository {

	ResponseModel generateSaleArticleReport(ArticleSaleReportDto articleSaleReportDto, String format,
			String idOperation);

	List<SummaryArticleSaleDto> findArticleListByParams(SaleReportArticleParamsDto saleReportArticleParamsDto,
			String idOperation);

}
