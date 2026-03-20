package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.domain.catalogue.ports.api.CatalogueAddressServicePort;
import mx.com.endtoend.domain.catalogue.ports.spi.CatalogueAddressPersistencePort;
import mx.com.endtoend.domain.catalogue.services.CatalogueAddressServiceImpl;
import mx.com.endtoend.infrastructure.catalogue.address.CatalogueAddressJpaAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogueAddressConfiguration {
    @Bean
    public CatalogueAddressPersistencePort catalogueAddressPersistencePort() {
        return new CatalogueAddressJpaAdapter();
    }
    @Bean
    public CatalogueAddressServicePort catalogueAddressServicePort() {
        return new CatalogueAddressServiceImpl(catalogueAddressPersistencePort());
    }

}
