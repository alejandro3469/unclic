package mx.com.endtoend.infrastructure.reports.sales.articles.ferresamano;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.reports.sales.articles.ferresamano.repositories.CustomDSLSaleArticleCFSamanoRepository;

@Repository
public class SaleArticleReportCFSamanoRepository extends BaseSaleArticleReportRepository {

	public SaleArticleReportCFSamanoRepository(CustomDSLSaleArticleCFSamanoRepository customDSLSaleArticleCarredanaRepository){
		super(SaleArticleReportCFSamanoRepository.class,customDSLSaleArticleCarredanaRepository);
	}
}
