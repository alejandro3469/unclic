package mx.com.endtoend.infrastructure.cash.emailReport.common.factory;

import mx.com.endtoend.infrastructure.cash.emailReport.common.persistence.GenericEmailCashConfigurationPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.cash.emailReport.calzada.business.EmailCashConfigurationCalzadaRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.calzada.fragua.business.EmailCashConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.carredana.business.EmailCashConfigurationFCarredanaRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.ferresamano.business.EmailCashConfigurationCFSamanoRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.carredana.zapata.business.EmailCashConfigurationCZapataRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.demo.business.EmailCashConfigurationDemoRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;

@Component
public class EmailCashConfigurationRepositoryFactory {

	@Autowired
	private EmailCashConfigurationCalzadaRepository emailCashConfigurationCalzadaRepository;

	@Autowired
	private EmailCashConfigurationFraguaRepository emailCashConfigurationFraguaRepository;

	@Autowired
	private EmailCashConfigurationFCarredanaRepository emailCashConfigurationFCarredanaRepository;

	@Autowired
	private EmailCashConfigurationCZapataRepository emailCashConfigurationCZapataRepository;

	@Autowired
	private EmailCashConfigurationCFSamanoRepository emailCashConfigurationCFSamanoRepository;

	@Autowired
	private EmailCashConfigurationDemoRepository emailCashConfigurationDemoRepository;

	private final Logger LOG = LoggerFactory.getLogger(EmailCashConfigurationRepositoryFactory.class);

	public GenericEmailCashConfigurationPersistence getRepositoryByCompanyCode(String companyCode) {
		try {

			CompanyCodes value = CompanyCodes.valueOf(companyCode);

			switch (value) {

			case FCAL:
				LOG.info("RETURN emailCashConfigurationCalzadaRepository");
				return emailCashConfigurationCalzadaRepository;

			case CFRA:
				LOG.info("RETURN emailCashConfigurationFraguaRepository");
				return emailCashConfigurationFraguaRepository;

			case FCAR:
				LOG.info("RETURN emailCashConfigurationFCarredanaRepository");
				return emailCashConfigurationFCarredanaRepository;

			case CZAP:
				LOG.info("RETURN emailCashConfigurationCZapataRepository");
				return emailCashConfigurationCZapataRepository;

			case CFSA:
				LOG.info("RETURN emailCashConfigurationCFSamanoRepository");
				return emailCashConfigurationCFSamanoRepository;

			case DEMO:
				LOG.info("RETURN emailCashConfigurationDemoRepository");
				return emailCashConfigurationDemoRepository;

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
