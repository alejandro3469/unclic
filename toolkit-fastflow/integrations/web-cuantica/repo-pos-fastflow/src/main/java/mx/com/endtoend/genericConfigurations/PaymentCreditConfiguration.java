package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditPersistencePort;
import mx.com.endtoend.domain.paymentsCredit.ports.PaymentCreditServicePort;
import mx.com.endtoend.domain.paymentsCredit.services.PaymentCreditServiceImpl;
import mx.com.endtoend.infrastructure.paymentsCredit.common.adapter.PaymentCreditJpaAdapter;

@Configuration
public class PaymentCreditConfiguration {

	@Bean
	PaymentCreditPersistencePort paymentCreditPersistencePort() {
		return new PaymentCreditJpaAdapter();
	}
	
	@Bean
	PaymentCreditServicePort paymentCreditServicePort() {
		return new PaymentCreditServiceImpl(paymentCreditPersistencePort());
	}
}
