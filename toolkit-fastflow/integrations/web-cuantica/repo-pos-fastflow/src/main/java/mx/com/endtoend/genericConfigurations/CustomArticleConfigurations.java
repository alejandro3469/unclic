package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.articles.ports.api.CustomArticleServicePort;
import mx.com.endtoend.domain.articles.ports.spi.CustomArticlePersistencePort;
import mx.com.endtoend.domain.articles.services.CustomArticleServiceImpl;
import mx.com.endtoend.infrastructure.articles.common.adapter.CustomArticleJpaAdapter;

@Configuration
public class CustomArticleConfigurations {

	@Bean
	CustomArticlePersistencePort customArticlePersistence() {
		return new CustomArticleJpaAdapter();
	}

	@Bean
	CustomArticleServicePort customArticleServicePort() {
		return new CustomArticleServiceImpl(customArticlePersistence());
	}
}
