package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.closings.ports.ClosingOperationPersistencePort;
import mx.com.endtoend.domain.closings.ports.ClosingOperationServicePort;
import mx.com.endtoend.domain.closings.services.ClosingOperationServiceImpl;
import mx.com.endtoend.infrastructure.closings.common.adapter.ClosingOperationJpaAdapter;

@Configuration
public class ClosingOperationConfiguration {

	@Bean
	public ClosingOperationPersistencePort closingOperationPersistencePort() {
		return new ClosingOperationJpaAdapter();
	}

	@Bean
	public ClosingOperationServicePort closingOperationServicePort() {
		return new ClosingOperationServiceImpl(closingOperationPersistencePort());
	}

}
