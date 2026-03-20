package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CataloguePersistencePort;
import mx.com.endtoend.domain.catalogue.services.CatalogueServicelmpl;
import mx.com.endtoend.infrastructure.catalogue.orders.common.adapter.CatalogueJpaAdapter;

@Configuration
public class CatalogueConfigurations {

	@Bean
	public  CataloguePersistencePort catalogPersistence() {
		return new CatalogueJpaAdapter();
	}
	
	@Bean
	public CatalogueServicePort catalogServicePort() {
		return new CatalogueServicelmpl(catalogPersistence());
	}
}
