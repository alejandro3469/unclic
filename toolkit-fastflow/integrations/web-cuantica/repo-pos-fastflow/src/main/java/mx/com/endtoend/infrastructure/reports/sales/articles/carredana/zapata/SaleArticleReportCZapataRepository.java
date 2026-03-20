package mx.com.endtoend.infrastructure.reports.sales.articles.carredana.zapata;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import org.springframework.stereotype.Repository;
import mx.com.endtoend.infrastructure.reports.sales.articles.carredana.zapata.repositories.CustomDSLSaleArticleCZapataRepository;

@Repository
public class SaleArticleReportCZapataRepository extends BaseSaleArticleReportRepository {

	public SaleArticleReportCZapataRepository(CustomDSLSaleArticleCZapataRepository customDSLSaleArticleCarredanaRepository) {
		super(SaleArticleReportCZapataRepository.class,customDSLSaleArticleCarredanaRepository);
	}
}
