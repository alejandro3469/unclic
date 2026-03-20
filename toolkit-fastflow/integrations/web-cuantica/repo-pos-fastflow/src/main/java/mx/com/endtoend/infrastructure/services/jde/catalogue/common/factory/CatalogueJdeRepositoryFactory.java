package mx.com.endtoend.infrastructure.services.jde.catalogue.common.factory;

import mx.com.endtoend.infrastructure.services.jde.catalogue.common.repository.GenericCatalogueJdeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.business.CatalogueCalzadaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.fragua.business.CatalogueFraguaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.business.CatalogueFCarredanaJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.business.CatalogueCFSamanoJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.zapata.business.CatalogueCZapataJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.demo.business.CatalogueFDemoJdeRepository;

@Component
public class CatalogueJdeRepositoryFactory {

	@Autowired(required = false)
	private CatalogueCalzadaJdeRepository catalogueCalzadaJdeRepository;

	@Autowired(required = false)
	private CatalogueFraguaJdeRepository catalogueFraguaJdeRepository;

	@Autowired(required = false)
	private CatalogueFCarredanaJdeRepository catalogueFCarredanaJdeRepository;

	@Autowired(required = false)
	private CatalogueCZapataJdeRepository catalogueCZapataJdeRepository;

	@Autowired(required = false)
	private CatalogueCFSamanoJdeRepository catalogueCFSamanoJdeRepository;

	@Autowired(required = false)
	private CatalogueFDemoJdeRepository catalogueFDemoJdeRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueJdeRepositoryFactory.class);

	public GenericCatalogueJdeRepository getRepository(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {
			case FCAL:
				LOG.info("RETURN catalogueCalzadaJdeRepository");
				return catalogueCalzadaJdeRepository;

			case CFRA:
				LOG.info("RETURN catalogueFraguaJdeRepository");
				return catalogueFraguaJdeRepository;

			case FCAR:
				LOG.info("RETURN catalogueFCarredanaJdeRepository");
				return catalogueFCarredanaJdeRepository;

			case CZAP:
				LOG.info("RETURN catalogueCZapataJdeRepository");
				return catalogueCZapataJdeRepository;

			case CFSA:
				LOG.info("RETURN catalogueCFSamanoJdeRepository");
				return catalogueCFSamanoJdeRepository;

				case DEMO:
					LOG.info("RETURN catalogueFDemoJdeRepository");
					return catalogueFDemoJdeRepository;

			default:
				return null;
			}
		} catch (Exception e) {
			LOG.error("ERROR GETTING REPOSITORY TO COMPANY - > " + companyCode);
			return null;
		}
	}
}