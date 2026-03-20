package mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.CatalogDirectionDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.entities.F0117;

@Component
public class F0117Converter {
	
	public F0117 CatalogDirectionDtoToF0117(CatalogDirectionDto catalogDirectionDto) {
		
		F0117 catalogueEntity = new F0117();
		
		catalogueEntity.getId().setA8addz(catalogDirectionDto.getCp());
		catalogueEntity.setA8adds(catalogDirectionDto.getState());
		catalogueEntity.getId().setA8cty1(catalogDirectionDto.getCity());
		catalogueEntity.getId().setA8coun(catalogDirectionDto.getDelegation());
		catalogueEntity.setA8ctr(catalogDirectionDto.getCountry());
		catalogueEntity.getId().setA7add4(catalogDirectionDto.getColony());
		
		return catalogueEntity;
	}
	
	public CatalogDirectionDto f0117ToCatalogDirectionDto(F0117 catalogueEntity) {
		
		CatalogDirectionDto catalogDirectionDto = new CatalogDirectionDto();
		
		if (catalogDirectionDto.getId() == null) {
			catalogDirectionDto.setId(0L);
		}
		catalogDirectionDto.setCp(catalogueEntity.getId().getA8addz());
		catalogDirectionDto.setState(catalogueEntity.getA8adds());
		catalogDirectionDto.setCity(catalogueEntity.getId().getA8cty1());
		catalogDirectionDto.setDelegation(catalogueEntity.getId().getA8coun());
		catalogDirectionDto.setCountry(catalogueEntity.getA8ctr());
		catalogDirectionDto.setColony(catalogueEntity.getId().getA7add4());
		
		return catalogDirectionDto;
	}
	
	 public List<F0117> DirectionDtoListToF0117List(List<CatalogDirectionDto> catalogDirectionDtosLis){
			
			List<F0117> catalogEntityList = new ArrayList<F0117>();
			
			for (CatalogDirectionDto catalogDto : catalogDirectionDtosLis) {
				catalogEntityList.add(CatalogDirectionDtoToF0117(catalogDto));
			}
			return catalogEntityList;
		}
		
	 public List<CatalogDirectionDto> F0117ListToDirectionDtoList(List<F0117> catalogueEntityLis){
				
			List<CatalogDirectionDto> catalogDtoList = new ArrayList<CatalogDirectionDto>();
				
			for (F0117 catalogEntity : catalogueEntityLis) {
				catalogDtoList.add(f0117ToCatalogDirectionDto(catalogEntity));
			}
			return catalogDtoList;
		}

}
