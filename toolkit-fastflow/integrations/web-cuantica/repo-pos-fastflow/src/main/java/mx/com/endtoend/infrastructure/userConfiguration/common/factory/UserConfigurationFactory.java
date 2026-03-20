package mx.com.endtoend.infrastructure.userConfiguration.common.factory;

import mx.com.endtoend.infrastructure.userConfiguration.common.repository.GenericUserConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.business.UserConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.calzada.fragua.business.UserConfigFraguaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.business.UserConfigurationCarredanaRepository;
import mx.com.endtoend.infrastructure.userConfiguration.ferresamano.business.UserConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.userConfiguration.carredana.zapata.business.UserConfigurationZapataRepository;
import mx.com.endtoend.infrastructure.userConfiguration.demo.business.UserConfigurationDemoBusinessRepository;

@Component
public class UserConfigurationFactory {

	@Autowired(required = false)
	private UserConfigurationCalzadaRepository userConfigurationCalzadaRepository;

	@Autowired(required = false)
	private UserConfigFraguaRepository userConfigurationFraguaRepository;

	@Autowired(required = false)
	private UserConfigurationCarredanaRepository userConfigurationCarredanaRepository;

	@Autowired(required = false)
	private UserConfigurationZapataRepository userConfigurationZapataRepository;

	@Autowired(required = false)
	private UserConfigurationCFSamanoRepository userConfigurationCFSamanoRepository;

	@Autowired(required = false)
	private UserConfigurationDemoBusinessRepository userConfigurationDemoBusinessRepository;

	public GenericUserConfigurationRepository getClientRepository(String companyCode) {

		CompanyCodes value = CompanyCodes.valueOf(companyCode);

		switch (value) {

		    case FCAL: 
			case ETE:
			    return userConfigurationCalzadaRepository;

		    case CFRA:
			    return userConfigurationFraguaRepository;

		    case FCAR:
			    return userConfigurationCarredanaRepository;

		    case CZAP:
			    return userConfigurationZapataRepository;

		    case CFSA:
			    return userConfigurationCFSamanoRepository;

		    case DEMO:
			    return userConfigurationDemoBusinessRepository;

		    default:
			    return null;
		}

	}

}
