package mx.com.endtoend.genericConfigurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.cash.openingInstruments.ports.api.OpeningInstrumentServicePort;
import mx.com.endtoend.domain.cash.openingInstruments.ports.spi.OpenigInstrumentPersistencePort;
import mx.com.endtoend.domain.cash.openingInstruments.services.OpeningInstrumentImpl;
import mx.com.endtoend.infrastructure.cash.openingInstruments.common.adapter.OpeningInstrumentJpaAdapter;

@Configuration
public class OpeningInstrumentConfiguration {

	@Bean
	public OpenigInstrumentPersistencePort openigInstrumentPersistencePort() {
		return new OpeningInstrumentJpaAdapter();
	}
	
	@Bean
	public OpeningInstrumentServicePort openingInstrumentServicePort() {
		return new OpeningInstrumentImpl(openigInstrumentPersistencePort());
	}
}
