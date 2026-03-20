package mx.com.endtoend.infrastructure.debug;

import mx.com.endtoend.infrastructure.debug.demo.business.DebugFDemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.debug.calzada.DebugCalzadaRepository;
import mx.com.endtoend.infrastructure.debug.calzada.fragua.business.DebugSFraguaRepository;
import mx.com.endtoend.infrastructure.debug.carredana.business.DebugFCarredanaRepository;
import mx.com.endtoend.infrastructure.debug.ferresamano.business.DebugFFerresamanoRepository;

@Component
public class DebugRepositoryFactory {

	@Autowired
	private DebugCalzadaRepository debugCalzadaRepository;

	@Autowired
	private DebugSFraguaRepository debugSFraguaRepository;
	
	@Autowired
	private DebugFCarredanaRepository debugFCarredanaRepository;

	@Autowired
	private DebugFFerresamanoRepository debugFFerresamanoRepository;

	@Autowired
	private DebugFDemoRepository debugDemoRepository;

	public GenericDebugRepositoryInterface getRepositopry(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				return debugCalzadaRepository;

			case CFRA:
				return debugSFraguaRepository;

			case FCAR:
				return debugFCarredanaRepository;

			case CFSA:
				return debugFFerresamanoRepository;

			case DEMO:
				return debugDemoRepository;
				
			default:

				return null;
			}

		} catch (Exception e) {

			return null;
		}
	}

}
