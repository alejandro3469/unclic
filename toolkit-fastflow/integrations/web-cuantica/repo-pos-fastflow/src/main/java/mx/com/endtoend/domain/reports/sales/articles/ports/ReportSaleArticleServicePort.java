package mx.com.endtoend.domain.reports.sales.articles.ports;

import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface ReportSaleArticleServicePort {

	ResponseModel generateReportSaleArticleByParamsAndCompanyCode(SaleReportArticleParamsDto saleReportArticleParamsDto,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation);
}
