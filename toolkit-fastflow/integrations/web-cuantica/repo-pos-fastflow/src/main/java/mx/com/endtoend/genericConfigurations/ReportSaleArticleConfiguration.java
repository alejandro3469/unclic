package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticlePersistencePort;
import mx.com.endtoend.domain.reports.sales.articles.ports.ReportSaleArticleServicePort;
import mx.com.endtoend.domain.reports.sales.articles.service.SaleArticleReportServiceImpl;
import mx.com.endtoend.infrastructure.reports.sales.articles.common.adapter.SaleArticleReportJpaAdapter;

@Configuration
public class ReportSaleArticleConfiguration {

	@Bean
	public ReportSaleArticlePersistencePort reportSaleArticlePersistencePort() {
		return new SaleArticleReportJpaAdapter();
	}

	@Bean
	public ReportSaleArticleServicePort reportSaleArticleServicePort() {
		return new SaleArticleReportServiceImpl(reportSaleArticlePersistencePort());
	}
}
