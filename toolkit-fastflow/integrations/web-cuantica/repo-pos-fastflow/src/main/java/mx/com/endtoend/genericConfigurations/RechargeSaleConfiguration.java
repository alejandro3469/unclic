package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.recharges.ports.RechargeSalePersistencePort;
import mx.com.endtoend.domain.recharges.ports.RechargeSaleServicePort;
import mx.com.endtoend.domain.recharges.services.RechargeSaleServiceImpl;
import mx.com.endtoend.infrastructure.recharges.common.adapter.RechargeSaleJpaAdapter;

@Configuration
public class RechargeSaleConfiguration {

	@Bean
	RechargeSalePersistencePort rechargeSalePersistencePort() {
		return new RechargeSaleJpaAdapter();
	}

	@Bean
	RechargeSaleServicePort rechargeSaleServicePort() {
		return new RechargeSaleServiceImpl(rechargeSalePersistencePort());
	}

}
