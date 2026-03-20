package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogPersistencePort;
import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.logs.security.services.SecurityLogSerciceImpl;
import mx.com.endtoend.infrastructure.logs.security.SecurityLogPersistenceImpl;

@Configuration
public class SecurityLogConfiguration {

	@Bean
	SecurityLogPersistencePort securityLogPersistencePort() {
		return new SecurityLogPersistenceImpl();
	}

	@Bean
	SecurityLogServicePort securityLogServicePort() {
		return new SecurityLogSerciceImpl(securityLogPersistencePort());
	}
}
