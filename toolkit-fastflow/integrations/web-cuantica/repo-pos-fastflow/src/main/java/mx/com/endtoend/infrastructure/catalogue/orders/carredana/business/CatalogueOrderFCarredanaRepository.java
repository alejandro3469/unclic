package mx.com.endtoend.infrastructure.catalogue.orders.carredana.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.repositories.StatusFCarRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueOrderFCarredanaRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueOrderFCarredanaRepository(CatalogueConverter catalogueConverter, CatalogueRepository catalogueRepository,
											  StatusFCarRepository statusRepository, StatusConverter statusConverter) {
		super(CatalogueOrderFCarredanaRepository.class, catalogueConverter, catalogueRepository , statusRepository, statusConverter);
	}


}
