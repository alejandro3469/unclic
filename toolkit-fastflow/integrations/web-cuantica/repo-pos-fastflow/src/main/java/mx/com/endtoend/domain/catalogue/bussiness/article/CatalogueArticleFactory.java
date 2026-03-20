package mx.com.endtoend.domain.catalogue.bussiness.article;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;

public class CatalogueArticleFactory {
	
	private final static Logger LOG = LoggerFactory.getLogger(CatalogueArticleFactory.class);
	
	public CatalogueArticleInterface getImplementatioByCode(String method,
			CatalogueJdeServicePort catalogueOracleServicePort) {
		try {

			LOG.info(String.format("INIT getImplementationByCode() "));
			GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(method);

			switch (value) {

			case CAT_ARTICLE_ONE:

				return new CatalogueArticleMethodOne(catalogueOracleServicePort);

			default:
				return null;
			}

		} catch (IllegalArgumentException | NullPointerException ex) {
			LOG.error("ERROR GETTING METHOD TYPE FOR CATALOGUE-ARTICLE MODULE");
			return null;
		}
		
	}

}
