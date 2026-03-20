package mx.com.endtoend.infrastructure.client.common.factory;

import mx.com.endtoend.infrastructure.client.common.repository.GenericClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.client.Calzada.business.ClientCalzadaRepository;
import mx.com.endtoend.infrastructure.client.Calzada.fragua.business.ClientSFraguaRepository;
import mx.com.endtoend.infrastructure.client.carredana.business.ClientFCarredanaRepository;
import mx.com.endtoend.infrastructure.client.ferresamano.business.ClientCFSamanoRepository;
import mx.com.endtoend.infrastructure.client.carredana.zapata.business.ClientZapataRepository;
import mx.com.endtoend.infrastructure.client.demo.business.ClientDemoBusinessRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class ClientRepositoryFactory {

	@Autowired(required = false)
	public ClientCalzadaRepository clientCalzadaRepository;

	@Autowired(required = false)
	private ClientSFraguaRepository clientSFraguaRepository;

	@Autowired(required = false)
	private ClientFCarredanaRepository clientFCarredanaRepository;

	@Autowired(required = false)
	private ClientZapataRepository clientZapataRepository;

	@Autowired(required = false)
	private ClientCFSamanoRepository clientCFSamanoRepository;

	@Autowired(required = false)
	private ClientDemoBusinessRepository clientDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClientRepositoryFactory.class);

	public GenericClientRepository returnRepository(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN clientCalzadaRepository");
			return clientCalzadaRepository;

		case CFRA:
			LOG.info("RETURN clientSFraguaRepository");
			return clientSFraguaRepository;

		case FCAR:
			LOG.info("RETURN clientFCarredanaRepository");
			return clientFCarredanaRepository;

		case CZAP:
			LOG.info("RETURN clientZapataRepository");
			return clientZapataRepository;

		case CFSA:
			LOG.info("RETURN clientCFSamanoRepository");
			return clientCFSamanoRepository;

		case DEMO:
			LOG.info("RETURN clientDemoBusinessRepository");
			return clientDemoBusinessRepository;

		default:
			LOG.info("RETURN NULL");
			return null;
		}

	}

}
