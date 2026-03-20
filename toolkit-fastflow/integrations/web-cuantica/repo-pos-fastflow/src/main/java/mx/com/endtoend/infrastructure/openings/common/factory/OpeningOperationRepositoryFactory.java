package mx.com.endtoend.infrastructure.openings.common.factory;

import mx.com.endtoend.infrastructure.openings.common.repositories.GenericOpeningOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.openings.calzada.business.OpeningOperationCalzadaRepository;
import mx.com.endtoend.infrastructure.openings.calzada.fragua.business.OpeningOperationFraguaRepository;
import mx.com.endtoend.infrastructure.openings.carredana.business.OpeningOperationFCarredanaRepository;
import mx.com.endtoend.infrastructure.openings.ferresamano.business.OpeningOperationCFSamanoRepository;
import mx.com.endtoend.infrastructure.openings.carredana.zapata.business.OpeningOperationZapataRepository;
import mx.com.endtoend.infrastructure.openings.demo.business.OpeningOperationDemoBusinessRepository;

@Component
public class OpeningOperationRepositoryFactory {

	@Autowired(required = false)
	private OpeningOperationCalzadaRepository openingOperationCalRepository;

	@Autowired(required = false)
	private OpeningOperationFraguaRepository openingOperationFraguaRepository;

	@Autowired(required = false)
	private OpeningOperationFCarredanaRepository openingOperationFCarredanaRepository;

	@Autowired(required = false)
	private OpeningOperationZapataRepository openingOperationZapataRepository;

	@Autowired(required = false)
	private OpeningOperationCFSamanoRepository openingOperationCFSamanoRepository;

	@Autowired(required = false)
	private OpeningOperationDemoBusinessRepository openingOperationDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(OpeningOperationRepositoryFactory.class);

	public GenericOpeningOperationRepository getRepositoryByCompanyCode(String companyCode) {

		try {
			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN openingOperationCalRepository");
				return openingOperationCalRepository;

			case CFRA:
				LOG.info("RETURN openingOperationFraguaRepository");
				return openingOperationFraguaRepository;

			case FCAR:
				LOG.info("RETURN openingOperationFCarredanaRepository");
				return openingOperationFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN openingOperationZapataRepository");
				return openingOperationZapataRepository;

			case CFSA:
				LOG.info("RETURN openingOperationCFSamanoRepository");
				return openingOperationCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN openingOperationDemoBusinessRepository");
				return openingOperationDemoBusinessRepository;

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
