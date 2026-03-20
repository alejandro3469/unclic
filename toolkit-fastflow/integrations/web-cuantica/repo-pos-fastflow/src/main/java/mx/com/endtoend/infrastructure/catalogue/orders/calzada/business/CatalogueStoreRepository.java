package mx.com.endtoend.infrastructure.catalogue.orders.calzada.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.StatusRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueStoreRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueStoreRepository(CatalogueConverter catalogueConverter, CatalogueRepository catalogueRepository,
 									StatusRepository statusRepository, StatusConverter statusConverter) {
		super(CatalogueStoreRepository.class,
				catalogueConverter, catalogueRepository,statusRepository,statusConverter);
	}


}
