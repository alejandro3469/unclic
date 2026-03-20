package mx.com.endtoend.infrastructure.reports.sales.articles.calzada.fragua;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.reports.sales.articles.calzada.fragua.repositories.CustomDSLSaleArticleCFraguaRepository;

@Repository
public class SaleArticleReportFraguaRepository extends BaseSaleArticleReportRepository {

	public SaleArticleReportFraguaRepository(CustomDSLSaleArticleCFraguaRepository customDSLSaleArticleCFraguaRepository){
		super(SaleArticleReportFraguaRepository.class,customDSLSaleArticleCFraguaRepository);
	}

}
