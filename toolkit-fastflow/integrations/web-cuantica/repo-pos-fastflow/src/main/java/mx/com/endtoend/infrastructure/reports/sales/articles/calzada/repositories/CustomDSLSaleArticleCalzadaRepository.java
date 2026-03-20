package mx.com.endtoend.infrastructure.reports.sales.articles.calzada.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleCalzadaRepository extends BaseCustomDSLSaleArticleRepository {

	public CustomDSLSaleArticleCalzadaRepository(@Qualifier("calzadaDataEntityManagerFactory") EntityManager em) {
		super(CustomDSLSaleArticleCalzadaRepository.class, em);
	}
}
