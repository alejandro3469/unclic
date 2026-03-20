package mx.com.endtoend.domain.clients.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.infrastructure.services.jde.clients.common.serviceport.ClientOracleServicePort;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.serviceport.ClientLegacyServicePort;

public class ClientFactory  {

	private final static Logger LOG = LoggerFactory.getLogger(ClientFactory.class);

	
	public ClientInterface createFactory(String factoryName, ClientOracleServicePort clientOracleServicePort, ClientLegacyServicePort clientPosLegacyServicePort) {

		GenericIdentifyMethods value = GenericIdentifyMethods.valueOf(factoryName);

		switch (value) {
		case CLI_CONF_ONE:

			return new ClientMethodOne(clientOracleServicePort);	
			
		case CLI_CONF_TWO:

			return new ClientMethodTwo(clientPosLegacyServicePort, clientOracleServicePort);	

		default:
			LOG.error("ERROR AL OBTENER TIPO DE METODO PARA EL MODULO DE CLIENTES");
			return null;
		}

	}
}