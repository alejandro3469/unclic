package mx.com.endtoend.infrastructure.reports.sales.articles.carredana;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseSaleArticleReportRepository;
import org.springframework.stereotype.Repository;
import mx.com.endtoend.infrastructure.reports.sales.articles.carredana.repositories.CustomDSLSaleArticleCarredanaRepository;

@Repository
public class SaleArticleReportCarredanaRepository extends BaseSaleArticleReportRepository {

    public SaleArticleReportCarredanaRepository(CustomDSLSaleArticleCarredanaRepository customDSLSaleArticleCarredanaRepository) {
        super(SaleArticleReportCarredanaRepository.class,customDSLSaleArticleCarredanaRepository);
    }

}
