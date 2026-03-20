package mx.com.endtoend.infrastructure.cash.closingInstruments.common.factory;

import mx.com.endtoend.infrastructure.cash.closingInstruments.common.repositories.GenericClosingInstrumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.business.ClosePaymentInstrumentCalRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.calzada.fragua.business.ClosePaymentInstrumentCFraRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.business.ClosePaymentInstrumentFCarredanaRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.ferresamano.business.ClosePaymentInstrumentCFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.carredana.zapata.business.ClosePaymentInstrumentZapataRepository;
import mx.com.endtoend.infrastructure.cash.closingInstruments.demo.business.ClosePaymentInstrumentDemoBusinessRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class ClosingInstrumentRepositoryFactory {

	@Autowired
	private ClosePaymentInstrumentCalRepository closePaymentInstrumentCalRepository;

	@Autowired
	private ClosePaymentInstrumentCFraRepository closePaymentInstrumentCFraRepository;

	@Autowired
	private ClosePaymentInstrumentFCarredanaRepository closePaymentInstrumentFCarredanaRepository;

	@Autowired
	private ClosePaymentInstrumentZapataRepository closePaymentInstrumentZapataRepository;
	
	@Autowired
	private ClosePaymentInstrumentCFSamanoRepository closePaymentInstrumentCFSamanoRepository;

	@Autowired
	private ClosePaymentInstrumentDemoBusinessRepository closePaymentInstrumentDemoBusinessRepository;

	private final Logger LOG = LoggerFactory.getLogger(ClosingInstrumentRepositoryFactory.class);

	public GenericClosingInstrumentRepository getRepositoryByCompanyCode(String companyCode) {

		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN closePaymentInstrumentCalRepository");
				return closePaymentInstrumentCalRepository;

			case CFRA:
				LOG.info("RETURN closePaymentInstrumentCFraRepository");
				return closePaymentInstrumentCFraRepository;

			case FCAR:
				LOG.info("RETURN closePaymentInstrumentFCarredanaRepository");
				return closePaymentInstrumentFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN closePaymentInstrumentZapataRepository");
				return closePaymentInstrumentZapataRepository;
				
			case CFSA:
				LOG.info("RETURN closePaymentInstrumentCFSamanoRepository");
				return closePaymentInstrumentCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN closePaymentInstrumentDemoBusinessRepository");
				return closePaymentInstrumentDemoBusinessRepository;

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
