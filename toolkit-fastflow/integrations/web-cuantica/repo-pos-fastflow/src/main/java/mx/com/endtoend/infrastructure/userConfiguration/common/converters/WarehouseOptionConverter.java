package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.userConfigurations.dto.WarehouseOptionsDto;
import mx.com.endtoend.infrastructure.userConfiguration.common.entities.WarehouseOptionEntity;

@Component
public class WarehouseOptionConverter {

	public WarehouseOptionEntity warehouseOptionDtoToWarehouseOptionEntity(WarehouseOptionsDto warehouseOptionDto) {
		
		WarehouseOptionEntity warehouseOptionEntity = new WarehouseOptionEntity();
		
		warehouseOptionEntity.setId(warehouseOptionDto.getId());
		warehouseOptionEntity.setWarehouseCode(warehouseOptionDto.getWarehouseCode());
		warehouseOptionEntity.setWarehouseName(warehouseOptionDto.getWarehouseName());
		warehouseOptionEntity.setDefault(warehouseOptionDto.getIsDefault());
		warehouseOptionEntity.setConsumption(warehouseOptionDto.getIsConsumption());
		warehouseOptionEntity.setQuery(warehouseOptionDto.getIsQuery());

		
		return warehouseOptionEntity;
	}
	
	public WarehouseOptionsDto warehouseOptionEntityToWarehouseOptionsDto(WarehouseOptionEntity warehouseOptionEntity) {
		
		WarehouseOptionsDto warehouseOptionsDto = new WarehouseOptionsDto();
		
		warehouseOptionsDto.setId(warehouseOptionEntity.getId());
		warehouseOptionsDto.setWarehouseCode(warehouseOptionEntity.getWarehouseCode());
		warehouseOptionsDto.setWarehouseName(warehouseOptionEntity.getWarehouseName());
		warehouseOptionsDto.setIsDefault(warehouseOptionEntity.isDefault());
		warehouseOptionsDto.setIsConsumption(warehouseOptionEntity.isConsumption());
		warehouseOptionsDto.setIsQuery(warehouseOptionEntity.isQuery());
		
		return warehouseOptionsDto;
	}
	
	public List<WarehouseOptionEntity> warehouseOptionDtoListToWarehouseOptionEntityList(List<WarehouseOptionsDto> warehouseOptionDtoList){
		List<WarehouseOptionEntity> warehouseOptionEntityList = new ArrayList<WarehouseOptionEntity>();
		for (WarehouseOptionsDto warehouseOptionsDto : warehouseOptionDtoList) {
			warehouseOptionEntityList.add(warehouseOptionDtoToWarehouseOptionEntity(warehouseOptionsDto));
		}
		return warehouseOptionEntityList;
	}
	
	public List<WarehouseOptionsDto> warehouseOptionEntityListToWarehouseOptionsDtoList(List<WarehouseOptionEntity> warehouseOptionEntityList){
		List<WarehouseOptionsDto> warehouseOtionDtoList = new ArrayList<WarehouseOptionsDto>();
		for (WarehouseOptionEntity warehouseOptionEntity : warehouseOptionEntityList) {
			warehouseOtionDtoList.add(warehouseOptionEntityToWarehouseOptionsDto(warehouseOptionEntity));
		}
		return warehouseOtionDtoList;
	}
}