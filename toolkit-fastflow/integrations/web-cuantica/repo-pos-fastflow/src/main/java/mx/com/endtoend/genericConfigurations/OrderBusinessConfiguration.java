package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.orders.ports.OrderPersistencePort;
import mx.com.endtoend.domain.orders.ports.OrderV2ServicePort;
import mx.com.endtoend.domain.orders.services.OrderServiceImpl;
import mx.com.endtoend.infrastructure.orders.common.adapter.OrderJpaAdapter;

@Configuration
public class OrderBusinessConfiguration {

	@Bean
	OrderPersistencePort orderPersistence() {
		return new OrderJpaAdapter();
	}

	@Bean
	OrderV2ServicePort orderV2ServicePort() {
		return new OrderServiceImpl(orderPersistence());
	}
}
