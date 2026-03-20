package mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.CatalogueJdeDTO;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.entities.F0005;

@Component
public class F0005JdeConverter {
	
	public F0005 f0005DtoToF0005Entity(CatalogueJdeDTO catalogDto) {

		F0005 catalogEntity = new F0005();

		catalogEntity.getId().setDrky(catalogDto.getCode());
		catalogEntity.setDrdl01(catalogDto.getName());


		return catalogEntity;
	}

	public CatalogueJdeDTO f0005EntityToF0005Dto(F0005 catalogEntity) {

		CatalogueJdeDTO catalogDto = new CatalogueJdeDTO();		

		catalogDto.setCode(catalogEntity.getId().getDrky());
		catalogDto.setName(catalogEntity.getDrdl01());

		return catalogDto;
	}

	public List<F0005> f0005DtoListToF0005EntityList(List<CatalogueJdeDTO> catalogDtoList) {

		List<F0005> catalogEntityList = new ArrayList<F0005>();

		for (CatalogueJdeDTO catalogDto : catalogDtoList) {
			catalogEntityList.add(f0005DtoToF0005Entity(catalogDto));
		}
		return catalogEntityList;
	}

	public List<CatalogueJdeDTO> f0005EntityListToF0005DtoList(List<F0005> catalogEntityList) {

		List<CatalogueJdeDTO> catalogDtoList = new ArrayList<CatalogueJdeDTO>();

		for (F0005 catalogEntity : catalogEntityList) {
			catalogDtoList.add(f0005EntityToF0005Dto(catalogEntity));
		}
		return catalogDtoList;
	}

}
