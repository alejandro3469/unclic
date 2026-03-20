package mx.com.endtoend.infrastructure.services.jde.clients.common.factory;

import mx.com.endtoend.infrastructure.services.jde.clients.common.repository.ClientOracleRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.business.HardwareStoreOracleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.business.ClientOracleCarredanaRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.demo.business.ClientOracleDemoRepository;

@Component
public class ClientOracleFactory {

	@Autowired(required = false)
	private HardwareStoreOracleRepository hardwareStoreOracleRepository;

	@Autowired(required = false)
	private ClientOracleCarredanaRepository clientOracleCarredanaRepository;

	@Autowired(required = false)
	private ClientOracleDemoRepository clientOracleDemoRepository;

	@Autowired(required = false)
	private mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.business.ClientOracleFerresamanoRepository clientOracleFerresamanoRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClientOracleFactory.class);

	public ClientOracleRepository getClientRepository(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN hardwareStoreOracleRepository");
			return hardwareStoreOracleRepository;
			
		case CFRA:
			LOG.info("RETURN hardwareStoreOracleRepository");
			return hardwareStoreOracleRepository;

		case FCAR:
			LOG.info("RETURN clientOracleCarredanaRepository");
			return clientOracleCarredanaRepository;
			
		case CZAP:
			LOG.info("RETURN clientOracleCarredanaRepository");
			return clientOracleCarredanaRepository;
			
		case CFSA:
			LOG.info("RETURN clientOracleFerresamanoRepository");
			return clientOracleFerresamanoRepository;

		case DEMO:
			LOG.info("RETURN clientOracleDemoRepository");
			return clientOracleDemoRepository;

		default:
			return null;
		}

	}
}