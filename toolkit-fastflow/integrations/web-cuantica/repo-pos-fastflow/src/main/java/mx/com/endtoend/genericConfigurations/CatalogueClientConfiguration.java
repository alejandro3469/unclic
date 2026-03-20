package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueClientServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueClientPersistencePort;
import mx.com.endtoend.domain.catalogue.services.CatalogueClientServiceImpl;
import mx.com.endtoend.infrastructure.catalogue.client.common.adapter.CatalogueClientJpaAdapter;

@Configuration
public class CatalogueClientConfiguration {

	@Bean
	public CatalogueClientPersistencePort catalogueClientPersistence() {
		return new CatalogueClientJpaAdapter();
	}

	@Bean
	public CatalogueClientServicePort catalogueClientServicePort() {
		return new CatalogueClientServiceImpl(catalogueClientPersistence());
	}

}
