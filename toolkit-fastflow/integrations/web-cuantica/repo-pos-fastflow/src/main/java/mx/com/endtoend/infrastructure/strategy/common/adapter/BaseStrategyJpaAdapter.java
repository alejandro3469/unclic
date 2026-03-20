package mx.com.endtoend.infrastructure.strategy.common.adapter;

import mx.com.endtoend.domain.strategy.dto.StrategyDto;
import mx.com.endtoend.domain.strategy.ports.spi.StrategyPersistencePort;
import mx.com.endtoend.infrastructure.strategy.common.converters.StrategyConverter;
import mx.com.endtoend.infrastructure.strategy.common.entities.StrategyEntity;
import mx.com.endtoend.infrastructure.strategy.common.repository.BaseStrategyRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BaseStrategyJpaAdapter implements StrategyPersistencePort {

    private  final BaseStrategyRepository strategyRepository;
    private  final StrategyConverter strategyConverter;

    public BaseStrategyJpaAdapter(BaseStrategyRepository strategyRepository, StrategyConverter strategyConverter) {
        this.strategyRepository = strategyRepository;
        this.strategyConverter = strategyConverter;
    }

    @Transactional
    @Override
    public List<StrategyDto> strategyList() {

        List<StrategyDto> strategyDtos = strategyConverter.strategyEntityListToStrategyDtoList(
                (List<StrategyEntity>)strategyRepository.findAll());

//		List<String> list = new ArrayList<>();
//		List<StrategyEntity> stategyList = strategyRepository.findAll();
//		System.out.println("Tamaño de la lista: " + stategyList.size());
//		for (StrategyEntity strategy : stategyList ) {
//
//			boolean company =  companyRepository.existsByCode(CompanyCodes.valueOf(strategy.getCode()));
//
//			if (!company) {
//
//				list.add(strategy.getCode());
//
//			}
//		}

        return strategyDtos;
    }
}
