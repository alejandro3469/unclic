package mx.com.endtoend.infrastructure.reports.sales.articles.carredana.repositories;


import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleCarredanaRepository extends BaseCustomDSLSaleArticleRepository {

	public CustomDSLSaleArticleCarredanaRepository(@Qualifier("carredanaDataEntityManagerFactory")
												   EntityManager em) {
		super(CustomDSLSaleArticleCarredanaRepository.class, em);
	}

}
