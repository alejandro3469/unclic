package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.creditCardReference.ports.CreditCardConfigurationServicePort;
import mx.com.endtoend.domain.cash.creditCardReference.services.CreditCardConfigurationImpl;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.adapter.CreditCardConfigurationJpaAdapter;

@Configuration
public class CreditCardReferenceConfiguration {

	@Bean
	public CreditCardConfigurationPersistencePort creditCardConfigurationPersistencePort() {
		return new CreditCardConfigurationJpaAdapter();
	}

	@Bean
	public CreditCardConfigurationServicePort creditCardConfigurationServicePort() {
		return new CreditCardConfigurationImpl(creditCardConfigurationPersistencePort());
	}
}
