package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueArticleServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueArticlePersistencePort;
import mx.com.endtoend.domain.catalogue.services.CatalogueArticleServiceImpl;
import mx.com.endtoend.infrastructure.catalogue.article.common.adapter.CatalogueArticleJpaAdapter;

@Configuration
public class CatalogueArticleConfiguration {

	@Bean
	public CatalogueArticlePersistencePort catalogueArticlePersistencePort() {
		return new CatalogueArticleJpaAdapter();
	}
	
	@Bean
	public CatalogueArticleServicePort catalogueArticleServicePort() {
		return new CatalogueArticleServiceImpl(catalogueArticlePersistencePort());
	}
}
