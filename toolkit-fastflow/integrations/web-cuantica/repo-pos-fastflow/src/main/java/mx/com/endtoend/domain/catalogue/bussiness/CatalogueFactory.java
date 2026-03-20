package mx.com.endtoend.domain.catalogue.bussiness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.serviceport.CatalogueJdeServicePort;

public class CatalogueFactory{
	
	private final static Logger LOG = LoggerFactory.getLogger(CatalogueFactory.class);
	
	public CatalogueInterface createFactory(String factoryName, CatalogueJdeServicePort catalogueOracleServicePort) {
		
		GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);
			
		switch (value) {
		case CAT_CONF_ONE:	
			return new CatalogueMethodOne(catalogueOracleServicePort);
			
		case CAT_CONF_TWO:	
			return new CatalogueMethodTow(catalogueOracleServicePort);

		default:
			//TODO: MALA IMPLEMENTACION DE LOG
			LOG.error("ERROR AL OBTENER TIPO DE METODO PARA EL MODULO DE CLIENTES");
			return null;
		}
		
	}

}
