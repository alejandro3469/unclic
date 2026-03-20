package mx.com.endtoend.infrastructure.catalogue.orders.common.factory;

import mx.com.endtoend.infrastructure.catalogue.orders.common.service.GenericCatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.orders.calzada.business.CatalogueStoreRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.fragua.business.CatalogueOrderFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.business.CatalogueOrderFCarredanaRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.ferresamano.business.CatalogueOrderCFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.carredana.zapata.business.CatalogueOrderCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.orders.demo.business.CatalogueOrderDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CatalogueStoreFactory {

	@Autowired(required = false)
	private CatalogueStoreRepository catalogueStoreRepository;

	@Autowired(required = false)
	private CatalogueOrderFraguaRepository catalogueOrderFraguaRepository;

	@Autowired(required = false)
	private CatalogueOrderFCarredanaRepository catalogueOrderFCarredanaRepository;

	@Autowired(required = false)
	private CatalogueOrderCZapataRepository catalogueOrderCZapataRepository;

	@Autowired(required = false)
	private CatalogueOrderCFSamanoRepository catalogueOrderCFSamanoRepository;

	@Autowired(required = false)
	private CatalogueOrderDemoRepository catalogueOrderDemoRepository;

	public GenericCatalogueService createFactory(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			return catalogueStoreRepository;

		case CFRA:
			return catalogueOrderFraguaRepository;

		case FCAR:
			return catalogueOrderFCarredanaRepository;

		case CZAP:
			return catalogueOrderCZapataRepository;

		case CFSA:
			return catalogueOrderCFSamanoRepository;

		case DEMO:
			return catalogueOrderDemoRepository;

		default:
			return null;
		}

	}

}
