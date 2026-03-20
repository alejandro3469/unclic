package mx.com.endtoend.infrastructure.reports.sales.articles.carredana.zapata.repositories;


import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleCZapataRepository extends BaseCustomDSLSaleArticleRepository {


	public CustomDSLSaleArticleCZapataRepository(@Qualifier("zapataDataEntityManagerFactory")
												 EntityManager em) {
		super(CustomDSLSaleArticleCZapataRepository.class, em);
	}
}
