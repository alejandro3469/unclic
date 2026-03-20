package mx.com.endtoend.infrastructure.strategy.common.converters;

import java.util.ArrayList;
import java.util.List;

import mx.com.endtoend.infrastructure.strategy.common.entities.StrategyEntity;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.strategy.dto.StrategyDto;

@Component
public class StrategyConverter {

	public StrategyEntity strategyDtoEntity(StrategyDto strategyDto) {
		
		StrategyEntity strategyEntity = new StrategyEntity();
		
		strategyEntity.setId(strategyDto.getId());
		strategyEntity.setCode(strategyDto.getCode());
		
		return strategyEntity;

	}

	public StrategyDto strategyEntityTostrategyDto (StrategyEntity strategyEntity) {
	
		StrategyDto strategyDto = new StrategyDto();
	
		strategyDto.setId(strategyEntity.getId());
		strategyDto.setCode(strategyEntity.getCode());
		
		
		return strategyDto;
	
	}
	
	public List<StrategyEntity> strategyDtoListToStrategyEntityList(List<StrategyDto> strategyDtoList){
		
		List<StrategyEntity> strategyEntityList = new ArrayList<StrategyEntity>();
		
		for (StrategyDto strategyDto : strategyDtoList) {
			strategyEntityList.add(strategyDtoEntity(strategyDto));
		}
		return strategyEntityList;
	}
	
	public List<StrategyDto> strategyEntityListToStrategyDtoList(List<StrategyEntity> strategyEntityList){
			
			List<StrategyDto> strategyDtoList = new ArrayList<StrategyDto>();
			
			for (StrategyEntity strategyEntity : strategyEntityList) {
				strategyDtoList.add(strategyEntityTostrategyDto(strategyEntity));
			}
			return strategyDtoList;
		}
	
}


