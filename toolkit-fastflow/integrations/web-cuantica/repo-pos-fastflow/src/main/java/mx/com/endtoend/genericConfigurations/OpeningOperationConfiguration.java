package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.openings.ports.OpeningOperationPersistencePort;
import mx.com.endtoend.domain.openings.ports.OpeningOperationServicePort;
import mx.com.endtoend.domain.openings.services.OpeningOperationServiceImpl;
import mx.com.endtoend.infrastructure.openings.common.adapter.OpeningOperationJpaAdapter;

@Configuration
public class OpeningOperationConfiguration {

	@Bean
	public OpeningOperationPersistencePort openingOperationPersistencePort() {
		return new OpeningOperationJpaAdapter();
	}

	@Bean
	public OpeningOperationServicePort openingOperationServicePort() {
		return new OpeningOperationServiceImpl(openingOperationPersistencePort());
	}
}
