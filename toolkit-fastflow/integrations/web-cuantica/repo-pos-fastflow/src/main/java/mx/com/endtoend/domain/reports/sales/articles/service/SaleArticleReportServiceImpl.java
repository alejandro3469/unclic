package mx.com.endtoend.domain.reports.sales.articles.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.reports.sales.articles.business.SaleReporArticleInterface;
import mx.com.endtoend.domain.reports.sales.articles.business.SaleReportArticleFactory;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticlePersistencePort;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticleServicePort;
import mx.com.endtoend.domain.reports.sales.models.SaleReportInterfaceService;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleArticleReportServiceImpl implements ReportSaleArticleServicePort {

	private ReportSaleArticlePersistencePort reportSaleArticlePersistencePort;

	public SaleArticleReportServiceImpl(ReportSaleArticlePersistencePort reportSaleArticlePersistencePort) {
		this.reportSaleArticlePersistencePort = reportSaleArticlePersistencePort;
	}

	private SaleReportArticleFactory saleReportArticleFactory = new SaleReportArticleFactory();

	private final Logger LOG = LoggerFactory.getLogger(SaleArticleReportServiceImpl.class);

	@Override
	public ResponseModel generateReportSaleArticleByParamsAndCompanyCode(
			SaleReportArticleParamsDto saleReportArticleParamsDto,
			SaleReportInterfaceService saleReportInterfaceService, String method, String companyCode,
			String idOperation) {

		LOG.info(String.format("%s INIT generateReportSaleArticleByParamsAndCompanyCode()", idOperation));
		saleReportInterfaceService.setReportSaleArticlePersistencePort(reportSaleArticlePersistencePort);

		SaleReporArticleInterface reportSale = saleReportArticleFactory.getImplementationByCode(method,
				saleReportInterfaceService);

		if (reportSale == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}

		ResponseModel response = reportSale.generateReportSaleArticleByParamsAndCompanyCode(saleReportArticleParamsDto,
				companyCode, idOperation);
		return response;
	}

}
