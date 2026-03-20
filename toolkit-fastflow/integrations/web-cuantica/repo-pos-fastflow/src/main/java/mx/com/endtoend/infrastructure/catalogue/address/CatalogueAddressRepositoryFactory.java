package mx.com.endtoend.infrastructure.catalogue.address;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.catalogue.address.calzada.business.CatalogueAddressCalzadaRepository;
import mx.com.endtoend.infrastructure.catalogue.address.calzada.fragua.business.CatalogueAddressFraguaRepository;
import mx.com.endtoend.infrastructure.catalogue.address.carredana.business.CatalogueAddressFCarredanaRepository;
import mx.com.endtoend.infrastructure.catalogue.address.ferresamano.business.CatalogueAddressCFSamanoRepository;
import mx.com.endtoend.infrastructure.catalogue.address.carredana.zapata.business.CatalogueAddressCZapataRepository;
import mx.com.endtoend.infrastructure.catalogue.address.demo.business.CatalogueAddressDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CatalogueAddressRepositoryFactory {

	@Autowired
	private CatalogueAddressCalzadaRepository catalogueAddressCalzadaRepository;

	@Autowired
	private CatalogueAddressFraguaRepository catalogueAddressFraguaRepository;

	@Autowired
	private CatalogueAddressFCarredanaRepository catalogueAddressFCarredanaRepository;

	@Autowired
	private CatalogueAddressCZapataRepository catalogueAddressCZapataRepository;

	@Autowired
	private CatalogueAddressCFSamanoRepository catalogueAddressCFSamanoRepository;

	@Autowired
	private CatalogueAddressDemoRepository catalogueAddressDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueAddressRepositoryFactory.class);

	public GenericCatalogueAddressRepository getRepository(String companyCode) {
		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);
			switch (value) {
			case FCAL:
				LOG.info("RETURN catalogueAddressCalzadaRepository()");
				return catalogueAddressCalzadaRepository;

			case CFRA:
				LOG.info("RETURN catalogueAddressFraguaRepository()");
				return catalogueAddressFraguaRepository;

			case FCAR:
				LOG.info("RETURN catalogueAddressFCarredanaRepository()");
				return catalogueAddressFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN catalogueAddressCZapataRepository()");
				return catalogueAddressCZapataRepository;

			case CFSA:
				LOG.info("RETURN catalogueAddressCFSamanoRepository()");
				return catalogueAddressCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN catalogueAddressDemoRepository()");
				return catalogueAddressDemoRepository;

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
