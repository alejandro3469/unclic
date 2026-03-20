package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.bankReference.ports.BankConfigurationServicePort;
import mx.com.endtoend.domain.cash.bankReference.services.BankConfigurationImpl;
import mx.com.endtoend.infrastructure.cash.bankReference.common.adapter.BankConfigurationJpaAdapter;

@Configuration
public class BankConfiguration {

	@Bean
	public BankConfigurationPersistencePort bankConfigurationPersistencePort() {
		return new BankConfigurationJpaAdapter();
	}

	@Bean
	public BankConfigurationServicePort bankConfigurationServicePort() {
		return new BankConfigurationImpl(bankConfigurationPersistencePort());
	}

}
