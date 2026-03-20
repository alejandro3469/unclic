package mx.com.endtoend.infrastructure.services.posLegacy.clients.common.factory;

import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.repository.ClientLegacyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.business.HardwareStoreLegacyRepository;
import mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.fragua.business.FraguaPosLegacyRepository;

@Component
public class ClientLegacyFactory {

	@Autowired(required = false)
	private HardwareStoreLegacyRepository hardwareStoreLegacyRepository;

	@Autowired(required = false)
	private FraguaPosLegacyRepository fraguaPosLegacyRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClientLegacyFactory.class);

	public ClientLegacyRepository getClientRepository(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		case FCAL:
			LOG.info("RETURN hardwareStoreLegacyRepository");
			return hardwareStoreLegacyRepository;

		case CFRA:
			LOG.info("RETURN fraguaPosLegacyRepository");
			return fraguaPosLegacyRepository;
			
		default:
			return null;
		}

	}

}
