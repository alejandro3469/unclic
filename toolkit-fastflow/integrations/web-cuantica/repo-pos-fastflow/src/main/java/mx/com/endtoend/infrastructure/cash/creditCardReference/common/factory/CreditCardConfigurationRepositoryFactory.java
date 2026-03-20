package mx.com.endtoend.infrastructure.cash.creditCardReference.common.factory;

import mx.com.endtoend.infrastructure.cash.creditCardReference.common.persistence.GenericCreditCardConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.business.CreditCardConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.calzada.fragua.business.CreditCardConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.business.CreditCardConfigurationFCarredanaRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.ferresamano.business.CreditCardConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.carredana.zapata.business.CreditCardConfigurationCZapataRepository;
import mx.com.endtoend.infrastructure.cash.creditCardReference.demo.business.CreditCardConfigurationDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class CreditCardConfigurationRepositoryFactory {

	@Autowired
	private CreditCardConfigurationCalzadaRepository creditCardConfigurationCalzadaRepository;

	@Autowired
	private CreditCardConfigurationFraguaRepository creditCardConfigurationFraguaRepository;

	@Autowired
	private CreditCardConfigurationFCarredanaRepository creditCardConfigurationFCarredanaRepository;

	@Autowired
	private CreditCardConfigurationCZapataRepository creditCardConfigurationCZapataRepository;

	@Autowired
	private CreditCardConfigurationCFSamanoRepository creditCardConfigurationCFSamanoRepository;

	@Autowired
	private CreditCardConfigurationDemoRepository creditCardConfigurationDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(CreditCardConfigurationRepositoryFactory.class);

	public GenericCreditCardConfigurationPersistence getRepositoryByCompanyCode(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN creditCardConfigurationCalzadaRepository");
				return creditCardConfigurationCalzadaRepository;

			case CFRA:
				LOG.info("RETURN creditCardConfigurationFraguaRepository");
				return creditCardConfigurationFraguaRepository;

			case FCAR:
				LOG.info("RETURN creditCardConfigurationFCarredanaRepository");
				return creditCardConfigurationFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN creditCardConfigurationCZapataRepository");
				return creditCardConfigurationCZapataRepository;

			case CFSA:
				LOG.info("RETURN creditCardConfigurationCFSamanoRepository");
				return creditCardConfigurationCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN creditCardConfigurationDemoRepository");
				return creditCardConfigurationDemoRepository;

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
