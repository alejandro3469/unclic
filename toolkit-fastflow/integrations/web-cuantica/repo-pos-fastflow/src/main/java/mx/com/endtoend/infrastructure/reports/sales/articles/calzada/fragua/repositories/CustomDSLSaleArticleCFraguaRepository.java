package mx.com.endtoend.infrastructure.reports.sales.articles.calzada.fragua.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.articles.common.repository.BaseCustomDSLSaleArticleRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDSLSaleArticleCFraguaRepository extends BaseCustomDSLSaleArticleRepository {

	public CustomDSLSaleArticleCFraguaRepository(@Qualifier("fraguaDataEntityManagerFactory")
												 EntityManager em) {
		super(CustomDSLSaleArticleCFraguaRepository.class, em);
	}




}
