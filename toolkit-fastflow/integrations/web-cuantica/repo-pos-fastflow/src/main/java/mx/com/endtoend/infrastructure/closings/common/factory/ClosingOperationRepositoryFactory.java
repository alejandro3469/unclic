package mx.com.endtoend.infrastructure.closings.common.factory;

import mx.com.endtoend.infrastructure.closings.common.repositories.GenericClosingOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.closings.calzada.business.ClosingOperationCalzadaRepositoryBusiness;
import mx.com.endtoend.infrastructure.closings.calzada.fragua.business.ClosingOperationFraguaRepository;
import mx.com.endtoend.infrastructure.closings.carredana.business.ClosingOperationFCarredanaRepository;
import mx.com.endtoend.infrastructure.closings.ferresamano.business.ClosingOperationCFSamanoRepository;
import mx.com.endtoend.infrastructure.closings.carredana.zapta.business.ClosingOperationFCZapataRepository;
import mx.com.endtoend.infrastructure.closings.demo.business.ClosingOperationDemoBusinessRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class ClosingOperationRepositoryFactory {

	@Autowired
	private ClosingOperationCalzadaRepositoryBusiness closingOperationCalRepository;

	@Autowired
	private ClosingOperationFraguaRepository closingOperationFraguaRepository;

	@Autowired
	private ClosingOperationFCarredanaRepository closingOperationFCarredanaRepository;

	@Autowired
	private ClosingOperationFCZapataRepository closingOperationFCZapataRepository;

	@Autowired
	private ClosingOperationCFSamanoRepository closingOperationCFSamanoRepository;

	@Autowired
	private ClosingOperationDemoBusinessRepository closingOperationDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClosingOperationRepositoryFactory.class);

	public GenericClosingOperationRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN closingOperationCalRepository");
				return closingOperationCalRepository;

			case CFRA:
				LOG.info("RETURN closingOperationFraguaRepository");
				return closingOperationFraguaRepository;

			case FCAR:
				LOG.info("RETURN closingOperationFCarredanaRepository");
				return closingOperationFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN closingOperationFCZapataRepository");
				return closingOperationFCZapataRepository;

			case CFSA:
				LOG.info("RETURN closingOperationCFSamanoRepository");
				return closingOperationCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN closingOperationDemoBusinessRepository");
				return closingOperationDemoBusinessRepository;

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
