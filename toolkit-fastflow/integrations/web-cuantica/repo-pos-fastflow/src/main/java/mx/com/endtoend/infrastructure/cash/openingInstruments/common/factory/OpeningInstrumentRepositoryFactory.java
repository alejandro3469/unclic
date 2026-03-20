package mx.com.endtoend.infrastructure.cash.openingInstruments.common.factory;

import mx.com.endtoend.infrastructure.cash.openingInstruments.common.repository.GenericOpeningInstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.business.OpeningInstrumentCalRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.calzada.fragua.business.OpeningInstrumentFraguaRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.business.OpeningInstrumentFCarredanaRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.ferresamano.business.OpeningInstrumentCFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.carredana.zapata.business.OpeningInstrumentFCZapataRepository;
import mx.com.endtoend.infrastructure.cash.openingInstruments.demo.business.OpeningInstrumentDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class OpeningInstrumentRepositoryFactory {

	@Autowired
	private OpeningInstrumentCalRepository openingInstrumentCalRepository;

	@Autowired
	private OpeningInstrumentFraguaRepository openingInstrumentFraguaRepository;

	@Autowired
	private OpeningInstrumentFCarredanaRepository openingInstrumentFCarredanaRepository;

	@Autowired
	private OpeningInstrumentFCZapataRepository openingInstrumentFCZapataRepository;
	
	@Autowired
	private OpeningInstrumentCFSamanoRepository openingInstrumentCFSamanoRepository;

	@Autowired
	private OpeningInstrumentDemoRepository openingInstrumentDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(OpeningInstrumentRepositoryFactory.class);

	public GenericOpeningInstrumentRepository getRepositoryByCompanyCode(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN openingInstrumentCalRepository");
				return openingInstrumentCalRepository;

			case CFRA:
				LOG.info("RETURN openingInstrumentFraguaRepository");
				return openingInstrumentFraguaRepository;

			case FCAR:
				LOG.info("RETURN openingInstrumentFCarredanaRepository");
				return openingInstrumentFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN openingInstrumentFCZapataRepository");
				return openingInstrumentFCZapataRepository;
				
			case CFSA:
				LOG.info("RETURN openingInstrumentCFSamanoRepository");
				return openingInstrumentCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN openingInstrumentDemoRepository");
				return openingInstrumentDemoRepository;

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