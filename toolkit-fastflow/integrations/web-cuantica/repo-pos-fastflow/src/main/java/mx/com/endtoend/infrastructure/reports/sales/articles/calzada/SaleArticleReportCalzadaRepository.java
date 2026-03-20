package mx.com.endtoend.infrastructure.reports.sales.articles.calzada;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import mx.com.endtoend.infrastructure.reports.sales.articles.calzada.repositories.CustomDSLSaleArticleCalzadaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SaleArticleReportCalzadaRepository extends BaseSaleArticleReportRepository {

	public SaleArticleReportCalzadaRepository(CustomDSLSaleArticleCalzadaRepository customDSLSaleArticleCalzadaRepository){
		super(SaleArticleReportCalzadaRepository.class,customDSLSaleArticleCalzadaRepository);
	}
}
