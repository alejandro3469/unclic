package mx.com.endtoend.infrastructure.catalogue.orders.demo.business;

import mx.com.endtoend.infrastructure.catalogue.orders.common.business.BaseCatalogueOrderBusinessRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.demo.repositories.StatusDemoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.CatalogueConverter;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;

@Service
public class CatalogueOrderDemoRepository extends BaseCatalogueOrderBusinessRepository {

	public CatalogueOrderDemoRepository(CatalogueConverter catalogueConverter, CatalogueRepository catalogueRepository,
										StatusDemoRepository statusRepository, StatusConverter statusConverter) {
		super(CatalogueOrderDemoRepository.class, catalogueConverter, catalogueRepository , statusRepository, statusConverter);
	}


}
