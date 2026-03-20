package mx.com.endtoend.infrastructure.reports.sales.articles.common.adapter;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.GenericSaleArticleRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.common.factory.SaleArticleRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.reports.sales.articles.dto.ArticleSaleReportDto;
import mx.com.endtoend.domain.reports.sales.articles.dto.SaleReportArticleParamsDto;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticlePersistencePort;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public class SaleArticleReportJpaAdapter implements ReportSaleArticlePersistencePort {

	@Autowired
	private SaleArticleRepositoryFactory saleArticleRepositoryFactory;

	private final Logger LOG = LoggerFactory.getLogger(SaleArticleReportJpaAdapter.class);

	@Override
	public ResponseModel generateSaleArticleReportByCompanyCode(ArticleSaleReportDto articleSaleReportDto,
			String format, String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT generateSaleArticleReportByCompanyCode()", idOperation));
		GenericSaleArticleRepository repository = saleArticleRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return repository.generateSaleArticleReport(articleSaleReportDto, format, idOperation);
	}

	@Override
	public ResponseModel findArticleListByParamsAndCompanyCode(SaleReportArticleParamsDto saleReportArticleParamsDto,
			String companyCode, String idOperation) {
		LOG.info(String.format("%s INIT findArticleListByParamsAndCompanyCode()", idOperation));
		GenericSaleArticleRepository repository = saleArticleRepositoryFactory.getRepositoryByCompanyCode(companyCode);
		if (repository == null) {
			LOG.error(String.format("%s AN ERROR OCCURRED WHILE RETRIEVING THE SPECIFIC IMPLEMENTATION ", idOperation));
			throw new GlobalError();
		}
		return new ResponseModel(repository.findArticleListByParams(saleReportArticleParamsDto, idOperation));
	}

}
