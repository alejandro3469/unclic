package mx.com.endtoend.infrastructure.strategy.common.strategies;


import mx.com.endtoend.infrastructure.strategy.common.adapter.BaseStrategyJpaAdapter;
import mx.com.endtoend.infrastructure.strategy.common.converters.StrategyConverter;
import mx.com.endtoend.infrastructure.strategy.common.repository.StrategyRepository;
import org.springframework.stereotype.Service;


@Service
public class StrategyJpaAdapter extends BaseStrategyJpaAdapter {


	public StrategyJpaAdapter(StrategyRepository strategyRepository, StrategyConverter strategyConverter) {
		super(strategyRepository,strategyConverter);
	}

}
