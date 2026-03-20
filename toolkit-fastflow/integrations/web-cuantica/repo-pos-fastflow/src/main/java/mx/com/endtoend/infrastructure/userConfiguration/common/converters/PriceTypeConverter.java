package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.PriceTypeDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.PriceTypeEntity;

@Component
public class PriceTypeConverter {
	
	public PriceTypeDto priceTypeEntityToPriceTypeDto(PriceTypeEntity priceTypeEntity) {
		
		PriceTypeDto priceTypeDto = new PriceTypeDto();
		
		priceTypeDto.setId(priceTypeEntity.getId());
		priceTypeDto.setCode(priceTypeEntity.getCode());
		priceTypeDto.setDescription(priceTypeEntity.getDescription());
		
		return priceTypeDto;
	}
	
	public PriceTypeEntity priceTypeDtoToPriceTypeEntity(PriceTypeDto priceTypeDto) {
		
		PriceTypeEntity priceTypeEntity = new PriceTypeEntity();
		
		priceTypeEntity.setId(priceTypeDto.getId());
		priceTypeEntity.setCode(priceTypeDto.getCode());
		priceTypeEntity.setDescription(priceTypeDto.getDescription());
		
		return priceTypeEntity;
	}
	
	public List<PriceTypeEntity> priceTypeDtoListToPriceTypeEntityList(List<PriceTypeDto> priceTypeDtoList){
		List<PriceTypeEntity> priceTypeEntityList = new ArrayList<PriceTypeEntity>();
		for (PriceTypeDto priceTypeDto : priceTypeDtoList) {
			priceTypeEntityList.add(priceTypeDtoToPriceTypeEntity(priceTypeDto));
		}
		return priceTypeEntityList;
	}
	
	public List<PriceTypeDto> priceTypeEntityListToPriceTypeDtoList(List<PriceTypeEntity> priceTypeEntityList){
		List<PriceTypeDto> priceTypeDtoList = new ArrayList<PriceTypeDto>();
		for (PriceTypeEntity priceTypeEntity : priceTypeEntityList) {
			priceTypeDtoList.add(priceTypeEntityToPriceTypeDto(priceTypeEntity));
		}
		return priceTypeDtoList;
	}
}
