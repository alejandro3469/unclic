package mx.com.endtoend.infrastructure.catalogue.client.common.factory;

import mx.com.endtoend.infrastructure.catalogue.client.common.repository.GenericCatalogueClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.client.calzada.business.CatalogueClientCalzadaRepository;
import mx.com.endtoend.infrastructure.catalogue.client.calzada.fragua.business.CatalogueClientFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.business.CatalogueClientFCarredanaRepository;
import mx.com.endtoend.infrastructure.catalogue.client.ferresamano.business.CatalogueClientCFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.client.carredana.zapata.business.CatalogueClientCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.client.demo.business.CatalogueClientDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CatalogueClientRepositoryFactory {

	@Autowired(required = false)
	private CatalogueClientCalzadaRepository catalogueClientCalzadaRepository;

	@Autowired(required = false)
	private CatalogueClientFraguaRepository catalogueClientFraguaRepository;

	@Autowired(required = false)
	private CatalogueClientFCarredanaRepository catalogueClientFCarredanaRepository;

	@Autowired(required = false)
	private CatalogueClientCZapataRepository catalogueClientCZapataRepository;

	@Autowired(required = false)
	private CatalogueClientCFSamanoRepository catalogueClientCFSamanoRepository;

	@Autowired(required = false)
	private CatalogueClientDemoRepository catalogueClientDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueClientRepositoryFactory.class);

	public GenericCatalogueClientRepository getRepository(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN catalogueClientCalzadaRepository()");
				return catalogueClientCalzadaRepository;

			case CFRA:
				LOG.info("RETURN catalogueClientFraguaRepository()");
				return catalogueClientFraguaRepository;

			case FCAR:
				LOG.info("RETURN catalogueClientFCarredanaRepository()");
				return catalogueClientFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN catalogueClientCZapataRepository()");
				return catalogueClientCZapataRepository;

			case CFSA:
				LOG.info("RETURN catalogueClientCFSamanoRepository()");
				return catalogueClientCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN catalogueClientDemoRepository()");
				return catalogueClientDemoRepository;

			default:
				LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
				return null;
			}

		} catch (Exception e) {
			LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
			return null;
		}
	}
}
