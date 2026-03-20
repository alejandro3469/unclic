package mx.com.endtoend.infrastructure.catalogue.orders.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;

@Component
public class CatalogueConverter {

	
	public CatalogueEntity catalogClientTypeDtoToCatalogClientEntity (CatalogueDto catalogDto) {
		
		CatalogueEntity catalogEntity = new CatalogueEntity();
		
		catalogEntity.setId(catalogDto.getId());
		catalogEntity.setCode(catalogDto.getCode());
		catalogEntity.setName(catalogDto.getName());
		return catalogEntity;		
	}
	
	public CatalogueDto catalogClientTypeEntityToCatalogClientTypeDto (CatalogueEntity catalogEntity) {
		
		CatalogueDto catalogDto = new CatalogueDto();
		
		catalogDto.setId(catalogEntity.getId());
		catalogDto.setCode(catalogEntity.getCode());
		catalogDto.setName(catalogEntity.getName());

		return catalogDto;	
	}
	
	 public List<CatalogueEntity> catalogClientTypeDtoListToCatalogClientTypeEntityList(List<CatalogueDto> catalogDtoList){
			
			List<CatalogueEntity> catalogEntityList = new ArrayList<CatalogueEntity>();
			
			for (CatalogueDto catalogDto : catalogDtoList) {
				catalogEntityList.add(catalogClientTypeDtoToCatalogClientEntity(catalogDto));
			}
			return catalogEntityList;
		}
		
	 public List<CatalogueDto> catalogClientTypeEntityListToCatalogClientTypeDtoList(List<CatalogueEntity> catalogEntityList){
				
			List<CatalogueDto> catalogDtoList = new ArrayList<CatalogueDto>();
				
			for (CatalogueEntity catalogEntity : catalogEntityList) {
				catalogDtoList.add(catalogClientTypeEntityToCatalogClientTypeDto(catalogEntity));
			}
			return catalogDtoList;
		}
}
