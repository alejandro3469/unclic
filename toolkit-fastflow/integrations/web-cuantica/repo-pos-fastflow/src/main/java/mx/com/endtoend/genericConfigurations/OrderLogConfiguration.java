package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.logs.orders.ports.OrderLogPersistencePort;
import mx.com.endtoend.domain.logs.orders.ports.OrderLogServicePort;
import mx.com.endtoend.domain.logs.orders.services.OrderLogServiceImpl;
import mx.com.endtoend.infrastructure.logs.orders.OrderLogPersistenceImpl;

@Configuration
public class OrderLogConfiguration {

	@Bean
	OrderLogPersistencePort orderLogPersistencePort() {
		return new OrderLogPersistenceImpl();
	}

	@Bean
	OrderLogServicePort orderLogServicePort() {
		return new OrderLogServiceImpl(orderLogPersistencePort());
	}
}
