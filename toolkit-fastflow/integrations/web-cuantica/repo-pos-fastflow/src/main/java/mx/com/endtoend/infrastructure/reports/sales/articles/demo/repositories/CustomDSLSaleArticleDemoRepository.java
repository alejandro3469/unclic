package mx.com.endtoend.infrastructure.reports.sales.articles.demo.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleDemoRepository extends BaseCustomDSLSaleArticleRepository {

    public CustomDSLSaleArticleDemoRepository(@Qualifier("demoDataEntityManagerFactory")
                                                   EntityManager em) {
        super(CustomDSLSaleArticleDemoRepository.class, em);
    }

}
