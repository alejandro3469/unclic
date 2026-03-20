package mx.com.endtoend.infrastructure.reports.sales.articles.ferresamano.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleCFSamanoRepository extends BaseCustomDSLSaleArticleRepository {


	public CustomDSLSaleArticleCFSamanoRepository(@Qualifier("ferresamanoDataEntityManagerFactory")
												  EntityManager em) {
		super(CustomDSLSaleArticleCFSamanoRepository.class, em);
	}
}
