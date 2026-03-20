package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationPersistencePort;
import mx.com.endtoend.domain.cash.emailReport.ports.EmailCashConfigurationServicePort;
import mx.com.endtoend.domain.cash.emailReport.services.EmailCashConfigurationImpl;
import mx.com.endtoend.infrastructure.cash.emailReport.common.adapter.EmailCashConfigurationJpaAdapter;

@Configuration
public class EmailCashConfiguration {

	@Bean
	public EmailCashConfigurationPersistencePort emailCashConfigurationPersistencePort() {
		return new EmailCashConfigurationJpaAdapter();
	}

	@Bean
	public EmailCashConfigurationServicePort emailCashConfigurationServicePort() {
		return new EmailCashConfigurationImpl(emailCashConfigurationPersistencePort());
	}
}
