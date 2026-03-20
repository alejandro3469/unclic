package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.clients.ports.api.ClientServicePort;
import mx.com.endtoend.domain.clients.ports.spi.ClientPersistencePort;
import mx.com.endtoend.domain.clients.services.ClientServicelmpl;
import mx.com.endtoend.infrastructure.client.common.adapter.ClientJpaAdapte;

@Configuration
public class ClientConfigurations {

	@Bean
	public ClientPersistencePort clientPersistence() {
		return new ClientJpaAdapte();
	}

	@Bean
	public ClientServicePort clientServicePort() {
		return new ClientServicelmpl(clientPersistence());
	}
	


}
