package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.cash.closingInstruments.ports.api.ClosingInstrumentServicePort;
import mx.com.endtoend.domain.cash.closingInstruments.ports.spi.ClosingInstrumentPersistencePort;
import mx.com.endtoend.domain.cash.closingInstruments.services.ClosingInstrumentImpl;
import mx.com.endtoend.infrastructure.cash.closingInstruments.common.adapter.ClosingInstrumentJpaAdapter;

@Configuration
public class ClosingInstrumentConfiguration {

	@Bean
	public ClosingInstrumentPersistencePort closnigInstrumentPersistencePort() {
		return new ClosingInstrumentJpaAdapter();
	}

	@Bean
	public ClosingInstrumentServicePort closingInstrumentServicePort() {
		return new ClosingInstrumentImpl(closnigInstrumentPersistencePort());
	}
}
