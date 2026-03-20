package mx.com.endtoend.infrastructure.reports.sales.articles.demo;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import org.springframework.stereotype.Repository;
import mx.com.endtoend.infrastructure.reports.sales.articles.demo.repositories.CustomDSLSaleArticleDemoRepository;

@Repository
public class SaleArticleReportDemoRepository extends BaseSaleArticleReportRepository {

    public SaleArticleReportDemoRepository(CustomDSLSaleArticleDemoRepository customDSLSaleArticleCarredanaRepository) {
        super(SaleArticleReportDemoRepository.class,customDSLSaleArticleCarredanaRepository);
    }

}
