package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.articles.ports.api.ArticleServicePort;
import mx.com.endtoend.domain.articles.ports.spi.ArticlePersistencePort;
import mx.com.endtoend.domain.articles.services.ArticleServiceImpl;
import mx.com.endtoend.infrastructure.articles.common.adapter.ArticleJpaAdapter;

@Configuration
public class ArticleConfigurations {

	@Bean
	public ArticlePersistencePort articlePersistence() {
		return new ArticleJpaAdapter();
	}

	@Bean
	public ArticleServicePort articleServicePort() {
		return new ArticleServiceImpl(articlePersistence());
	}
}
