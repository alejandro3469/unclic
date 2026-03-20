package mx.com.endtoend.infrastructure.userConfiguration.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.userConfiguration.common.entities.SaleTypeEntity;
import mx.com.endtoend.smart.bussiness.model.users.dto.SaleTypeDto;

@Component
public class SaleTypeConverter {

	public SaleTypeEntity saleTypeDtoToSaleTypeEntity(SaleTypeDto saleTypeDto) {

		SaleTypeEntity saleTypeEntity = new SaleTypeEntity();

		saleTypeEntity.setId(saleTypeDto.getId());
		saleTypeEntity.setCode(saleTypeDto.getCode());
		saleTypeEntity.setType(saleTypeDto.getType());
		saleTypeEntity.setModuleId(saleTypeDto.getModuleId());

		return saleTypeEntity;

	}

	public SaleTypeDto saleTypeEntityToSaleTypeDto(SaleTypeEntity saleTypeEntity) {

		SaleTypeDto saleTypeDto = new SaleTypeDto();

		saleTypeDto.setId(saleTypeEntity.getId());
		saleTypeDto.setCode(saleTypeEntity.getCode());
		saleTypeDto.setType(saleTypeEntity.getType());
		saleTypeDto.setModuleId(saleTypeEntity.getModuleId());

		return saleTypeDto;
	}

	public List<SaleTypeEntity> saleTypeDtoListToSaleTypeEntityList(List<SaleTypeDto> saleTypeDtoList) {
		List<SaleTypeEntity> saleTypeEntityList = new ArrayList<SaleTypeEntity>();
		for (SaleTypeDto saleTypeDto : saleTypeDtoList) {
			saleTypeEntityList.add(saleTypeDtoToSaleTypeEntity(saleTypeDto));
		}
		return saleTypeEntityList;
	}

	public List<SaleTypeDto> saleTypeEntityListToSaleTypeDtoList(List<SaleTypeEntity> saleTypeEntityList) {
		List<SaleTypeDto> saleTypeDtoList = new ArrayList<SaleTypeDto>();
		for (SaleTypeEntity saleTypeEntity : saleTypeEntityList) {
			saleTypeDtoList.add(saleTypeEntityToSaleTypeDto(saleTypeEntity));
		}
		return saleTypeDtoList;
	}
}
