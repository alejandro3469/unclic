package mx.com.endtoend.genericConfigurations;

import mx.com.endtoend.infrastructure.strategy.common.converters.StrategyConverter;
import mx.com.endtoend.infrastructure.strategy.common.repository.StrategyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.com.endtoend.domain.strategy.ports.api.StrategyServicePort;
import mx.com.endtoend.domain.strategy.ports.spi.StrategyPersistencePort;
import mx.com.endtoend.domain.strategy.services.StrategyServicelmpl;
import mx.com.endtoend.infrastructure.strategy.common.strategies.StrategyJpaAdapter;

@Configuration
public class StrategyConfigurations {

	private  final StrategyRepository strategyRepository;
	private  final StrategyConverter strategyConverter;
	public StrategyConfigurations(StrategyRepository _strategyRepository, StrategyConverter _strategyConverter) {
		strategyRepository = _strategyRepository;
		strategyConverter = _strategyConverter;
	}

	@Bean
	public StrategyPersistencePort strategyPersistence() {
		return new StrategyJpaAdapter(strategyRepository, strategyConverter);
	}
	
	@Bean
	public StrategyServicePort strategyServicePort() {
		return new StrategyServicelmpl(strategyPersistence());
	}

}
