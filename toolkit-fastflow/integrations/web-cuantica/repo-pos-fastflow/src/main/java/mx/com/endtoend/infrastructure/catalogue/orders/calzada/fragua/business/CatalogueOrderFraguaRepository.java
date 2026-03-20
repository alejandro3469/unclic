package mx.com.endtoend.infrastructure.catalogue.orders.calzada.fragua.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.fragua.repositories.StatusFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueOrderFraguaRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueOrderFraguaRepository(CatalogueConverter catalogueConverter,
 							CatalogueRepository catalogueRepository,
							StatusFraguaRepository statusRepository,
							StatusConverter statusConverter) {
		super(CatalogueOrderFraguaRepository.class, catalogueConverter, catalogueRepository, statusRepository, statusConverter);
	}


}
