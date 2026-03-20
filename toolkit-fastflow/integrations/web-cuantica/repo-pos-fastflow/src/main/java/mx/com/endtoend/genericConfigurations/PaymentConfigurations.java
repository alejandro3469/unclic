package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.payments.ports.PaymentPersistencePort;
import mx.com.endtoend.domain.payments.ports.PaymentServicePort;
import mx.com.endtoend.domain.payments.services.PaymentServiceImpl;
import mx.com.endtoend.infrastructure.payments.common.adapter.PaymentJpaAdapter;

@Configuration
public class PaymentConfigurations {

	@Bean
	PaymentPersistencePort paymentPersistence() {
		return new PaymentJpaAdapter();
	}

	@Bean
	PaymentServicePort paymentServicePort() {
		return new PaymentServiceImpl(paymentPersistence());
	}

}
