package mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0005;

@Component
public class F0005FFerresamanoConverter {

	public F0005 f0005DtoToF0005Entity(CatalogueDto catalogDto) {

		F0005 catalogEntity = new F0005();

		catalogEntity.getId().setDrky(catalogDto.getCode());
		catalogEntity.setDrdl01(catalogDto.getName());

		return catalogEntity;
	}

	public CatalogueDto f0005EntityToF0005Dto(F0005 catalogEntity) {

		CatalogueDto catalogDto = new CatalogueDto();

		catalogDto.setCode(catalogEntity.getId().getDrky());
		catalogDto.setName(catalogEntity.getDrdl01() + catalogEntity.getDrdl02());

		return catalogDto;
	}

	public List<F0005> f0005DtoListToF0005EntityList(List<CatalogueDto> catalogDtoList) {

		List<F0005> catalogEntityList = new ArrayList<F0005>();

		for (CatalogueDto catalogDto : catalogDtoList) {
			catalogEntityList.add(f0005DtoToF0005Entity(catalogDto));
		}
		return catalogEntityList;
	}

	public List<CatalogueDto> f0005EntityListToF0005DtoList(List<F0005> catalogEntityList) {

		List<CatalogueDto> catalogDtoList = new ArrayList<CatalogueDto>();

		for (F0005 catalogEntity : catalogEntityList) {
			catalogDtoList.add(f0005EntityToF0005Dto(catalogEntity));
		}
		return catalogDtoList;
	}

}

