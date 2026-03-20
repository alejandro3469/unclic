package mx.com.endtoend.domain.reports.sales.articles.business;

import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface SaleReporArticleInterface {

	ResponseModel generateReportSaleArticleByParamsAndCompanyCode(SaleReportArticleParamsDto saleReportArticleParamsDto,
			String companyCode, String idOperation);

}
