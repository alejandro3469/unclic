package mx.com.endtoend.infrastructure.catalogue.orders.ferresamano.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.ferresamano.repositories.StatusFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueOrderCFSamanoRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueOrderCFSamanoRepository(CatalogueConverter catalogueConverter,
											CatalogueRepository catalogueRepository,
											StatusFSamanoRepository statusRepository,
											StatusConverter statusConverter) {

		super(CatalogueOrderCFSamanoRepository.class,catalogueConverter ,catalogueRepository,statusRepository,statusConverter);

	}


}
