package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.orderConfigurations.ports.api.OrderConfigurationServicePort;
import mx.com.endtoend.domain.orderConfigurations.ports.spi.OrderConfigurationPersistencePort;
import mx.com.endtoend.domain.orderConfigurations.services.OrderConfigurationServiceImpl;
import mx.com.endtoend.infrastructure.orderConfigurations.common.adapter.OrderConfigurationJpaAdapter;

@Configuration
public class OrderConfigurationConfigurations {

	@Bean
	public OrderConfigurationPersistencePort orderConfigurationPersistencePort() {
		return new OrderConfigurationJpaAdapter();
	}
	
	@Bean
	public OrderConfigurationServicePort orderConfigurationServicePort() {
		return new OrderConfigurationServiceImpl(orderConfigurationPersistencePort());
	}
}

