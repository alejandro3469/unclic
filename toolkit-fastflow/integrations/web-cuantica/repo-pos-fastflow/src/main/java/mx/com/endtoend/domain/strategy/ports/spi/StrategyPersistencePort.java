package mx.com.endtoend.domain.strategy.ports.spi;

import java.util.List;

import mx.com.endtoend.domain.strategy.dto.StrategyDto;


public interface StrategyPersistencePort {
	
	List<StrategyDto> strategyList();

}
