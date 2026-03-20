package mx.com.endtoend.infrastructure.catalogue.orders.carredana.zapata.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.zapata.repositories.StatusCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueOrderCZapataRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueOrderCZapataRepository(CatalogueConverter catalogueConverter,
	CatalogueRepository catalogueRepository,
	StatusCZapataRepository statusRepository,
	StatusConverter statusConverter){
		super(CatalogueOrderCZapataRepository.class, catalogueConverter, catalogueRepository, statusRepository, statusConverter);

	}
}
