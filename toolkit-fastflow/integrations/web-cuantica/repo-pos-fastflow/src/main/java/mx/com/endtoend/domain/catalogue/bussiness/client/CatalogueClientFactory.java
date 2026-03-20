package mx.com.endtoend.domain.catalogue.bussiness.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;

public class CatalogueClientFactory {

	private final static Logger LOG = LoggerFactory.getLogger(CatalogueClientFactory.class);

	public CatalogueClientInterface getImplementationByCode(String method,
			CatalogueJdeServicePort catalogueOracleServicePort) {

		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			LOG.info(String.format("PARAMS: [ method: %s ]", method));

			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {

			case CAT_CLIENT_ONE:
				LOG.info("RETURN CatalogueClientMethodOne()");
				return new CatalogueClientMethodOne(catalogueOracleServicePort);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CATALOGUE-CLIENT MODULE");
			return null;
		}

	}

}
